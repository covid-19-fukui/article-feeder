package covid.fukui.rss.articlefeeder.domain.repository.db;

import covid.fukui.rss.articlefeeder.domain.model.Article;
import covid.fukui.rss.articlefeeder.domain.type.Count;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FirestoreRepository {

    /**
     * 記事情報をまとめて保存する
     *
     * @param articleCollections 保存対象の記事情報
     * @return 更新された記事の件数
     */
    Mono<Count> insertBulkArticle(final Flux<Article> articleCollections);
}
