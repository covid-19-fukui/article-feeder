package covid.fukui.rss.articlefeeder.infrastracture.api.dto.rss;

import covid.fukui.rss.articlefeeder.domain.model.article.Articles;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
class Channel implements Serializable {

    private static final long serialVersionUID = -2341316891947199446L;

    @XmlElement(name = "item")
    private List<Item> items;

    /**
     * 変更不可なitemのリストを返す
     *
     * @return itemのリスト(変更不可)
     */
    Articles toArticles() {
        final var articleList =
                items.stream().map(Item::toArticle).collect(Collectors.toUnmodifiableList());

        return Articles.from(articleList);
    }
}