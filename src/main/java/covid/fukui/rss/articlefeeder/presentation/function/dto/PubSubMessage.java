package covid.fukui.rss.articlefeeder.presentation.function.dto;

import java.io.Serializable;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Google Cloud PubsubからPublishされるメッセージ
 */
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class PubSubMessage implements Serializable {

    private static final long serialVersionUID = -8974036523793712602L;

    /**
     * pub/subのペイロード
     */
    private final String data;

    /**
     * attributes
     */
    private final Map<String, String> attributes;

    /**
     * メッセージID
     */
    private final String messageId;

    /**
     * publishされた時刻
     */
    private final String publishTime;
}