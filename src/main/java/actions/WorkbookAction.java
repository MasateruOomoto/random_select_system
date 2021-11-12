package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.WorkbookView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ChapterService;
import services.NumberService;
import services.ResultService;
import services.WorkbookService;

/**
 * 問題集に関わる処理を行うActionクラス
 *
 */
public class WorkbookAction extends ActionBase {

    private WorkbookService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new WorkbookService();


        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 生徒向けの一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void indexStudent() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<WorkbookView> workbooks = service.getPerPage(page);

        //全ての問題集データの件数を取得
        long workbookCount = service.countAll();

        putRequestScope(AttributeConst.WORKBOOKS, workbooks); //取得した問題集データ
        putRequestScope(AttributeConst.WORKBOOK_COUNT, workbookCount); //全ての問題集データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_RES_WOR_INDEX);
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //管理者かどうかのチェック
        if(checkAdmin()) {

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<WorkbookView> workbooks = service.getPerPage(page);

            //全ての問題集データの件数を取得
            long workbookCount = service.countAll();

            putRequestScope(AttributeConst.WORKBOOKS, workbooks); //取得した問題集データ
            putRequestScope(AttributeConst.WORKBOOK_COUNT, workbookCount); //全ての問題集データの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_WOR_INDEX);
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
            putRequestScope(AttributeConst.WORKBOOK, new WorkbookView()); //空の問題集インスタンス

            //新規登録画面を表示
            forward(ForwardConst.FW_WOR_NEW);
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

            //パラメータの値を元に問題集情報のインスタンスを作成する
            WorkbookView wv = new WorkbookView(
                    null,
                    getRequestParam(AttributeConst.WORKBOOK_FLAG),
                    getRequestParam(AttributeConst.WORKBOOK_NAME));

            //問題集情報登録作業を行い、もしもエラーがあればその件数を返す
            List<String> errors = service.create(wv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.WORKBOOK, wv); //入力された問題集情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_WOR_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_WOR, ForwardConst.CMD_INDEX);
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

            //idを条件に問題集データを取得する
            WorkbookView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));

            if (wv == null) {

                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.WORKBOOK, wv); //取得した問題集情報

            //編集画面を表示する
            forward(ForwardConst.FW_WOR_EDIT);
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

                //パラメータの値を元に問題集情報のインスタンスを作成する
                WorkbookView wv = new WorkbookView(
                        toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)),
                        getRequestParam(AttributeConst.WORKBOOK_FLAG),
                            getRequestParam(AttributeConst.WORKBOOK_NAME));

                //問題集情報更新
                List<String> errors = service.update(wv);

                if (errors.size() > 0) {
                    //更新中にエラーが発生した場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.WORKBOOK, wv); //入力された問題集情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //編集画面を再表示
                    forward(ForwardConst.FW_WOR_EDIT);

                } else {
                    //更新中にエラーがなかった場合

                    //セッションに更新完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                    //一覧画面にリダイレクト
                    redirect(ForwardConst.ACT_WOR, ForwardConst.CMD_INDEX);
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

            //問題集IDを条件に問題集データを削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));

            //問題集IDを条件にチャプターデータを削除する
            ChapterService cService = new ChapterService();
            cService.destroyByWorkbookId(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));
            cService.close();

            //問題集IDを条件に問題番号データを削除する
            NumberService nService = new NumberService();
            nService.destroyByWorkbookId(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));
            nService.close();

            //問題集IDを条件に回答結果データを削除する
            ResultService rService = new ResultService();
            rService.destroyByWorkbookId(toNumber(getRequestParam(AttributeConst.WORKBOOK_ID)));
            rService.close();

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_WOR, ForwardConst.CMD_INDEX);
        }
    }
}