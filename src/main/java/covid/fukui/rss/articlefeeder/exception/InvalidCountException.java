package covid.fukui.rss.articlefeeder.exception;

/**
 * Countの生成に失敗した場合の例外
 */
public class InvalidCountException extends ArticleFeederException {

    private static final long serialVersionUID = 5337756106684712403L;


    /**
     * コンストラクタ
     *
     * @param message メッセージ
     */
    public InvalidCountException(final String message) {
        super(message);
    }
}
