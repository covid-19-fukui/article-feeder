package covid.fukui.rss.articlefeeder.domain.model.article;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

/**
 * 記事コレクションドメイン
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Articles {

    private final Flux<Article> articles;

    /**
     * 記事情報リストのファクトリメソッド
     *
     * @return 記事コレクションドメイン
     */
    @NonNull
    public static Articles from(final Flux<Article> articleList) {
        return new Articles(articleList);
    }

    /**
     * コロナウイルス関連記事にフィルタリングする
     *
     * @return コロナウイルス関連記事リスト(Flux)
     */
    @NonNull
    public Flux<Article> filterCovid19AsFlux() {
        return articles.filter(Article::isTopicOfCovid19);
    }
}
