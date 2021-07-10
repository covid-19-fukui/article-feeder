package covid.fukui.rss.articlefeeder.exception;

/**
 * アプリケーションの例外抽象クラス
 */
public abstract class ArticleFeederException extends RuntimeException {

    private static final long serialVersionUID = 5337756106684712403L;


    /**
     * コンストラクタ
     *
     * @param message メッセージ
     * @param cause   Throwable
     */
    public ArticleFeederException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     *
     * @param message メッセージ
     */
    public ArticleFeederException(final String message) {
        super(message);
    }
}
