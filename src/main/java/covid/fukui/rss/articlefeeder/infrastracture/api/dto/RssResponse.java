package covid.fukui.rss.articlefeeder.infrastracture.api.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@ToString
@XmlRootElement(name = "rss")
@EqualsAndHashCode
public class RssResponse implements Serializable {

    private static final long serialVersionUID = -1107810883534940688L;

    @XmlElement(name = "channel")
    private Channel channel;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Channel implements Serializable {

        private static final long serialVersionUID = -3408525808124122384L;

        @XmlElement(name = "item")
        private List<Item> item;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    @Builder
    public static class Item implements Serializable {

        private static final long serialVersionUID = -8175683059155260618L;

        @XmlElement(name = "title")
        private String title;

        @XmlElement(name = "link")
        private String link;

        @XmlElement(name = "pubDate")
        private String pubDate;
    }
}
