package covid.fukui.rss.articlefeeder.domain.repository.api;

import covid.fukui.rss.articlefeeder.domain.model.Article;
import reactor.core.publisher.Flux;

public interface RssRepository {

    /**
     * 記事情報の取得
     *
     * @return 記事情報のリスト(Flux)
     */
    Flux<Article> getArticles();
}
