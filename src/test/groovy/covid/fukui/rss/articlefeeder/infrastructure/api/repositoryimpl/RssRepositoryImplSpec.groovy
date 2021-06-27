package covid.fukui.rss.articlefeeder.infrastructure.api.repositoryimpl

import covid.fukui.rss.articlefeeder.infrastracture.api.dto.RssResponse
import covid.fukui.rss.articlefeeder.infrastracture.api.repositoryimpl.RssRepositoryImpl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification

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

    final "getArticles"() {
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

        // 期待値を作成する
        final expectedResults = new RssResponse(
                channel: new RssResponse.Channel(item: [
                        RssResponse.Item.builder()
                                .title("新型コロナウイルスのワクチン接種　ファイザー、モデルナ、アストラゼネカ…対象年齢、副反応など違いは？")
                                .link("https://www.fukuishimbun.co.jp/articles/-/1336511")
                                .pubDate("Sun, 27 Jun 2021 11:00:00 +0900")
                                .build(),
                        RssResponse.Item.builder()
                                .title("企業向けの職場接種と大学接種　対象や申請条件、使用するワクチンは？ 新型コロナ職域接種")
                                .link("https://www.fukuishimbun.co.jp/articles/-/1336523")
                                .pubDate("Sun, 27 Jun 2021 10:50:00 +0900")
                                .build(),
                ]))

        when:
        final actual = sut.getArticles().block()

        then:
        actual == expectedResults
    }

}
