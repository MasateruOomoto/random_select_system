package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中のユーザー
    LOGIN_USER("login_user"),

    //ログイン画面
    LOGIN_ERR("loginError"),


    //ユーザー管理
    USER("user"),
    USERS("USERS"),
    USER_COUNT("user_count"),
    USER_ID("id"),
    USER_USER_ID("user_id"),
    USER_PASS("password"),
    USER_ADMIN_FLG("admin_flag"),

    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),


    //問題集管理
    WORKBOOK("workbook"),
    WORKBOOKS("workbooks"),
    WOR_ID("id"),
    WOR_KAMOKU_FLAG("kamoku_flag"),
    WOR_MONDAI_NAME("mondai_name"),

    //科目名フラグ
    ROLE_MATH("1"),
    ROLE_LANGUAGE("2"),
    ROLE_ENGLISH("3"),
    ROLE_CHEMISTRY("4"),
    ROLE_PHYSICS("5"),
    ROLE_SOCIETY("6"),

    //チャプター管理
    CHAPTER("chapter"),
    CHAPTERS("chapters"),
    CHA_ID("id");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}