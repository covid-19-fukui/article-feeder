package covid.fukui.rss.articlefeeder.domain.type

import spock.lang.Specification
import spock.lang.Unroll

class TitleSpec extends Specification {

    @Unroll
    final "ファクトリメソッド"() {
        when:
        final sut = Title.from("title")

        then:
        sut.value == "title"
    }

    @Unroll
    final "toStringメソッド"() {
        when:
        final sut = new Title("title")

        then:
        sut.value.toString() == "title"
    }
}
