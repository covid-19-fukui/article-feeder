package covid.fukui.rss.articlefeeder.config;

import covid.fukui.rss.articlefeeder.exception.FailedGenerateBeanException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class Sha256Config {

    /**
     * クロックのbean取得
     *
     * @return clockのbean
     */
    @Bean
    @NonNull
    public MessageDigest md5() throws FailedGenerateBeanException {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (final NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new FailedGenerateBeanException("md5のbean生成に失敗しました", noSuchAlgorithmException);
        }
    }

}
