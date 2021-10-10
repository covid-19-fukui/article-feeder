package covid.fukui.rss.articlefeeder.domain.model.type.title;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Title implements Serializable {

    private static final long serialVersionUID = -1037080036933270712L;

    private final String value;

    public String toString() {
        return value;
    }
}
