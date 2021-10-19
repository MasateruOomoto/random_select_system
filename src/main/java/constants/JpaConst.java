package constants;

public interface JpaConst {

    //persistence-util名
    String PERSISTENCE_UNIT_NAME = "random_select_system";

    //ユーザーテーブル
    String TABLE_USE = "users"; //テーブル名
    //ユーザーテーブルカラム
    String USE_COL_ID = "id"; //id
    String USE_COL_USER_ID = "user_id"; //ユーザーID
    String USE_COL_PASSWORD = "password"; //パスワード
    String USE_COL_ADMIN_FLG = "admin_flag"; //管理者権限

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)

    //問題集テーブル
    String TABLE_WOR = "workbooks"; //テーブル名
    //問題集テーブルカラム
    String WOR_COL_ID = "id"; //id
    String WOR_COL_KAMOKU_FLG = "kamoku_flag"; //科目の種類
    String WOR_COL_MONDAI_NAME = "mondai_name"; //問題集の名前

    int ROLE_MATH = 1;
    int ROLE_LANGUAGE = 2;
    int ROLE_ENGLISH = 3;
    int ROLE_CHEMISTRY = 4;
    int ROLE_PHYSICS = 5;
    int ROLE_SOCIETY = 6;

    //チャプターテーブル
    String TABLE_CHA = "chapters"; // テーブル名
    //チャプターテーブルカラム
    String CHA_COL_ID = "id"; //id
    String CHA_COL_MONDAI_ID = "mondai_id"; //問題集のID
    String CHA_COL_CHAPTER_NAME = "chapter_name"; //チャプターの名前
    String CHA_COLSORT = "sort"; //表示順

    //問題番号テーブル
    String TABLE_NUM = "numbers"; //テーブル名
    //問題番号テーブルカラム
    String NUM_COL_ID = "id"; //id
    String NUM_COL_MONDAI_ID = "mondai_id"; //問題集のID
    String NUM_COL_CHAPTER_ID = "chapter_id"; //チャプターのID
    String NUM_COL_MONDAI_NO = "mondai_no"; //問題番号

    //回答結果テーブル
    String TABLE_RES = "result"; //テーブル名
    //回答結果テーブルカラム
    String RES_COL_ID = "id"; //id_
    String RES_COL_USER_ID = "user_id"; //ユーザーID
    String RES_COL_MONDAI_NO_ID = "mondai_no_id"; //問題番号のID
    String RES_COL_ANSWER_FLG = "answer_flg"; //回答フラグ
    String RES_COL_VIEW_COUNT = "view_count"; //閲覧回数

    int ROLE_MARU = 0;
    int ROLE_BATSU = 1;

}
