package covid.fukui.rss.articlefeeder.domain.model;

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

    private final Flux<Article> articleFlux;

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
     * コロナウイルス関連記事のみ抽出する
     *
     * @return コロナウイルス関連記事リスト(Flux)
     */
    @NonNull
    public Flux<Article> getCovid19Articles() {
        return articleFlux.filter(Article::isTopicOfCovid19);
    }
}
