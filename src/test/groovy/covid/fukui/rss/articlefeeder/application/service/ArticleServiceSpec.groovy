package covid.fukui.rss.articlefeeder.application.service

import covid.fukui.rss.articlefeeder.domain.model.article.Article
import covid.fukui.rss.articlefeeder.domain.model.type.Count
import covid.fukui.rss.articlefeeder.domain.model.type.DateTime
import covid.fukui.rss.articlefeeder.domain.model.type.Link
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class ArticleServiceSpec extends Specification {

    private ArticleService sut
    private RssRepository rssRepository
    private FirestoreRepository firestoreRepository

    final setup() {
        rssRepository = Mock(RssRepository)
        firestoreRepository = Mock(FirestoreRepository)
        sut = new ArticleService(rssRepository, firestoreRepository)
    }

    @Unroll
    final "insertBulkArticleメソッド"() {
        given:
        // mockを作成する
        rssRepository.getArticles() >> Flux.fromIterable([
                new Article(new OriginalTitle("title1"), Link.from("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new OriginalTitle("title2"), Link.from("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ])

        final expected = Mono.empty().then().block()

        firestoreRepository.insertBulkArticle(*_) >> Mono.just(new Count(2))

        expect:
        sut.feedArticles().block() == expected
    }

    @Unroll
    final "getArticlesメソッド"() {
        given:
        // mockを作成する
        rssRepository.getArticles() >> Flux.fromIterable([
                new Article(new OriginalTitle("コロナ"), Link.from("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new OriginalTitle("title2"), Link.from("https://localhost2"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ])

        when:
        final actual = sut.getArticles().collectList().block()

        then:
        actual == [
                new Article(new OriginalTitle("コロナ"), Link.from("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
        ]
    }
}
