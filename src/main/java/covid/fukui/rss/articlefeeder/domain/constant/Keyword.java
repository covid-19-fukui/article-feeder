package covid.fukui.rss.articlefeeder.domain.constant;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

@Getter
@RequiredArgsConstructor
public enum Keyword {
    COVID("コロナ"),
    INFECTION("感染"),
    SURGICAL_MASK("マスク");

    @NonNull
    private final String phrase;

    /**
     * キーワードのいずれかを含むかどうか判定する
     *
     * @param sentence 判定対象
     * @return キーワードを含む場合、trueを返す
     */
    public static boolean includeKeyword(final String sentence) {
        return Arrays.stream(Keyword.values())
                .anyMatch(keyword -> StringUtils.contains(sentence, keyword.getPhrase()));
    }
}
