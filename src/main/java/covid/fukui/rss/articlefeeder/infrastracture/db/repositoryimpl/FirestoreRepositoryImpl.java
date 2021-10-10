package covid.fukui.rss.articlefeeder.infrastracture.db.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.model.article.Article;
import covid.fukui.rss.articlefeeder.domain.model.type.Count;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import covid.fukui.rss.articlefeeder.domain.service.TitleDomainService;
import covid.fukui.rss.articlefeeder.exception.FailedSaveArticleException;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionDto;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
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
     * @param articles
     */
    @Override
    public Mono<Count> insertBulkArticle(
            final Flux<Article> articles) {

        final var articleCollections =
                articles.map(this::buildArticleCollection);

        return firestoreTemplate.saveAll(articleCollections)
                .onErrorResume(exception -> Mono
                        .error(new FailedSaveArticleException("記事の保存に失敗しました", exception)))
                .collectList()
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
