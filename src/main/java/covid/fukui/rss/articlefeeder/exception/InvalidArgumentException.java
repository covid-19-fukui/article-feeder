package covid.fukui.rss.articlefeeder.exception;

public class InvalidArgumentException extends ArticleFeederException {

    private static final long serialVersionUID = -6300425394335412119L;

    public InvalidArgumentException(final String message) {
        super(message);
    }

    public InvalidArgumentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
