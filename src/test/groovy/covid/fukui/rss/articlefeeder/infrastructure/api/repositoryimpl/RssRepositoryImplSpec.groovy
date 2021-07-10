package covid.fukui.rss.articlefeeder.infrastructure.api.repositoryimpl

import covid.fukui.rss.articlefeeder.domain.model.Article
import covid.fukui.rss.articlefeeder.domain.type.DateTime
import covid.fukui.rss.articlefeeder.domain.type.Title
import covid.fukui.rss.articlefeeder.infrastracture.api.repositoryimpl.RssRepositoryImpl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification

import java.time.LocalDateTime

class RssRepositoryImplSpec extends Specification {

    private MockWebServer mockWebServer
    private WebClient webClient

    final setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        webClient = WebClient.create(mockWebServer.url("/").toString())
    }

    final cleanup() {
        mockWebServer.shutdown()
    }

    final "getArticlesメソッド"() {
        given:
        // テスト対象クラスのインスタンス作成
        final sut = new RssRepositoryImpl(webClient)

        // mockサーバを作成する
        final mockResponse = new FileReader("src/test/resources/xml/paper.xml").text
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(HttpStatus.OK.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_XML)
                        .setBody(mockResponse))

        final expectedResults = [
                new Article(
                        new Title("新型コロナウイルスのワクチン接種　ファイザー、モデルナ、アストラゼネカ…対象年齢、副反応など違いは？"),
                        "https://www.fukuishimbun.co.jp/articles/-/1336511",
                        new DateTime(LocalDateTime.of(2021, 06, 27, 11, 00, 00))),
                new Article(
                        new Title("企業向けの職場接種と大学接種　対象や申請条件、使用するワクチンは？ 新型コロナ職域接種"),
                        "https://www.fukuishimbun.co.jp/articles/-/1336523",
                        new DateTime(LocalDateTime.of(2021, 06, 27, 10, 50, 00)))
        ]

        when:
        final actual = sut.getArticles().collectList().block()

        then:
        actual == expectedResults
    }

}
