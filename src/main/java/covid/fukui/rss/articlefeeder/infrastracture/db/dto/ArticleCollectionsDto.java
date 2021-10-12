package covid.fukui.rss.articlefeeder.infrastracture.db.dto;

import covid.fukui.rss.articlefeeder.domain.model.article.SavedArticleCount;
import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ArticleCollectionsDto implements Serializable {

    private static final long serialVersionUID = 5512989128146219528L;

    @NonNull
    private final List<ArticleCollectionDto> articleCollectionsDto;

    /**
     * ファクトリメソッド
     *
     * @param articleCollectionsDto リスト
     * @return メソッド
     */
    public static ArticleCollectionsDto from(
            final List<ArticleCollectionDto> articleCollectionsDto) {
        return new ArticleCollectionsDto(articleCollectionsDto);
    }

    /**
     * fluxからArticleCollectionsDtoを生成するファクトリメソッド
     *
     * @param articleCollectionDtoFlux flux
     * @return ArticleCollectionsDto
     */
    public static ArticleCollectionsDto fromFlux(
            final Flux<ArticleCollectionDto> articleCollectionDtoFlux) {
        final var articleCollectionList = articleCollectionDtoFlux.collectList().block();

        if (Objects.isNull(articleCollectionList)) {
            throw new InvalidArgumentException("Fluxの値がnullです。");
        }

        return new ArticleCollectionsDto(articleCollectionList);
    }

    /**
     * Fluxとして返す
     *
     * @return Flux
     */
    public Flux<ArticleCollectionDto> asFlux() {
        return Flux.fromIterable(articleCollectionsDto);
    }

    /**
     * @return
     */
    public SavedArticleCount size() {
        return SavedArticleCount.from(CollectionUtils.size(articleCollectionsDto));
    }
}
