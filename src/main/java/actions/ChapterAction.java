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
import services.ChapterService;
import services.WorkbookService;


/**
 * に関わる処理を行うActionクラス
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

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<ChapterView> chapters = service.getPerPage(page, toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));

            //指定された問題集のすべてのチャプターデータの件数を取得
            long chapterCount = service.countAll(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));

            WorkbookService serviceWorkbook = new WorkbookService();

            WorkbookView wv = serviceWorkbook.findOne(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));

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

        }

    }











    /**
     * ログイン中のユーザーが管理者かどうかチェックし、管理者でなければエラー画面を表示
     * true: 管理者 false: 管理者ではない
     * @throws ServletException
     * @throws IOException
     */
    private boolean checkAdmin() throws ServletException, IOException {

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
