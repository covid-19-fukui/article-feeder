package covid.fukui.rss.articlefeeder.infrastructure.db.repositoryimpl

import covid.fukui.rss.articlefeeder.domain.model.Article
import covid.fukui.rss.articlefeeder.domain.service.TitleService
import covid.fukui.rss.articlefeeder.domain.type.Count
import covid.fukui.rss.articlefeeder.domain.type.DateTime
import covid.fukui.rss.articlefeeder.domain.type.Title
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionDto
import covid.fukui.rss.articlefeeder.infrastracture.db.repositoryimpl.FirestoreRepositoryImpl
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate
import reactor.core.publisher.Flux
import spock.lang.Specification

import java.time.LocalDateTime

class FirestoreRepositoryImplSpec extends Specification {

    private FirestoreRepositoryImpl sut
    private FirestoreTemplate firestoreTemplate
    private TitleService titleService

    final setup() {
        firestoreTemplate = Mock(FirestoreTemplate)
        titleService = Mock(TitleService)
        sut = new FirestoreRepositoryImpl(firestoreTemplate, titleService)
    }

    final "insertBulkArticleメソッド"() {
        given:
        titleService.encryptWithMd5(*_) >> { Title title ->
            return "encrypted: " + title.toString()
        }
        firestoreTemplate.saveAll(*_) >> { Flux<ArticleCollectionDto> dto ->
            return dto
        }

        final articles = Flux.fromIterable([
                new Article(new Title("コロナ"), "link1", new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new Title("感染"), "link2", new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ])

        when:
        final actual = sut.insertBulkArticle(articles).block()

        then:
        actual == new Count(2)
    }
}
