package covid.fukui.rss.articlefeeder.infrastructure.db.repositoryimpl

import covid.fukui.rss.articlefeeder.domain.model.article.Article
import covid.fukui.rss.articlefeeder.domain.model.type.Count
import covid.fukui.rss.articlefeeder.domain.model.type.DateTime
import covid.fukui.rss.articlefeeder.domain.model.type.Link
import covid.fukui.rss.articlefeeder.domain.model.type.title.EncryptedTitle
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle
import covid.fukui.rss.articlefeeder.domain.service.TitleDomainService
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollectionDto
import covid.fukui.rss.articlefeeder.infrastracture.db.repositoryimpl.FirestoreRepositoryImpl
import org.springframework.cloud.gcp.data.firestore.FirestoreTemplate
import reactor.core.publisher.Flux
import spock.lang.Specification

import java.time.LocalDateTime

class FirestoreRepositoryImplSpec extends Specification {

    private FirestoreRepositoryImpl sut
    private FirestoreTemplate firestoreTemplate
    private TitleDomainService titleService

    final setup() {
        firestoreTemplate = Mock(FirestoreTemplate)
        titleService = Mock(TitleDomainService)
        sut = new FirestoreRepositoryImpl(firestoreTemplate, titleService)
    }

    final "insertBulkArticleメソッド"() {
        given:
        titleService.encryptWithSha256(*_) >> { OriginalTitle title ->
            return new EncryptedTitle("encrypted")
        }
        firestoreTemplate.saveAll(*_) >> { Flux<ArticleCollectionDto> dto ->
            return dto
        }

        final articles = Flux.fromIterable([
                new Article(new OriginalTitle("コロナ"), Link.from("https://localhost"), new DateTime(LocalDateTime.of(2021, 07, 01, 00, 00, 00))),
                new Article(new OriginalTitle("感染"), Link.from("https://localhost2"), new DateTime(LocalDateTime.of(2021, 07, 02, 00, 00, 00)))
        ])

        when:
        final actual = sut.insertBulkArticle(articles).block()

        then:
        actual == new Count(2)
    }
}
