package covid.fukui.rss.articlefeeder.application.service;

import covid.fukui.rss.articlefeeder.domain.model.article.Articles;
import covid.fukui.rss.articlefeeder.domain.model.article.SavedArticleCount;
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final RssRepository rssRepository;

    private final FirestoreRepository firestoreRepository;

    /**
     * 記事データを取得し、firestoreに保存する
     */
    @NonNull
    public void feedArticles() {
        final var articles = getArticles();

        final var countMono = firestoreRepository.insertBulkArticle(articles);

        SavedArticleCount.fromMonoSync(countMono).logCount();
    }

    /**
     * 記事情報の取得
     *
     * @return 記事リスト(Flux)
     */
    @NonNull
    private Articles getArticles() {
        final var articlesMono = rssRepository.getArticles();

        return Articles.fromMonoSync(articlesMono).filterCovid19();
    }
}
