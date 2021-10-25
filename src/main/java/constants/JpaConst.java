package constants;

public interface JpaConst {

    //persistence-util名
    String PERSISTENCE_UNIT_NAME = "random_select_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

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
    String WOR_COL_WORKBOOK_FLG = "workbook_flag"; //科目の種類
    String WOR_COL_WORKBOOK_NAME = "workbook_name"; //問題集の名前

    //チャプターテーブル
    String TABLE_CHA = "chapters"; // テーブル名
    //チャプターテーブルカラム
    String CHA_COL_ID = "id"; //id
    String CHA_COL_WORKBOOK_ID = "mondai_id"; //問題集のID
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

    //Entity名
    String ENTITY_USER = "user"; //ユーザー
    String ENTITY_WOR = "workbook"; //問題集
    String ENTITY_CHA = "chapter"; //チャプター

    //JPQL内パラメータ
    String JPQL_PARM_ID = "id"; //id
    String JPQL_PARM_USER_ID = "userId"; //ユーザーID
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_USER = "user"; //ユーザー

    String JPQL_PARM_WORKBOOK_NAME = "workbookName"; //問題集名
    String JPQL_PARM_WORKBOOK_ID = "workbookId"; //問題集のID

    String JPQL_PARM_CHAPTER_NAME = "chapterName"; //チャプター名
    String JPQL_PRAM_CHAPTER_ID = "chapterId"; //チャプターID

    //NamedQueryの nameとquery
    //全てのユーザーをidの降順に取得する
    String Q_USER_GET_ALL = ENTITY_USER + ".getAll"; //name
    String Q_USER_GET_ALL_DEF = "SELECT e FROM User AS e ORDER BY e.id DESC"; //query
    //全てのユーザーの件数を取得する
    String Q_USER_COUNT = ENTITY_USER + ".count";
    String Q_USER_COUNT_DEF = "SELECT COUNT(e) FROM User AS e";
    //ユーザーIDとハッシュ化済パスワードを条件に未削除のユーザーを取得する
    String Q_USER_GET_BY_USERID_AND_PASS = ENTITY_USER + ".getByUserIdAndPass";
    String Q_USER_GET_BY_USERID_AND_PASS_DEF = "SELECT e FROM User AS e WHERE e.userId = :" + JPQL_PARM_USER_ID + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定したユーザーIDを保持するユーザーの件数を取得する
    String Q_USER_COUNT_RESISTERED_BY_USER_ID = ENTITY_USER + ".countRegisteredByUserId";
    String Q_USER_COUNT_RESISTERED_BY_USER_ID_DEF = "SELECT COUNT(e) FROM User AS e WHERE e.userId = :" + JPQL_PARM_USER_ID;
    //ユーザーIDを元にユーザーを削除する
    String Q_USER_DELETE = ENTITY_USER + ".deleteById";
    String Q_USER_INFOMATION_DELETE = "DELETE FROM User AS e WHERE e.id = :" + JPQL_PARM_ID;

    //すべての問題集をidの降順に取得する
    String Q_WOR_GET_ALL = ENTITY_WOR + "getAll";
    String Q_WOR_GET_ALL_DEF = "SELECT e FROM Workbook AS e ORDER BY e.id DESC";
    //全ての問題集の件数を取得する
    String Q_WOR_COUNT = ENTITY_WOR + ".count";
    String Q_WOR_COUNT_DEF = "SELECT COUNT(e) FROM Workbook AS e";
    //問題集IDを条件に未削除の問題集を取得する
    String Q_WOR_GET_BY_WOR_ID = ENTITY_WOR + ".getByWorId";
    String Q_WOR_GET_BY_WOR_ID_DEF = "SELECT e FROM Workbook AS e WHERE e.Id = :" + JPQL_PARM_ID;
    //指定した問題集名を保持する問題集の件数を取得する
    String Q_WOR_COUNT_RESISTERED_BY_WOR_NAME = ENTITY_WOR + ".countRegisteredByWorkbookName";
    String Q_WOR_COUNT_RESISTERED_BY_WOR_NAME_DEF = "SELECT COUNT(e) FROM Workbook AS e WHERE e.workbookName = :" + JPQL_PARM_WORKBOOK_NAME;
    //問題集IDを元に問題集を削除する
    String Q_WOR_DELETE = ENTITY_WOR + ".deleteById";
    String Q_WOR_INFOMATION_DELETE = "DELETE FROM Workbook AS e WHERE e.id = :" + JPQL_PARM_ID;

    //問題集にあるすべてのチャプターをidの降順に取得する
    String Q_CHA_GET_ALL = ENTITY_CHA + ".getAll";
    String Q_CHA_GET_ALL_DEF = "SELECT e FROM Chapter AS e WHERE e.workbookId = :" + JPQL_PARM_WORKBOOK_ID + " ORDER BY e.id DESC";
    //問題集にある全てのチャプターの件数を取得する
    String Q_CHA_COUNT = ENTITY_CHA + ".count";
    String Q_CHA_COUNT_DEF = "SELECT COUNT(e) FROM Chapter AS e WHERE e.workbookId = :" + JPQL_PARM_WORKBOOK_ID;
    //指定したチャプター名を保持するチャプターの件数を取得する
    String Q_CHA_COUNT_RESISTERED_BY_CHA_NAME_AND_WOR_ID = ENTITY_CHA + ".countRegisteredByChapterNameAndWorkbookId";
    String Q_CHA_COUNT_RESISTERED_BY_CHA_NAME_AND_WOR_ID_DEF = "SELECT COUNT(e) FROM Chapter AS e WHERE e.chapterName = :" + JPQL_PARM_CHAPTER_NAME + " AND e.workbookId = :" + JPQL_PARM_WORKBOOK_ID;
}
