package covid.fukui.rss.articlefeeder.exception;

public class FailedGenerateBeanException extends RuntimeException {

    private static final long serialVersionUID = 5337756106684712403L;


    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param cause   Throwable
     */
    public FailedGenerateBeanException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
