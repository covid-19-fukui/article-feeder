package covid.fukui.rss.articlefeeder.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Configuration
public class Sha256Config {

    /**
     * sha256のbean取得
     *
     * @return clockのbean
     */
    @Bean
    @NonNull
    public MessageDigest sha256() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256");
    }

}
