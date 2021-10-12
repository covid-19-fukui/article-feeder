package covid.fukui.rss.articlefeeder.presentation.function

import covid.fukui.rss.articlefeeder.application.service.ArticleService
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository
import covid.fukui.rss.articlefeeder.exception.FailedFetchArticleException
import covid.fukui.rss.articlefeeder.exception.FailedSaveArticleException
import covid.fukui.rss.articlefeeder.presentation.dto.PubSubMessage
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
@ActiveProfiles("test")
class ArticleFeederSubscriberSpec extends Specification {

    @MockBean
    private FirestoreRepository firestoreRepository

    @MockBean
    private ArticleService articleService

    @Autowired
    private ArticleFeederSubscriber sut

    final "サービス層で例外発生しない場合、正常終了する"() {
        given:
        final pubSubMessage = new PubSubMessage("", Map.of(), "", "")

        Mockito.when(articleService.feedArticles()).thenReturn(Mono.empty())

        when:
        sut.pubSubFunction().accept(pubSubMessage)

        then:
        noExceptionThrown()
    }

    @Unroll
    final "サービス層で例外発生した場合、異常終了する"() {
        given:
        final pubSubMessage = new PubSubMessage("", Map.of(), "", "");

        Mockito.when(articleService.feedArticles())
                .thenReturn(Mono.error(excetion))

        when:
        sut.pubSubFunction().accept(pubSubMessage)

        then:
        final actualException = thrown(expectedException)
        verifyAll(actualException) {
            getMessage() == message
            getCause() instanceof RuntimeException
        }

        where:
        excetion                                                               || expectedException           | message
        new FailedFetchArticleException("記事の取得に失敗しました", new RuntimeException()) | FailedFetchArticleException | "記事の取得に失敗しました"
        new FailedSaveArticleException("記事の保存に失敗しました", new RuntimeException())  | FailedSaveArticleException  | "記事の保存に失敗しました"

    }
}
