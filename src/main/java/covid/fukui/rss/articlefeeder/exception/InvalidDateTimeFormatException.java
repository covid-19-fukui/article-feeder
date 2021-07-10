package covid.fukui.rss.articlefeeder.exception;

/**
 * DateTimeの変換に失敗した場合の例外
 */
public class InvalidDateTimeFormatException extends ArticleFeederException {

    private static final long serialVersionUID = 5337756106684712403L;


    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param cause   Throwable
     */
    public InvalidDateTimeFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
