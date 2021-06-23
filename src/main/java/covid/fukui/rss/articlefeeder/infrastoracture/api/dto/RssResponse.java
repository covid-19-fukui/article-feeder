package covid.fukui.rss.articlefeeder.infrastoracture.api.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
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
@XmlRootElement(name = "RDF", namespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#")
public class RssResponse implements Serializable {

    private static final long serialVersionUID = -1107810883534940688L;

    @XmlElement(name = "item", namespace = "http://purl.org/rss/1.0/")
    private List<Item> item;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ToString
    public static class Item implements Serializable {

        private static final long serialVersionUID = -8175683059155260618L;

        @XmlElement(name = "title", namespace = "http://purl.org/rss/1.0/")
        private String title;

        @XmlElement(name = "link", namespace = "http://purl.org/rss/1.0/")
        private String link;

        @XmlElement(name = "description", namespace = "http://purl.org/rss/1.0/")
        private String description;

        @XmlElement(name = "date", namespace = "http://purl.org/dc/elements/1.1/")
        private String date;
    }
}
