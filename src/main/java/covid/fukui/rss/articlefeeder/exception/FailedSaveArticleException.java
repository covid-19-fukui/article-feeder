package covid.fukui.rss.articlefeeder.exception;

public class FailedSaveArticleException extends ArticleFeederException {

    private static final long serialVersionUID = -6300425394335412119L;

    public FailedSaveArticleException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
