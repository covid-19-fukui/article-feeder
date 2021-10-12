package covid.fukui.rss.articlefeeder.domain.model.article


import covid.fukui.rss.articlefeeder.domain.model.type.DateTime
import covid.fukui.rss.articlefeeder.domain.model.type.Link
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class ArticleSpec extends Specification {

    @Unroll
    final "ファクトリメソッド #caseName"() {
        when:
        final sut = Article.of(new OriginalTitle(title), new Link(link), new DateTime(datetime))

        then:
        verifyAll(sut) {
            sut.originalTitle == new OriginalTitle(expectedTitle)
            sut.datetime == new DateTime(expectedDateTime)
            sut.link == new Link(expectedLink)
        }

        where:
        caseName | title   | link   | datetime                                   || expectedTitle | expectedDateTime                           | expectedLink
        "正常系"    | "title" | "link" | LocalDateTime.of(2021, 07, 01, 00, 00, 00) || "title"       | LocalDateTime.of(2021, 07, 01, 00, 00, 00) | "link"
    }

    @Unroll
    final "isTopicOfCovid19() #caseName"() {
        when:
        final sut = new Article(new OriginalTitle(title), new Link("link"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00)))

        then:
        sut.isTopicOfCovid19() == expected

        where:
        caseName     | title  || expected
        "コロナを含む"     | "コロナ"  || true
        "感染を含む"      | "感染"   || true
        "マスクを含む"     | "マスク"  || true
        "接種を含む"      | "接種"   || true
        "ウイルスを含む"    | "ウイルス" || true
        "キーワードを含まない" | "hoge" || false
    }
}
