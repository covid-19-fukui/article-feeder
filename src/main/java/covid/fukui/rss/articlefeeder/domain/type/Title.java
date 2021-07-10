package covid.fukui.rss.articlefeeder.domain.type;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Title implements Serializable {

    private static final long serialVersionUID = -1037080036933270712L;
    private final String value;

    @NonNull
    public static Title from(final String value) {
        return new Title(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
