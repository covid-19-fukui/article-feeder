package covid.fukui.rss.articlefeeder.domain.repository.api;

import covid.fukui.rss.articlefeeder.infrastoracture.api.dto.RssResponse;
import reactor.core.publisher.Mono;

public interface RssRepository {

    Mono<RssResponse> getArticles();
}
