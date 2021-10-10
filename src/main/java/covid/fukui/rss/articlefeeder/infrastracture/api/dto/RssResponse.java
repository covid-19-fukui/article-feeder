package covid.fukui.rss.articlefeeder.infrastracture.api.dto;

import covid.fukui.rss.articlefeeder.domain.model.article.Article;
import covid.fukui.rss.articlefeeder.domain.model.type.DateTime;
import covid.fukui.rss.articlefeeder.domain.model.type.Link;
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 福井新聞のRSSレスポンス
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@XmlRootElement(name = "rss")
@EqualsAndHashCode
public class RssResponse implements Serializable {

    private static final long serialVersionUID = -1107810883534940688L;

    @XmlElement(name = "channel")
    private Channel channel;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Channel implements Serializable {

        private static final long serialVersionUID = -3408525808124122384L;

        @XmlElement(name = "item")
        private List<Item> items;

        /**
         * 変更不可なitemのリストを返す
         *
         * @return itemのリスト(変更不可)
         */
        public List<Item> getItems() {
            return Collections.unmodifiableList(items);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    public static class Item implements Serializable {

        private static final long serialVersionUID = -8175683059155260618L;

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
        public Article convertToArticle() {
            return Article
                    .of(OriginalTitle.from(title), Link.from(link), DateTime.from(pubDate));
        }
    }
}
