package covid.fukui.rss.articlefeeder.domain.model.constant;

import covid.fukui.rss.articlefeeder.domain.model.type.title.OriginalTitle;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
public enum Keyword {
    COVID("コロナ"),
    INFECTION("感染"),
    SURGICAL_MASK("マスク"),
    VACCINATION("接種"),
    VIRUS("ウイルス");

    @NonNull
    private final String phrase;

    /**
     * キーワードのいずれかを含むかどうか判定する
     *
     * @param originalTitle 判定対象
     * @return キーワードを含む場合、trueを返す
     */
    public static boolean includeKeyword(final OriginalTitle originalTitle) {
        final var keywords = Keyword.values();

        final var sentence = originalTitle.toString();

        return Arrays.stream(keywords)
                .anyMatch(keyword -> StringUtils.contains(sentence, keyword.phrase));
    }
}
