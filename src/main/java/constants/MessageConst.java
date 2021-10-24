package constants;

/**
 * 各出力メッセージを定義するEnumクラス
 *
 */
public enum MessageConst {

    //認証
    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    //バリデーション
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOUSER_USERID("ユーザーIDを入力してください。"),
    E_USER_USERID_EXIST("入力されたユーザーIDの情報は既に存在しています。"),

    E_NOWORKBOOK_NAME("問題集名を入力してください。"),
    E_WORKBOOK_NAME_EXIST("入力された問題集名の情報はすでに存在しています。");

    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getMessage() {
        return this.text;
    }
}