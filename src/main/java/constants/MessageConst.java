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
    E_WORKBOOK_NAME_EXIST("入力された問題集名の情報はすでに存在しています。"),

    E_NOCHAPTER_NAME("チャプター名を入力してください。"),
    E_NOSORT("ソート番号を入力してください。"),
    E_NOTSORT("ソート番号に数値を入力してください"),
    E_CHAPTER_NAME_EXIST("入力されたチャプター名の情報はすでに存在しています。"),

    E_NONUMBER("問題番号を入力してください。"),
    E_NOTNUMBER("問題番号に数値を入力してください。"),
    E_NUMBER_ORDER("最初の数字の方が小さくなるように入力してください。");



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