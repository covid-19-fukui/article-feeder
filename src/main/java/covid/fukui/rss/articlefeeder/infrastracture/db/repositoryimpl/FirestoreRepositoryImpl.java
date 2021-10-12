package covid.fukui.rss.articlefeeder.infrastracture.db.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.model.article.Article;
import covid.fukui.rss.articlefeeder.domain.model.article.Articles;
import covid.fukui.rss.articlefeeder.domain.model.article.SavedArticleCount;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import covid.fukui.rss.articlefeeder.domain.service.TitleDomainService;
import covid.fukui.rss.articlefeeder.exception.FailedSaveArticleException;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionDto;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionsDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * FirestoreのRepository
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class FirestoreRepositoryImpl implements FirestoreRepository {

    private final FirestoreTemplate firestoreTemplate;

    private final TitleDomainService titleDomainService;

    /**
     * {@inheritDoc}
     *
     * @param articles Articleドメイン
     */
    @Override
    public Mono<SavedArticleCount> insertBulkArticle(
            final Articles articles) {

        final var articleCollectionsDto = articles
                .asStream()
                .map(this::buildArticleCollection)
                .collect(Collectors.toUnmodifiableList());

        final var articleCollections = ArticleCollectionsDto
                .from(articleCollectionsDto).asFlux();

        final Mono<List<ArticleCollectionDto>> savedArticleCollectionListMono = firestoreTemplate
                .saveAll(articleCollections)
                .onErrorResume(exception -> Mono.error(
                        new FailedSaveArticleException("記事の保存に失敗しました:", exception)))
                .collectList();

        return savedArticleCollectionListMono
                .map(ArticleCollectionsDto::from)
                .map(ArticleCollectionsDto::size);
    }

    /**
     * ArticleCollectionDtoの生成
     *
     * @param article Articleドメイン
     * @return ArticleCollectionDto
     */
    @NonNull
    private ArticleCollectionDto buildArticleCollection(final Article article) {
        final var articleKey = titleDomainService.encryptWithSha256(article.getOriginalTitle());

        return ArticleCollectionDto.from(article, articleKey);
    }
}
