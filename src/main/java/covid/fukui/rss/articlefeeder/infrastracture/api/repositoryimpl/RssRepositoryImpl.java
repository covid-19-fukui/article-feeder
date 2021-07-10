package covid.fukui.rss.articlefeeder.infrastracture.api.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.model.Article;
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.infrastracture.api.dto.RssResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * RSS情報を取得するRepository実装クラス
 */
@Repository
@RequiredArgsConstructor
public class RssRepositoryImpl implements RssRepository {

    private final WebClient rssClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public Flux<Article> getArticles() {

        final Mono<RssResponse> articlesMono =
                rssClient.get().retrieve().bodyToMono(RssResponse.class);

        return articlesMono.map(RssResponse::getChannel)
                .map(RssResponse.Channel::getItems)
                .flatMapMany(Flux::fromIterable)
                .map(RssResponse.Item::convertToArticle);
    }
}
