package covid.fukui.rss.articlefeeder.application.service;

import com.google.cloud.Timestamp;
import covid.fukui.rss.articlefeeder.domain.constant.Keyword;
import covid.fukui.rss.articlefeeder.domain.model.Article;
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import covid.fukui.rss.articlefeeder.infrastracture.api.dto.RssResponse;
import covid.fukui.rss.articlefeeder.infrastracture.db.dto.ArticleCollection;
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

    private final MessageDigest md5;

    /**
     * 記事データを取得し、firestoreに保存する
     *
     * @return firestoreに保存された記事データ
     */
    @NonNull
    public Flux<ArticleCollection> feedArticles() {

        final var articleCollections = rssRepository.getArticles()
                .map(RssResponse::getItem)
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

        final var dateTime =
                LocalDateTime.parse(item.getDate(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return Article.builder()
                .title(item.getTitle())
                .link(item.getLink())
                .description(item.getDescription())
                .datetime(dateTime)
                .build();
    }

    /**
     * ArticleCollectionの生成
     *
     * @param article Articleドメイン
     * @return ArticleCollection
     */
    @NonNull
    private ArticleCollection buildArticleCollection(final Article article) {

        final var articleKey =
                Hex.encodeHexString(md5.digest(article.getTitle().getBytes()));

        final var datetime =
                Timestamp.of(Date.from(
                        article.getDatetime().atZone(ZoneId.systemDefault()).toInstant()));

        return ArticleCollection.builder()
                .articleKey(articleKey)
                .title(article.getTitle())
                .link(article.getLink())
                .description(article.getDescription())
                .datetime(datetime)
                .build();
    }

    /**
     * キーワードのいずれかを含むかどうか判定する
     *
     * @param article 記事
     * @return キーワードを含む場合、trueを返す
     */
    private boolean isTopicOfCovid19(final Article article) {

        final var title = article.getTitle();
        final var description = article.getDescription();

        return Keyword.includeKeyword(title) || Keyword.includeKeyword(description);
    }
}
