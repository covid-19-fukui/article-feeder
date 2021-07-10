package covid.fukui.rss.articlefeeder.domain.model;

import covid.fukui.rss.articlefeeder.domain.constant.Keyword;
import covid.fukui.rss.articlefeeder.domain.type.DateTime;
import covid.fukui.rss.articlefeeder.domain.type.Title;
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
@Getter
@ToString
public class Article implements Serializable {

    private static final long serialVersionUID = -38646047329248286L;

    @NonNull
    private final Title title;

    @NonNull
    private final String link;

    @NonNull
    private final DateTime datetime;

    /**
     * Articleドメインの生成
     *
     * @return Articleドメイン
     */
    @NonNull
    public static Article of(final Title title, final String link, final DateTime dateTime) {

        return Article.builder()
                .title(title)
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
        return Keyword.includeKeyword(title.toString());
    }
}
