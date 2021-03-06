package constants;

public enum ForwardConst {
    //action
    ACT("action"),

    ACT_TOP("Top"),
    ACT_USER("User"),
    ACT_WOR("Workbook"),
    ACT_CHA("Chapter"),
    ACT_NUM("Number"),
    ACT_AUTH("Auth"),
    ACT_RES("Result"),


    //command
    CMD("command"),
    CMD_NONE(""),

    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),
    CMD_INDEX_STUDENT("indexStudent"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_LOGIN("login/login"),
    FW_USER_INDEX("users/index"),
    FW_USER_SHOW("users/show"),
    FW_USER_NEW("users/new"),
    FW_USER_EDIT("users/edit"),
    FW_USER_DESTROY("users/destroy"),

    FW_WOR_INDEX("workbooks/index"),
    FW_WOR_NEW("workbooks/new"),
    FW_WOR_EDIT("workbooks/edit"),
    FW_WOR_DESTROY("workbooks/destroy"),

    FW_CHA_INDEX("chapters/index"),
    FW_CHA_NEW("chapters/new"),
    FW_CHA_EDIT("chapters/edit"),

    FW_NUM_INDEX("numbers/index"),
    FW_NUM_NEW("numbers/new"),
    FW_NUM_EDIT("numbers/edit"),

    FW_RES_WOR_INDEX("results/workbook_index"),
    FW_RES_CHA_INDEX("results/chapter_index"),
    FW_RES_NUM_INDEX("results/number_index"),
    FW_RES_EDIT("results/edit"),
    FW_RES_SHOW("results/show")

    /**
     * 文字列
     */

    ;

    private final String text;

    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getValue() {
        return this.text;
    }

    /**
     * 値(文字列)から、該当する定数を返却する
     * (例: "Report"→ForwardConst.ACT_REP)
     * @param 値(文字列)
     * @return ForwardConst型定数
     */
    public static ForwardConst get(String key) {
        for(ForwardConst c : values()) {
            if(c.getValue().equals(key)) {
                return c;
            }
        }
        return CMD_NONE;
    }

}
