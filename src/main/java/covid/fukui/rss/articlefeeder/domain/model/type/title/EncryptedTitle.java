package covid.fukui.rss.articlefeeder.domain.model.type.title;

import covid.fukui.rss.articlefeeder.exception.InvalidArgumentException;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper = false)
public class EncryptedTitle extends Title {

    private static final long serialVersionUID = 9206328061147345518L;

    private static final Pattern SHA256_PATTERN = Pattern.compile("^[a-z0-9]{64}$");

    private EncryptedTitle(final String value) {
        super(value);
    }

    /**
     * ファクトリメソッド
     *
     * @param encrypted 暗号化された文字列
     * @return 暗号化文字列タイトル
     * @throws InvalidArgumentException 引数が異常フォーマットの場合の例外
     */
    @NonNull
    public static EncryptedTitle from(final String encrypted) throws InvalidArgumentException {
        if (SHA256_PATTERN.matcher(encrypted).matches()) {
            return new EncryptedTitle(encrypted);
        }

        throw new InvalidArgumentException("フォーマット異常:EncryptedTitle");
    }
}
