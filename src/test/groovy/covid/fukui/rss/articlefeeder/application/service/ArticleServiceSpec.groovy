package covid.fukui.rss.articlefeeder.application.service

import com.google.cloud.Timestamp
import covid.fukui.rss.articlefeeder.domain.model.Article
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository
import covid.fukui.rss.articlefeeder.infrastracture.api.dto.RssResponse
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollection
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

import java.security.MessageDigest
import java.time.LocalDateTime

class ArticleServiceSpec extends Specification {

    private ArticleService sut
    private RssRepository rssRepository
    private FirestoreRepository firestoreRepository

    final setup() {

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256")

        rssRepository = Mock(RssRepository)
        firestoreRepository = Mock(FirestoreRepository)
        sut = new ArticleService(rssRepository, firestoreRepository, sha256)
    }

    @Unroll
    final "記事データを取得する #caseName"() {
        given:
        // mockを作成する
        rssRepository.getArticles() >> Mono.just(
                new RssResponse(
                        channel: new RssResponse.Channel(item: [
                                new RssResponse.Item(title: 'title1', link: 'link1', pubDate: 'Sun, 27 Jun 2021 08:00:00 +0900'),
                                new RssResponse.Item(title: 'title2', link: 'link2', pubDate: 'Sat, 26 Jun 2021 08:00:00 +0900')
                        ])))

        firestoreRepository.insertBulkArticle(*_) >> Flux.fromIterable(getArticleCollections())

        when:
        final actual = sut.feedArticles().collectList().block()

        then:
        actual == expected

        where:
        caseName || expected
        "正常系"    || getArticleCollections()
    }

    @Unroll
    final "getArticles #caseName"() {
        given:
        // mockを作成する
        rssRepository.getArticles() >> Mono.just(
                new RssResponse(
                        channel: new RssResponse.Channel(item: [
                                new RssResponse.Item(title: 'ウイルス', link: 'link1', pubDate: 'Sun, 27 Jun 2021 08:00:00 +0900'),
                                new RssResponse.Item(title: 'ウイルス', link: 'link2', pubDate: 'Sat, 26 Jun 2021 08:00:00 +0900')
                        ])))

        when:
        final actual = sut.getArticles().collectList().block()

        then:
        actual == expected

        where:
        caseName || expected
        "正常系"    || getArticleCollections()
    }

    @Unroll
    final "parsePubDate #caseName"() {
        expect:
        sut.parsePubDate("Sun, 27 Jun 2021 08:00:00 +0900") == expected

        where:
        caseName || expected
        "正常系"    || LocalDateTime.of(2021, 6, 27, 8, 0, 0)
    }

    @Unroll
    final "buildArticle #caseName"() {
        expect:
        sut.buildArticle(item) == expected

        where:
        caseName | item                                                                                             || expected
        "正常系"    | new RssResponse.Item(title: 'title1', link: 'link1', pubDate: 'Sun, 27 Jun 2021 08:00:00 +0900') || new Article("title1", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0))
    }

    @Unroll
    final "buildArticleCollection #caseName"() {
        expect:
        sut.buildArticleCollection(article) == expected

        where:
        caseName | article                                                                || expected
        "正常系"    | new Article("title1", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0)) || new ArticleCollection("5018e26faaec56a465e61aaeb752c2984c8a6d6305c8496b3a95213fb20889c7", "title1", "link1", Timestamp.of(new Date(1624748400000L)))
    }

    @Unroll
    final "encryptWithMd5 #caseName"() {
        expect:
        sut.encryptWithMd5(title) == expected

        where:
        caseName | title    || expected
        "正常系"    | "title1" || "5018e26faaec56a465e61aaeb752c2984c8a6d6305c8496b3a95213fb20889c7"
    }

    @Unroll
    final "convertToTimestamp #caseName"() {
        expect:
        sut.convertToTimestamp(datetime) == expected

        where:
        caseName | datetime                               || expected
        "正常系"    | LocalDateTime.of(2021, 6, 27, 8, 0, 0) || Timestamp.of(new Date(1624748400000L))
    }

    @Unroll
    final "isTopicOfCovid19 #caseName"() {
        expect:
        sut.isTopicOfCovid19(article) == expected

        where:
        caseName     | article                                                              || expected
        "コロナを含む"     | new Article("コロナ", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0))  || true
        "感染を含む"      | new Article("感染", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0))   || true
        "マスクを含む"     | new Article("マスク", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0))  || true
        "接種を含む"      | new Article("接種", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0))   || true
        "ウイルスを含む"    | new Article("ウイルス", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0)) || true
        "キーワードを含まない" | new Article("hoge", "link1", LocalDateTime.of(2021, 6, 27, 8, 0, 0)) || false
    }

    /**
     * 記事情報を取得
     *
     * @return 記事情報
     */
    private static getArticleCollections() {
        return [
                new ArticleCollection("f16f2d47d63947902286a3fadd1424dad42dd25ded9bfca0fdec7b4af8e17fca", "ウイルス", "link1", Timestamp.of(new Date(1624748400000L))),
                new ArticleCollection("f16f2d47d63947902286a3fadd1424dad42dd25ded9bfca0fdec7b4af8e17fca", "ウイルス", "link2", Timestamp.of(new Date(1624662000000L)))
        ]
    }
}
