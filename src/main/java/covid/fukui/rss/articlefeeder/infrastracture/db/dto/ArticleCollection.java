package covid.fukui.rss.articlefeeder.infrastracture.db.dto;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.cloud.gcp.data.firestore.Document;
import org.springframework.lang.NonNull;

@Document(collectionName = "article")
@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Builder
public class ArticleCollection implements Serializable {

    private static final long serialVersionUID = 5512989128146219528L;

    /**
     * firestoreのキー
     */
    @DocumentId
    @NonNull
    private final String articleKey;

    @NonNull
    private final String title;

    @NonNull
    private final String link;

    /**
     * 記事の掲載時刻
     */
    @NonNull
    @ServerTimestamp
    private final Timestamp datetime;

}
