package covid.fukui.rss.articlefeeder.domain.service

import covid.fukui.rss.articlefeeder.domain.type.Title
import spock.lang.Specification
import spock.lang.Unroll

import java.security.MessageDigest

class TitleServiceSpec extends Specification {

    private TitleService sut

    final setup() {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256")
        sut = new TitleService(sha256)
    }

    @Unroll
    final "encryptWithMd5"() {
        when:
        final actual = sut.encryptWithMd5(new Title("title1"))

        then:
        actual == "5018e26faaec56a465e61aaeb752c2984c8a6d6305c8496b3a95213fb20889c7"
    }
}
