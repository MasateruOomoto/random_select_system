package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ChapterView;
import actions.views.WorkbookView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.validators.ChapterValidator;
import services.ChapterService;
import services.NumberService;
import services.ResultService;
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
     * 生徒向けの一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void indexStudent() throws ServletException, IOException {

        //workbook_idについてパラメータの引き渡しがあればセッションスコープのworkbook_idの値を変更
        if (!(getRequestParam(AttributeConst.WORKBOOK_ID) == null)) {

            putSessionScope(AttributeConst.SESSION_WORKBOOK_ID, getRequestParam(AttributeConst.WORKBOOK_ID));
        }

        //セッションスコープに登録されているworkbook_idを元に各種データを取得
        int sessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));

        //セッションスコープのworkbook_idに該当するWorkbookViewを取得
        WorkbookService wService = new WorkbookService();
        WorkbookView wv = wService.findOne(sessionWorkbookId);
        wService.close();

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<ChapterView> chapters = service.getPerPage(page, sessionWorkbookId);

        //指定された問題集のすべてのチャプターデータの件数を取得
        long chapterCount = service.countAll(sessionWorkbookId);

        putRequestScope(AttributeConst.CHAPTERS, chapters); //取得したチャプターデータ
        putRequestScope(AttributeConst.CHAPTER_COUNT, chapterCount); //全てのチャプターデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
        putRequestScope(AttributeConst.WORKBOOK, wv); //問題集の情報をセット

        //一覧画面を表示
        forward(ForwardConst.FW_RES_CHA_INDEX);
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //管理者かどうかチェック
        if (checkAdmin()) {

            //workbook_idについてパラメータの引き渡しがあればセッションスコープのworkbook_idの値を変更
            if (!(getRequestParam(AttributeConst.WORKBOOK_ID) == null)) {

                putSessionScope(AttributeConst.SESSION_WORKBOOK_ID, getRequestParam(AttributeConst.WORKBOOK_ID));
            }

            //セッションスコープに登録されているworkbook_idを元に各種データを取得
            int sessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));

            //workbook_idに該当するWorkbookViewを取得
            WorkbookService wService = new WorkbookService();
            WorkbookView wv = wService.findOne(sessionWorkbookId);
            wService.close();

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<ChapterView> chapters = service.getPerPage(page, sessionWorkbookId);

            //workbook_idに該当するすべてのチャプターデータの件数を取得
            long chapterCount = service.countAll(sessionWorkbookId);

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

            //入力された値を取得
            String chapterName = getRequestParam(AttributeConst.CHAPTER_NAME);
            String sort = getRequestParam(AttributeConst.CHAPTER_SORT);

            //ソートについてのバリデーションを行う
            String inputSortError = ChapterValidator.validateInputSort(sort);

            //入力内容のバリデーションを行う
            List <String> inputErrors = ChapterValidator.validateInput(chapterName, inputSortError);

            if (inputErrors.size() > 0) {
                //すべて正しく入力されていない場合

                if (!inputSortError.equals("")) {
                    //ソートの値が正しく入力されていなかった場合

                    //パラメータの値を元にチャプター情報のインスタンスを作成する
                    ChapterView cv = new ChapterView(
                            null,
                            toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                            chapterName,
                            null);

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.CHAPTER, cv); //入力されていたチャプターの名前をセット
                    putRequestScope(AttributeConst.ERR, inputErrors); //エラーのリスト

                    //新規登録画面を表示
                    forward(ForwardConst.FW_CHA_NEW);

                } else {
                   //ソートの値が正しく入力されている場合

                    //パラメータの値を元にチャプター情報のインスタンスを作成する
                    ChapterView cv = new ChapterView(
                            null,
                            toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                            chapterName,
                            toNumber(sort));

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.CHAPTER, cv); //入力されていたチャプターの名前をセット
                    putRequestScope(AttributeConst.ERR, inputErrors); //エラーのリスト

                    //新規登録画面を表示
                    forward(ForwardConst.FW_CHA_NEW);

                }

            } else {
                //すべて正しく入力されていた場合

                //パラメータの値を元にチャプター情報のインスタンスを作成する
                ChapterView cv = new ChapterView(
                        null,
                        toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                        chapterName,
                        toNumber(sort));

                //チャプター情報登録作業を行い、もしもエラーがあればエラーリストを返す
                List<String> errors = service.create(cv, toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)));

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

                //入力された値を取得
                String chapterName = getRequestParam(AttributeConst.CHAPTER_NAME);
                String sort = getRequestParam(AttributeConst.CHAPTER_SORT);

                //ソートについてのバリデーションを行う
                String inputSortError = ChapterValidator.validateInputSort(sort);

                //入力内容のバリデーションを行う
                List <String> inputErrors = ChapterValidator.validateInput(chapterName, inputSortError);

                if (inputErrors.size() > 0) {
                    //すべて正しく入力されていない場合

                    if (!inputSortError.equals("")) {
                        //ソート番号が正しく入力されていない場合

                        //パラメータの値を元にチャプター情報のインスタンスを作成する
                        ChapterView cv = new ChapterView(
                                toNumber(getRequestParam(AttributeConst.CHAPTER_ID)),
                                toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                                chapterName,
                                null);

                        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                        putRequestScope(AttributeConst.CHAPTER, cv); //入力されていたチャプターの名前をセット
                        putRequestScope(AttributeConst.ERR, inputErrors); //エラーのリスト

                        //新規登録画面を表示
                        forward(ForwardConst.FW_CHA_EDIT);
                    } else {
                        //ソートの値が正しく入力されている場合

                        //パラメータの値を元にチャプター情報のインスタンスを作成する
                        ChapterView cv = new ChapterView(
                                toNumber(getRequestParam(AttributeConst.CHAPTER_ID)),
                                toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                                chapterName,
                                toNumber(sort));

                        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                        putRequestScope(AttributeConst.CHAPTER, cv); //入力されていたチャプターの名前をセット
                        putRequestScope(AttributeConst.ERR, inputErrors); //エラーのリスト

                        //新規登録画面を表示
                        forward(ForwardConst.FW_CHA_EDIT);

                    }

                } else {
                    //すべて正しく入力されていた場合

                    //パラメータの値を元にチャプター情報のインスタンスを作成する
                    ChapterView cv = new ChapterView(
                            toNumber(getRequestParam(AttributeConst.CHAPTER_ID)),
                            toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                            chapterName,
                            toNumber(sort));

                    //チャプター情報更新
                    List<String> errors = service.update(cv, toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)));

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

            //チャプターidを条件に問題番号データを削除する
            NumberService nService = new NumberService();
            nService.destroyByChapterId(toNumber(getRequestParam(AttributeConst.CHAPTER_ID)));
            nService.close();

            //チャプターidを条件に回答結果データを削除する
            ResultService rService = new ResultService();
            rService.destroyByChapterId(toNumber(getRequestParam(AttributeConst.CHAPTER_ID)));
            rService.close();

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_CHA, ForwardConst.CMD_INDEX);
        }
    }
}
