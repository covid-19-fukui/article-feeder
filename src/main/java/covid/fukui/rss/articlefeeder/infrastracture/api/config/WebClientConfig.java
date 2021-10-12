package covid.fukui.rss.articlefeeder.infrastracture.api.config;

import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import java.time.Duration;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.http.codec.xml.Jaxb2XmlEncoder;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * WebClientの設定クラス
 */
@Configuration
@Slf4j
public class WebClientConfig {
    /**
     * connector
     */
    private static final BiFunction<Duration, Duration, ReactorClientHttpConnector> CONNECTOR =
            (connectTimeout, readTimeout) -> {
                final var httpClient = HttpClient.create()
                        .responseTimeout(readTimeout)
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout.toMillisPart())
                        .resolver(DefaultAddressResolverGroup.INSTANCE)
                        .compress(true);

                return new ReactorClientHttpConnector(httpClient);
            };

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
     * リクエスト情報出力用
     */
    private static final ExchangeFilterFunction WEBCLIENT_REQUEST_LOGGER = ExchangeFilterFunction
            .ofRequestProcessor(
                    clientRequest -> {
                        log.info("{}[{}] {}",
                                clientRequest.logPrefix(), clientRequest.method(),
                                clientRequest.url());

                        final var existHeaders = !clientRequest.headers().isEmpty();

                        if (existHeaders) {

                            final var headerRemovedCookie =
                                    clientRequest.headers().keySet().stream()
                                            .filter(key -> !HttpHeaders.COOKIE.equals(key))
                                            .map(key -> key + "="
                                                    + clientRequest.headers().getFirst(key))
                                            .collect(Collectors.joining(","));

                            final var method = clientRequest.method().toString();

                            log.info(
                                    "{}[{}] Request Headers: {{}}", clientRequest.logPrefix(),
                                    method,
                                    headerRemovedCookie

                            );
                        }
                        return Mono.just(clientRequest);
                    }
            );

    /**
     * レスポンス情報出力用
     */
    private static final ExchangeFilterFunction WEBCLIENT_RESPONSE_LOGGER = ExchangeFilterFunction
            .ofResponseProcessor(
                    clientResponse -> {
                        log.info("{}[{}] {}",
                                clientResponse.logPrefix(), clientResponse.statusCode().value(),
                                clientResponse.headers().asHttpHeaders());
                        return Mono.just(clientResponse);
                    }
            );

    /**
     * 福井新聞RSSの取得エンドポイント
     *
     * @return api設定値
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

        final var connector = CONNECTOR
                .apply(apiSetting.getConnectTimeout(), apiSetting.getReadTimeout());

        return WebClient.builder()
                .baseUrl(apiSetting.getBaseUrl())
                .filter(WEBCLIENT_REQUEST_LOGGER)
                .filter(WEBCLIENT_RESPONSE_LOGGER)
                .exchangeStrategies(STRATEGY.apply(apiSetting.getMaxInMemorySize()))
                .clientConnector(connector)
                .build();
    }

}
