package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ChapterView;
import actions.views.UserView;
import actions.views.WorkbookView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ChapterService;
import services.WorkbookService;


/**
 * チャプターに関わる処理を行うActionクラス
 *
 */
public class ChapterAction extends ActionBase {


    private ChapterService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ChapterService();

        //メソッドを実行
        invoke();

        service.close();
    }


    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //管理者かどうかチェック
        if (checkAdmin()) {

            //jspでworkbook_idについてパラメータの引き渡しがあればセッションスコープのworkbook_idの値を変更
            if (!(getRequestParam(AttributeConst.WORKBOOK_ID) == null)) {

                putSessionScope(AttributeConst.SESSION_WORKBOOK_ID, getRequestParam(AttributeConst.WORKBOOK_ID));
            }

            //セッションスコープのworkbook_idに該当する問題集のViewを取得
            WorkbookService serviceWorkbook = new WorkbookService();
            WorkbookView wv = serviceWorkbook.findOne(toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)));


            //セッションスコープに登録されているworkbook_idを元に各種データを取得
            int SessionWorkbookId = wv.getId();

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<ChapterView> chapters = service.getPerPage(page, SessionWorkbookId);

            //指定された問題集のすべてのチャプターデータの件数を取得
            long chapterCount = service.countAll(SessionWorkbookId);

            putRequestScope(AttributeConst.CHAPTERS, chapters); //取得したチャプターデータ
            putRequestScope(AttributeConst.CHAPTER_COUNT, chapterCount); //全てのチャプターデータの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
            putRequestScope(AttributeConst.WORKBOOK, wv); //問題集の情報をセット

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_CHA_INDEX);
        }
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

      //管理者かどうかのチェック
        if(checkAdmin()) {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.CHAPTER, new ChapterView()); //空のチャプターインスタンス

            //新規登録画面を表示
            forward(ForwardConst.FW_CHA_NEW);
        }
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken() && checkAdmin()) {

            //パラメータの値を元にチャプター情報のインスタンスを作成する
            ChapterView cv = new ChapterView(
                    null,
                    toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                    getRequestParam(AttributeConst.CHAPTER_NAME),
                    toNumber(getRequestParam(AttributeConst.CHAPTER_SORT)));

            //チャプター情報登録作業を行い、もしもエラーがあればその件数を返す
            List<String> errors = service.create(cv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.CHAPTER, cv); //入力されたチャプター情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_CHA_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_CHA, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

      //管理者かどうかのチェック
        if(checkAdmin()) {

            //idを条件にチャプターデータを取得する
            ChapterView cv = service.findOne(toNumber(getRequestParam(AttributeConst.CHAPTER_ID)));

            if (cv == null) {

                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.CHAPTER, cv); //取得したチャプター情報

            //編集画面を表示する
            forward(ForwardConst.FW_CHA_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //管理者かどうかのチェック
        if(checkAdmin()) {

            //CSRF対策 tokenのチェック
            if (checkToken()) {
                //パラメータの値を元にチャプター情報のインスタンスを作成する
                ChapterView cv = new ChapterView(
                        toNumber(getRequestParam(AttributeConst.CHAPTER_ID)),
                        toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                        getRequestParam(AttributeConst.CHAPTER_NAME),
                        toNumber(getRequestParam(AttributeConst.CHAPTER_SORT)));

                //チャプター情報更新
                List<String> errors = service.update(cv);

                if (errors.size() > 0) {
                    //更新中にエラーが発生した場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.CHAPTER, cv); //入力されたチャプター情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //編集画面を再表示
                    forward(ForwardConst.FW_CHA_EDIT);
                } else {
                    //更新中にエラーがなかった場合

                    //セッションに更新完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                    //一覧画面にリダイレクト
                    redirect(ForwardConst.ACT_CHA, ForwardConst.CMD_INDEX);
                }

            }
        }
    }

    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken() && checkAdmin()) {

            //idを条件にチャプターデータを削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.CHAPTER_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_CHA, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * ログイン中のユーザーが管理者かどうかチェックし、管理者でなければエラー画面を表示
     * true: 管理者 false: 管理者ではない
     * @throws ServletException
     * @throws IOException
     */
    public boolean checkAdmin() throws ServletException, IOException {

        //セッションからログイン中のユーザー情報を取得
        UserView uv = (UserView) getSessionScope(AttributeConst.LOGIN_USER);

        //管理者でなければエラー画面を表示
        if (uv.getAdminFlag() != AttributeConst.ROLE_ADMIN.getIntegerValue()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;

        } else {

            return true;
        }

    }
}
