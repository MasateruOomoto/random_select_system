package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ChapterView;
import actions.views.NumberView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ChapterService;
import services.NumberService;

/**
 * 問題番号に関わる処理を行うActionクラス
 *
 */
public class NumberAction extends ActionBase{

    private NumberService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new NumberService();

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

            //jspでchapter_idについてパラメータの引き渡しがあればセッションスコープのchapter_idの値を変更
            if (!(getRequestParam(AttributeConst.CHAPTER_ID) == null)) {

                putSessionScope(AttributeConst.SESSION_CHAPTER_ID, getRequestParam(AttributeConst.CHAPTER_ID));
            }

            //セッションスコープに登録されている値session_chapter_idに該当するチャプターのViewを取得
            ChapterService serviceChapter = new ChapterService();
            ChapterView cv = serviceChapter.findOne(toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID)));

            //セッションスコープに登録されている値session_workbook_idとsession_chapter_idを元に各種データを取得
            int SessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));
            int SessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<NumberView> numbers = service.getPerPage(page, SessionWorkbookId, SessionChapterId);

            //指定されたチャプターのすべての問題番号データの件数を取得
            long numberCount = service.countAll(SessionWorkbookId, SessionChapterId);

            putRequestScope(AttributeConst.NUMBERS, numbers); //取得したチャプターデータ
            putRequestScope(AttributeConst.NUMBER_COUNT, numberCount); //全ての問題番号データの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
            putRequestScope(AttributeConst.CHAPTER, cv); //チャプターの情報をセット

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_NUM_INDEX);
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
            putRequestScope(AttributeConst.NUMBER, new NumberView()); //空の問題番号インスタンス
            //新規登録画面を表示
            forward(ForwardConst.FW_NUM_NEW);
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

            List<String> exsists;

                for (int i = toNumber(getRequestParam(AttributeConst.FIRST_NUMBER)); i < toNumber(getRequestParam(AttributeConst.LAST_NUMBER))+1; i++) {

                    //パラメータの値を元に問題番号情報のインスタンスを作成する
                    NumberView nv = new NumberView(
                            null,
                            toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                            toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID)),
                            i);

                    //問題番号の情報登録作業を行う
                    exsists = service.create(nv);
                }

                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_NUM, ForwardConst.CMD_INDEX);


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


            //セッションスコープに登録されている値session_workbook_idとsession_chapter_idを元に各種データを取得
            int SessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));
            int SessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<NumberView> numbers = service.getPerPage(page, SessionWorkbookId, SessionChapterId);

            //指定されたチャプターのすべての問題番号データの件数を取得
            long numberCount = service.countAll(SessionWorkbookId, SessionChapterId);

            putRequestScope(AttributeConst.NUMBERS, numbers); //取得したチャプターデータ
            putRequestScope(AttributeConst.NUMBER_COUNT, numberCount); //全ての問題番号データの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

            //編集画面を表示する
            forward(ForwardConst.FW_NUM_EDIT);
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

            String[] deleteNumbers = getRequestParams(AttributeConst.DELETE_NUMBER);

            for (String deleteNumberId : deleteNumbers) {
                //idを条件にチャプターデータを削除する
                service.destroy(toNumber(deleteNumberId));


                putSessionScope(AttributeConst.FLUSH, deleteNumberId);
            }

            //セッションに削除完了のフラッシュメッセージを設定
            //putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_NUM, ForwardConst.CMD_INDEX);
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
