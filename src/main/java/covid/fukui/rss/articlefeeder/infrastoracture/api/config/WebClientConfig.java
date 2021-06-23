package covid.fukui.rss.articlefeeder.infrastoracture.api.config;

import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * WebClientの設定クラス
 */
@Configuration
public class WebClientConfig {
    /**
     * connector
     */
    private static final BiFunction<Duration, Duration, ReactorClientHttpConnector> CONNECTOR =
            (connectTimeout, readTimeout) -> new ReactorClientHttpConnector(HttpClient.create()
                    .responseTimeout(readTimeout)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout.toMillisPart())
                    .resolver(DefaultAddressResolverGroup.INSTANCE)
                    .compress(true)
            );

    /**
     * strategy
     */
    private static final Function<Integer, ExchangeStrategies> STRATEGY =
            (maxInMemorySize) -> ExchangeStrategies.builder()
                    .codecs(configurer -> {
                        configurer.defaultCodecs().maxInMemorySize(maxInMemorySize);
                        configurer.defaultCodecs().jaxb2Encoder(new Jaxb2XmlEncoder());
                        configurer.defaultCodecs().jaxb2Decoder(new Jaxb2XmlDecoder());
                    })
                    .build();

    /**
     * 福井新聞RSSの取得エンドポイント
     *
     * @return UDB
     */
    @Bean
    @ConfigurationProperties(prefix = "extension.api.rss")
    public ApiSetting rssSetting() {
        return new ApiSetting();
    }

    /**
     * 福井新聞RSSのクライアント
     *
     * @param apiSetting API設定
     * @return WebClient
     */
    @Bean
    @NonNull
    public WebClient rssClient(
            @Qualifier(value = "rssSetting") final ApiSetting apiSetting) {

        final ReactorClientHttpConnector connector = CONNECTOR
                .apply(apiSetting.getConnectTimeout(), apiSetting.getReadTimeout());

        return WebClient.builder()
                .baseUrl(apiSetting.getBaseUrl())
                .exchangeStrategies(STRATEGY.apply(apiSetting.getMaxInMemorySize()))
                .clientConnector(connector)
                .build();
    }

}
