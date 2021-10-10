package covid.fukui.rss.articlefeeder.domain.model.type.title;

import lombok.EqualsAndHashCode;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper = false)
public class OriginalTitle extends Title {

    private static final long serialVersionUID = -8391144684246370877L;

    private OriginalTitle(final String value) {
        super(value);
    }

    @NonNull
    public static OriginalTitle from(final String value) {
        return new OriginalTitle(value);
    }

}
