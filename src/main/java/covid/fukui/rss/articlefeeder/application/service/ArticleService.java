package covid.fukui.rss.articlefeeder.application.service;

import covid.fukui.rss.articlefeeder.domain.model.Article;
import covid.fukui.rss.articlefeeder.domain.model.Articles;
import covid.fukui.rss.articlefeeder.domain.repository.api.RssRepository;
import covid.fukui.rss.articlefeeder.domain.repository.db.FirestoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final RssRepository rssRepository;

    private final FirestoreRepository firestoreRepository;

    /**
     * 記事データを取得し、firestoreに保存する
     *
     * @return firestoreに保存された記事データ
     */
    @NonNull
    public Mono<Void> feedArticles() {
        return firestoreRepository.insertBulkArticle(getArticles())
                .doOnNext(count -> log.info("更新された記事数:" + count)).then();
    }

    /**
     * 記事情報の取得
     *
     * @return 記事リスト(Flux)
     */
    @NonNull
    private Flux<Article> getArticles() {
        return Articles.from(rssRepository.getArticles()).getCovid19Articles();
    }


}
