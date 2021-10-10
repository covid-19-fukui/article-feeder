package covid.fukui.rss.articlefeeder.presentation.function;

import covid.fukui.rss.articlefeeder.application.service.ArticleService;
import covid.fukui.rss.articlefeeder.exception.FailedSaveArticleException;
import covid.fukui.rss.articlefeeder.presentation.dto.PubSubMessage;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ArticleFeederSubscriber {

    private final ArticleService articleService;

    /**
     * pubsubでメッセージを受け取る関数
     *
     * @return PubSubMessageを引数に持つ関数
     */
    @Bean
    @NonNull
    public Consumer<PubSubMessage> pubSubFunction() throws FailedSaveArticleException {
        return message -> {
            log.info("更新開始");

            articleService.feedArticles().onErrorResume(Mono::error).block();

            log.info("更新終了");
        };
    }
}
