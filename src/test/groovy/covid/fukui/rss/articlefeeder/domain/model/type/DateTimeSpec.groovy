package covid.fukui.rss.articlefeeder.domain.model.type

import com.google.cloud.Timestamp
import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class DateTimeSpec extends Specification {

    @Unroll
    final "ファクトリメソッド #caseName"() {
        when:
        final sut = DateTime.from(pubDate)

        then:
        sut.value == expected

        where:
        caseName | pubDate                           || expected
        "正常系"    | "Sun, 27 Jun 2021 08:00:00 +0900" || LocalDateTime.of(2021, 6, 27, 8, 00, 00)
    }

    @Unroll
    final "ファクトリメソッド - 例外発生 - #caseName"() {
        when:
        DateTime.from(value)

        then:
        final exception = thrown(InvalidArgumentException)
        exception.getMessage() == expected

        where:
        caseName                 | value  || expected
        "DateTimeParseException" | "hoge" || "文字列がRFC1123の日付フォーマットに準拠していません。"
        "NullPointerException"   | null   || "文字列がnullです。"
    }

    @Unroll
    final "convertToLocalDateメソッド"() {
        when:
        final sut = DateTime.from("Sun, 27 Jun 2021 08:00:00 +0900")

        then:
        sut.convertToLocalDate() == LocalDateTime.of(2021, 6, 27, 8, 00, 00)
    }

    @Unroll
    final "convertToTimestampメソッド"() {
        when:
        final sut = DateTime.from("Sun, 27 Jun 2021 08:00:00 +0900")

        then:
        sut.convertToTimestamp() == Timestamp.of(new Date(1624748400000L))
    }
}
