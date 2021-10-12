package covid.fukui.rss.articlefeeder.domain.service;

import covid.fukui.rss.articlefeeder.domain.model.type.title.EncryptedTitle;
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleDomainService {

    private final MessageDigest sha256;

    /**
     * 記事タイトルをSha256で暗号化する
     *
     * @param originalTitle 記事タイトル
     * @return sha256で暗号化されたタイトル
     */
    @NonNull
    public EncryptedTitle encryptWithSha256(final OriginalTitle originalTitle) {
        final var sha256TitleText = Hex
                .encodeHexString(
                        sha256.digest(originalTitle.toString().getBytes(StandardCharsets.UTF_8)));

        return EncryptedTitle.from(sha256TitleText);
    }
}
