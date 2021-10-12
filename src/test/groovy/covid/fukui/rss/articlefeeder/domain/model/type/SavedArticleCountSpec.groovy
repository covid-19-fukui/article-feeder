package covid.fukui.rss.articlefeeder.domain.model.type

import covid.fukui.rss.articlefeeder.domain.model.article.SavedArticleCount
import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException
import spock.lang.Specification
import spock.lang.Unroll

class SavedArticleCountSpec extends Specification {

    @Unroll
    final "ファクトリメソッド #caseName"() {
        when:
        final sut = SavedArticleCount.from(value)

        then:
        sut.value == expected

        where:
        caseName | value || expected
        "正常系"    | 2     || 2
    }

    @Unroll
    final "ファクトリメソッド - 例外発生 - #caseName"() {
        when:
        SavedArticleCount.from(value)

        then:
        final exception = thrown(InvalidArgumentException)
        exception.getMessage() == expected

        where:
        caseName | value || expected
        "-1"     | -1    || "数値が負数です。"
    }
}
