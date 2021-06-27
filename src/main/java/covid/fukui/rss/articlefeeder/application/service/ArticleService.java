package covid.fukui.rss.articlefeeder.application.service;

import com.google.cloud.Timestamp;
import covid.fukui.rss.articlefeeder.domain.constant.Keyword;
import covid.fukui.rss.articlefeeder.domain.model.Article;
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import covid.fukui.rss.articlefeeder.infrastracture.api.dto.RssResponse;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Hex;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final RssRepository rssRepository;

    private final FirestoreRepository firestoreRepository;

    private final MessageDigest sha256;

    /**
     * 記事データを取得し、firestoreに保存する
     *
     * @return firestoreに保存された記事データ
     */
    @NonNull
    public Flux<ArticleCollection> feedArticles() {

        final var articleCollections = rssRepository.getArticles()
                .map(RssResponse::getChannel)
                .map(RssResponse.Channel::getItem)
                .flatMapMany(Flux::fromIterable)
                .map(this::buildArticle)
                .filter(this::isTopicOfCovid19)
                .map(this::buildArticleCollection);

        return firestoreRepository.insertBulkArticle(articleCollections);
    }

    /**
     * Articleドメインの生成
     *
     * @param item rssのレスポンスのitem属性
     * @return Articleドメイン
     */
    @NonNull
    private Article buildArticle(final RssResponse.Item item) {

        final var dateTime = parsePubDate(item.getPubDate());

        return Article.builder()
                .title(item.getTitle())
                .link(item.getLink())
                .datetime(dateTime)
                .build();
    }

    /**
     * RFC1123形式の日付時刻の文字列をLocalDateTimeに変換
     *
     * @param pubDate RFC1123形式の日付時刻の文字列
     * @return LocalDateTime
     */
    @NonNull
    private LocalDateTime parsePubDate(final String pubDate) {
        return LocalDateTime.parse(pubDate, DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    /**
     * ArticleCollectionの生成
     *
     * @param article Articleドメイン
     * @return ArticleCollection
     */
    @NonNull
    private ArticleCollection buildArticleCollection(final Article article) {

        final var articleKey = encryptWithMd5(article.getTitle());

        final var datetime = convertToTimestamp(article.getDatetime());

        return ArticleCollection.builder()
                .articleKey(articleKey)
                .title(article.getTitle())
                .link(article.getLink())
                .datetime(datetime)
                .build();
    }

    /**
     * 記事タイトルをMd5で暗号化する
     *
     * @param title 記事タイトル
     * @return md5で暗号化されたタイトル
     */
    @NonNull
    private String encryptWithMd5(final String title) {
        return Hex.encodeHexString(sha256.digest(title.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * LocalDateTimeをTimestampに変換する
     *
     * @param datetime LocalDateTime
     * @return Timestamp
     */
    @NonNull
    private Timestamp convertToTimestamp(final LocalDateTime datetime) {
        return Timestamp.of(Date.from(datetime.atZone(ZoneId.of("Japan")).toInstant()));
    }

    /**
     * キーワードのいずれかを含むかどうか判定する
     *
     * @param article 記事
     * @return キーワードを含む場合、trueを返す
     */
    private boolean isTopicOfCovid19(final Article article) {
        return Keyword.includeKeyword(article.getTitle());
    }
}
