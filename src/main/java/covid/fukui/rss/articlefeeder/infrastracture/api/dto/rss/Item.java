package covid.fukui.rss.articlefeeder.infrastracture.api.dto.rss;

import covid.fukui.rss.articlefeeder.domain.model.article.Article;
import covid.fukui.rss.articlefeeder.domain.model.type.DateTime;
import covid.fukui.rss.articlefeeder.domain.model.type.Link;
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
class Item implements Serializable {

    private static final long serialVersionUID = 5125946939687084306L;
    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "link")
    private String link;

    @XmlElement(name = "pubDate")
    private String pubDate;

    /**
     * Articleドメインを生成する
     *
     * @return Articleドメイン
     */
    Article toArticle() {
        return Article
                .of(OriginalTitle.from(title), Link.from(link), DateTime.from(pubDate));
    }
}