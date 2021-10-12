package covid.fukui.rss.articlefeeder.domain.repository.api;

import covid.fukui.rss.articlefeeder.domain.model.article.Articles;
import reactor.core.publisher.Mono;

public interface RssRepository {

    /**
     * 記事情報の取得
     *
     * @return 記事情報のリスト(Flux)
     */
    Mono<Articles> getArticles();
}
