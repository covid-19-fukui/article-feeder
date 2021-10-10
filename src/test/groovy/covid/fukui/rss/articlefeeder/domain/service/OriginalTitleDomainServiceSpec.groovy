package covid.fukui.rss.articlefeeder.domain.service

import covid.fukui.rss.articlefeeder.domain.model.type.title.EncryptedTitle
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle
import spock.lang.Specification
import spock.lang.Unroll

import java.security.MessageDigest

class OriginalTitleDomainServiceSpec extends Specification {

    private TitleDomainService sut

    final setup() {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256")
        sut = new TitleDomainService(sha256)
    }

    @Unroll
    final "encryptWithMd5"() {
        when:
        final actual = sut.encryptWithSha256(new OriginalTitle("title1"))

        then:
        actual == new EncryptedTitle("5018e26faaec56a465e61aaeb752c2984c8a6d6305c8496b3a95213fb20889c7")
    }
}
