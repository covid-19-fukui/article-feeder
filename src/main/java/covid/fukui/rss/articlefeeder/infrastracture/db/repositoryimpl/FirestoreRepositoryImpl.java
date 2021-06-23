package covid.fukui.rss.articlefeeder.infrastracture.db.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * FirestoreのRepository
 */
@Repository
@RequiredArgsConstructor
public class FirestoreRepositoryImpl implements FirestoreRepository {

    private final FirestoreTemplate firestoreTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<ArticleCollection> insertBulkArticle(
            final Flux<ArticleCollection> articleCollections) {
        return firestoreTemplate.saveAll(articleCollections);
    }
}
