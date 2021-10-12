package covid.fukui.rss.articlefeeder.infrastracture.api.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.model.article.Articles;
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.exception.FailedFetchArticleException;
import covid.fukui.rss.articlefeeder.infrastracture.api.dto.rss.RssResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
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
    public Mono<Articles> getArticles() throws FailedFetchArticleException {

        final var rssResponseMono =
                rssClient.get().retrieve().bodyToMono(RssResponse.class)
                        .onErrorResume(exception -> Mono.error(
                                new FailedFetchArticleException("記事の取得に失敗しました:", exception)));

        return rssResponseMono.map(RssResponse::toArticles);
    }
}
