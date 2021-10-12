package covid.fukui.rss.articlefeeder.domain.model.article;

import com.google.cloud.Timestamp;
import covid.fukui.rss.articlefeeder.domain.model.constant.Keyword;
import covid.fukui.rss.articlefeeder.domain.model.type.DateTime;
import covid.fukui.rss.articlefeeder.domain.model.type.Link;
import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

/**
 * 記事ドメイン
 */
@Builder(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Article implements Serializable {

    private static final long serialVersionUID = -38646047329248286L;

    @NonNull
    @Getter
    private final OriginalTitle originalTitle;

    @NonNull
    private final Link link;

    @NonNull
    private final DateTime datetime;

    /**
     * Articleドメインの生成
     *
     * @return Articleドメイン
     */
    @NonNull
    public static Article of(final OriginalTitle originalTitle, final Link link,
                             final DateTime dateTime) {

        return Article.builder()
                .originalTitle(originalTitle)
                .link(link)
                .datetime(dateTime)
                .build();
    }

    /**
     * キーワードのいずれかを含むかどうか判定する
     *
     * @return キーワードを含む場合、trueを返す
     */
    public boolean isTopicOfCovid19() {
        return Keyword.includeKeyword(originalTitle);
    }

    public Timestamp getTimestamp() {
        return datetime.convertToTimestamp();
    }

    public String getTitle() {
        return originalTitle.toString();
    }

    public String getLink() {
        return link.toString();
    }
}
