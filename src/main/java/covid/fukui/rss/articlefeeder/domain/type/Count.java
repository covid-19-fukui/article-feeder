package covid.fukui.rss.articlefeeder.domain.type;

import covid.fukui.rss.articlefeeder.exception.InvalidCountException;
import java.io.Serializable;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
public class Count implements Serializable {


    private static final ZoneId JAPAN_ZONE_ID = ZoneId.of("Japan");

    private static final long serialVersionUID = 7704077945080882845L;

    private final Integer value;

    /**
     * Integerから型インスタンスを生成するファクトリメソッド
     *
     * @param value int
     * @return DateTimeの型インスタンス
     */
    @NonNull
    public static Count from(final int value) {

        if (value >= 0) {
            return new Count(value);
        } else {
            throw new InvalidCountException("数値が負数です。");
        }
    }
}
