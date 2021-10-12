package covid.fukui.rss.articlefeeder.domain.model.article;

import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Slf4j
public class SavedArticleCount implements Serializable {

    private static final long serialVersionUID = 7704077945080882845L;

    private final Integer value;

    /**
     * Integerから型インスタンスを生成するファクトリメソッド
     *
     * @param value int
     * @return Countの型インスタンス
     */
    @NonNull
    public static SavedArticleCount from(final int value) throws InvalidArgumentException {

        final var isDigit = 0 <= value;

        if (isDigit) {
            return new SavedArticleCount(value);
        }

        throw new InvalidArgumentException("数値が負数です。");
    }

    /**
     * Monoをブロッキングして型インスタンスを生成するファクトリメソッド
     *
     * @param countMono Mono
     * @return Countの型インスタンス
     */
    public static SavedArticleCount fromMonoSync(final Mono<SavedArticleCount> countMono) {
        final var count = countMono.block();

        if (Objects.isNull(count)) {
            throw new InvalidArgumentException("monoがnullです");
        }

        return count;
    }

    /**
     * カウントの値をログ出力する
     */
    public void logCount() {
        log.info("更新された記事数:" + value);
    }
}
