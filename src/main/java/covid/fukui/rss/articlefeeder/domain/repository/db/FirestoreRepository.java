package covid.fukui.rss.articlefeeder.domain.repository.db;

import covid.fukui.rss.articlefeeder.infrastoracture.db.dto.ArticleCollection;
import reactor.core.publisher.Flux;

public interface FirestoreRepository {

    /**
     * 記事情報をまとめて保存する
     *
     * @param articleCollections 保存対象の記事情報
     * @return firestoreに保存された記事情報リスト
     */
    Flux<ArticleCollection> insertBulkArticle(final Flux<ArticleCollection> articleCollections);
}
