package covid.fukui.rss.articlefeeder.domain.model.type

import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException
import spock.lang.Specification
import spock.lang.Unroll

class CountSpec extends Specification {

    @Unroll
    final "ファクトリメソッド #caseName"() {
        when:
        final sut = Count.from(value)

        then:
        sut.value == expected

        where:
        caseName | value || expected
        "正常系"    | 2     || 2
    }

    @Unroll
    final "ファクトリメソッド - 例外発生 - #caseName"() {
        when:
        Count.from(value)

        then:
        final exception = thrown(InvalidArgumentException)
        exception.getMessage() == expected

        where:
        caseName | value || expected
        "-1"     | -1    || "数値が負数です。"
    }
}
