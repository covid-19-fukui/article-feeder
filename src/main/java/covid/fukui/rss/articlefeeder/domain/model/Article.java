package covid.fukui.rss.articlefeeder.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@RequiredArgsConstructor
@Getter
@ToString
@Builder
public class Article implements Serializable {

    private static final long serialVersionUID = -38646047329248286L;

    @NonNull
    private final String title;

    @NonNull
    private final String link;

    @NonNull
    private final LocalDateTime datetime;
}
