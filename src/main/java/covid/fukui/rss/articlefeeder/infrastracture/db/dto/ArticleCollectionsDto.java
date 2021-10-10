package covid.fukui.rss.articlefeeder.infrastracture.db.dto;

import covid.fukui.rss.articlefeeder.domain.model.type.Count;
import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ArticleCollectionsDto implements Serializable {

    private static final long serialVersionUID = 5512989128146219528L;

    private final List<ArticleCollectionDto> articleCollectionsDto;

    public static ArticleCollectionsDto from(
            final List<ArticleCollectionDto> articleCollectionsDto) {
        return new ArticleCollectionsDto(articleCollectionsDto);
    }

    public Count size() {
        return Count.from(CollectionUtils.size(articleCollectionsDto));
    }
}
