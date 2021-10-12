package covid.fukui.rss.articlefeeder.domain.model.type

import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle
import spock.lang.Specification
import spock.lang.Unroll

class OriginalTitleSpec extends Specification {

    @Unroll
    final "ファクトリメソッド"() {
        when:
        final sut = OriginalTitle.from("title")

        then:
        sut == new OriginalTitle("title")
    }

    @Unroll
    final "toStringメソッド"() {
        when:
        final sut = new OriginalTitle("title")

        then:
        sut.toString() == "title"
    }
}
