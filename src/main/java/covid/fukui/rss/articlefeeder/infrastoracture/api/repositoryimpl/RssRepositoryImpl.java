package covid.fukui.rss.articlefeeder.infrastoracture.api.repositoryimpl;

import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.infrastoracture.api.dto.RssResponse;
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

    @Override
    public Mono<RssResponse> getArticles() {
        return rssClient.get().retrieve().bodyToMono(RssResponse.class);
    }
}
