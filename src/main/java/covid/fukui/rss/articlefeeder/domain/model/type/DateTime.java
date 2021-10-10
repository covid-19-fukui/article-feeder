package covid.fukui.rss.articlefeeder.domain.model.type;

import com.google.cloud.Timestamp;
import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class DateTime implements Serializable {

    private static final long serialVersionUID = 7852859403300478054L;

    private static final ZoneId JAPAN_ZONE_ID = ZoneId.of("Japan");

    private final LocalDateTime value;

    /**
     * Stringから型インスタンスを生成するファクトリメソッド
     *
     * @param value String
     * @return DateTimeの型インスタンス
     */
    @NonNull
    public static DateTime from(final String value) throws InvalidArgumentException {
        try {
            return new DateTime(LocalDateTime.parse(value, DateTimeFormatter.RFC_1123_DATE_TIME));
        } catch (final DateTimeParseException dateTimeParseException) {
            throw new InvalidArgumentException("文字列がRFC1123の日付フォーマットに準拠していません。",
                    dateTimeParseException);
        } catch (final NullPointerException nullPointerException) {
            throw new InvalidArgumentException("文字列がnullです。",
                    nullPointerException);
        }
    }

    /**
     * LocalDateTime型に変換する
     *
     * @return LocalDateTime
     */
    @NonNull
    public LocalDateTime convertToLocalDate() {
        return value;
    }

    /**
     * Timestamp型に変換する
     *
     * @return Timestamp
     */
    @NonNull
    public Timestamp convertToTimestamp() {
        return Timestamp.of(Date.from(convertToLocalDate().atZone(JAPAN_ZONE_ID).toInstant()));
    }
}
