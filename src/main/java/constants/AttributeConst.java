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
    USERS("users"),
    USER_COUNT("users_count"),
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
    WORKBOOK_COUNT("workbooks_count"),
    WORKBOOK_ID("workbook_id"),
    WORKBOOK_FLAG("workbook_flag"),
    WORKBOOK_NAME("workbook_name"),
    SESSION_WORKBOOK_ID("session_workbook_id"),

    //科目名フラグ
    ROLE_MATH("1"), //数学
    ROLE_LANGUAGE("2"), //国語
    ROLE_ENGLISH("3"), //英語
    ROLE_CHEMISTRY("4"), //化学
    ROLE_PHYSICS("5"), //物理
    ROLE_SOCIETY("6"), //社会

    //チャプター管理
    CHAPTER("chapter"),
    CHAPTERS("chapters"),
    CHAPTER_NAME("chapter_name"),
    CHAPTER_SORT("chapter_sort"),
    CHAPTER_ID("chapter_id"),
    CHAPTER_COUNT("chapters_count"),
    SESSION_CHAPTER_ID("session_chapter_id"),

    //問題番号管理
    NUMBER("number"),
    NUMBERS("numbers"),
    NUMBER_ID("number_id"),
    NUMBER_COUNT("numbers_count"),
    NUMBER_NUMBER("number_number"),
    FIRST_NUMBER("first_number"),
    LAST_NUMBER("last_number"),
    DELETE_NUMBER_ID("delete_number_id"),

    //回答結果管理
    RESULT("result"),
    RESULTS("results"),
    RESULT_COUNT("results_count"),
    ANSWER_FLAG("answer_flag"),
    ANSWER_FLAG_NUMBER("answer_flag_number"),

    //回答フラグ
    ROLE_MARU(0),
    ROLE_BATSU(1),
    ROLE_MADA(2);


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