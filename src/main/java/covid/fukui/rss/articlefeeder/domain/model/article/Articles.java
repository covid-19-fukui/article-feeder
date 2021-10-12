package covid.fukui.rss.articlefeeder.domain.model.article;

import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

/**
 * 記事コレクションドメイン
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Articles {

    private final List<Article> articles;

    /**
     * 記事情報リストのファクトリメソッド
     *
     * @return 記事コレクションドメイン
     */
    @NonNull
    public static Articles from(final List<Article> articleList) {
        return new Articles(articleList);
    }

    /**
     * コロナウイルス関連記事にフィルタリングする
     *
     * @return コロナウイルス関連記事リスト(Flux)
     */
    @NonNull
    public Articles filterCovid19() {
        final var articleFilteredCovid19 =
                articles.stream().filter(Article::isTopicOfCovid19).collect(Collectors.toList());

        return new Articles(articleFilteredCovid19);
    }

    @NonNull
    public Stream<Article> asStream() {
        return articles.stream();
    }

    /**
     * MonoをブロッキングしてからArticlesを取得する
     *
     * @param articlesMono Mono
     * @return Articles
     */
    @NonNull
    public static Articles fromMonoSync(final Mono<Articles> articlesMono) {
        final var articles = articlesMono.block();

        if (Objects.isNull(articles)) {
            throw new InvalidArgumentException("monoがnullです");
        }

        return articles;
    }
}
