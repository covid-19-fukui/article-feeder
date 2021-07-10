package covid.fukui.rss.articlefeeder.domain.service;

import covid.fukui.rss.articlefeeder.domain.type.Title;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TitleService {

    private final MessageDigest sha256;

    /**
     * 記事タイトルをMd5で暗号化する
     *
     * @param title 記事タイトル
     * @return md5で暗号化されたタイトル
     */
    @NonNull
    public String encryptWithMd5(final Title title) {
        return Hex
                .encodeHexString(sha256.digest(title.toString().getBytes(StandardCharsets.UTF_8)));
    }
}
