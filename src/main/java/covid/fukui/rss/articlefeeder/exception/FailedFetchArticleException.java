package covid.fukui.rss.articlefeeder.exception;

public class FailedFetchArticleException extends ArticleFeederException {

    private static final long serialVersionUID = -6300425394335412119L;

    public FailedFetchArticleException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
