package covid.fukui.rss.articlefeeder.domain.model.article


import covid.fukui.rss.articlefeeder.domain.model.type.DateTime
import covid.fukui.rss.articlefeeder.domain.model.type.Link
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle
import reactor.core.publisher.Flux
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class ArticlesSpec extends Specification {

    @Unroll
    final "ファクトリメソッド"() {
        when:
        final sut = Articles.from(Flux.fromIterable([
                new Article(new OriginalTitle("title1"), new Link("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new OriginalTitle("title2"), new Link("https://localhost2"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]))


        then:
        sut.articles.collectList().block() == [
                new Article(new OriginalTitle("title1"), new Link("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new OriginalTitle("title2"), new Link("https://localhost2"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]
    }

    @Unroll
    final "getCovid19Articlesメソッド"() {
        when:
        final sut = new Articles(Flux.fromIterable([
                new Article(new OriginalTitle("hoge"), new Link("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new OriginalTitle("コロナ"), new Link("https://localhost2"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]))


        then:
        sut.filterCovid19().collectList().block() == [
                new Article(new OriginalTitle("コロナ"), new Link("https://localhost2"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ]

    }
}
