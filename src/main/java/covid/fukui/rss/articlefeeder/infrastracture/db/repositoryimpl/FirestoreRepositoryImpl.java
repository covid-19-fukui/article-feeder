package covid.fukui.rss.articlefeeder.infrastracture.db.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.model.Article;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import covid.fukui.rss.articlefeeder.domain.service.TitleService;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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

    private final TitleService titleService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Mono<Integer> insertBulkArticle(
            final Flux<Article> articles) {

        final var articleCollections =
                articles.map(this::buildArticleCollection);

        return firestoreTemplate.saveAll(articleCollections)
                .collectList()
                .map(CollectionUtils::size);
    }

    /**
     * ArticleCollectionDtoの生成
     *
     * @param article Articleドメイン
     * @return ArticleCollectionDto
     */
    @NonNull
    private ArticleCollectionDto buildArticleCollection(final Article article) {

        final var articleKey = titleService.encryptWithMd5(article.getTitle());

        return ArticleCollectionDto.from(article, articleKey);
    }
}
