package covid.fukui.rss.articlefeeder.infrastracture.api.dto.rss;

import covid.fukui.rss.articlefeeder.domain.model.article.Articles;
import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * 福井新聞のRSSレスポンス
 */
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "rss")
@EqualsAndHashCode
public class RssResponse implements Serializable {

    private static final long serialVersionUID = -1107810883534940688L;

    @XmlElement(name = "channel")
    private Channel channel;

    /**
     * Monoからクラスを生成するファクトリメソッド
     *
     * @param rssResponseMono Mono
     * @return RssResponse
     */
    public static RssResponse fromMono(final Mono<RssResponse> rssResponseMono) {
        final var rssResponse = rssResponseMono.block();

        if (Objects.isNull(rssResponse)) {
            throw new InvalidArgumentException("monoがnullです");
        }

        return rssResponse;
    }

    public Articles toArticles() {
        return channel.toArticles();
    }
}
