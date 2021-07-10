package covid.fukui.rss.articlefeeder.domain.model

import covid.fukui.rss.articlefeeder.domain.type.DateTime
import covid.fukui.rss.articlefeeder.domain.type.Title
import reactor.core.publisher.Flux
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class ArticlesSpec extends Specification {

    @Unroll
    final "ファクトリメソッド"() {
        when:
        final sut = Articles.from(Flux.fromIterable([
                new Article(new Title("title1"), "link1", new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new Title("title2"), "link2", new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]))


        then:
        sut.articleFlux.collectList().block() == [
                new Article(new Title("title1"), "link1", new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new Title("title2"), "link2", new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]
    }

    @Unroll
    final "getCovid19Articlesメソッド"() {
        when:
        final sut = new Articles(Flux.fromIterable([
                new Article(new Title("hoge"), "link1", new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new Title("コロナ"), "link2", new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]))


        then:
        sut.getCovid19Articles().collectList().block() == [
                new Article(new Title("コロナ"), "link2", new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]

    }
}
