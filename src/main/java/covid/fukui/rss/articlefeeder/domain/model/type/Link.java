package covid.fukui.rss.articlefeeder.domain.model.type;

import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.io.Serializable;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Link implements Serializable {

    private static final long serialVersionUID = 4473931324629885980L;

    private static final Pattern URL_PATTERN =
            Pattern.compile("^https?://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]+$");

    private final String value;

    /**
     * ファクトリメソッド
     *
     * @param url url
     * @return Link型クラス
     * @throws InvalidArgumentException 引数が異常フォーマットの場合の例外
     */
    public static Link from(final String url) throws InvalidArgumentException {
        if (URL_PATTERN.matcher(url).matches()) {
            return new Link(url);
        }

        throw new InvalidArgumentException("フォーマット異常:EncryptedTitle");
    }

    public String toString() {
        return value;
    }
}
