package covid.fukui.rss.articlefeeder.infrastracture.api.config;

import java.io.Serializable;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * API設定値
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
class ApiSetting implements Serializable {

    private static final long serialVersionUID = 2591264824531899180L;

    /**
     * URL
     */
    private String baseUrl;

    /**
     * 接続タイムアウト
     */
    private Duration connectTimeout;

    /**
     * 読み込みタイムアウト
     */
    private Duration readTimeout;

    /**
     * 最大メモリサイズ
     */
    private Integer maxInMemorySize;

}
