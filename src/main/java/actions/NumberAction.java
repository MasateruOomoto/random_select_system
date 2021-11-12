package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ChapterView;
import actions.views.NumberView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.validators.NumberValidator;
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

            //chapter_idについてパラメータの引き渡しがあればセッションスコープのchapter_idの値を変更
            if (!(getRequestParam(AttributeConst.CHAPTER_ID) == null)) {

                putSessionScope(AttributeConst.SESSION_CHAPTER_ID, getRequestParam(AttributeConst.CHAPTER_ID));
            }

            //セッションスコープに登録されている値session_workbook_idとsession_chapter_idを元に各種データを取得
            int sessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));

            //chapter_idに該当するチャプターのViewを取得
            ChapterService cService = new ChapterService();
            ChapterView cv =cService.findOne(sessionChapterId);
            cService.close();

            //chapter_idに該当するデータを取得
            List<NumberView> numbers = service.getAll(sessionChapterId);

            //指定されたチャプターのすべての問題番号データの件数を取得
            long numberCount = service.countAll(sessionChapterId);

            putRequestScope(AttributeConst.NUMBERS, numbers); //取得したチャプターデータ
            putRequestScope(AttributeConst.NUMBER_COUNT, numberCount); //全ての問題番号データの件数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数
            putRequestScope(AttributeConst.CHAPTER, cv); //チャプターの情報をセット

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

            //入力された値をString型で取得
            String firstNumber = getRequestParam(AttributeConst.FIRST_NUMBER);
            String lastNumber = getRequestParam(AttributeConst.LAST_NUMBER);

            //入力内容のバリデーションを行う
            List <String> errors = NumberValidator.validateNumber(firstNumber, lastNumber);

            if (errors.size() > 0) {
                //正しく入力できていない場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.NUMBER, new NumberView()); //空の問題番号インスタンス
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を表示
                forward(ForwardConst.FW_NUM_NEW);

            } else {
                //正しく入力できている場合

                //セッションスコープからworkbook_idとchapter_idを取得
                int workbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));
                int chapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));

                //入力された範囲にあるすべての問題番号を登録する
                for (int inputNumber = toNumber(firstNumber); inputNumber < toNumber(lastNumber)+1; inputNumber++) {

                    //パラメータの値を元に問題番号情報のインスタンスを作成する
                    NumberView nv = new NumberView(
                            null,
                            toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID)),
                            toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID)),
                            inputNumber);

                    //問題番号の情報登録作業を行う
                    service.create(nv, inputNumber, workbookId, chapterId);
                }

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_NUM, ForwardConst.CMD_INDEX);
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


            //セッションスコープに登録されている値session_workbook_idとsession_chapter_idを元に各種データを取得
            int sessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));

            //一覧画面に表示するデータを取得
            List<NumberView> numbers = service.getAll(sessionChapterId);

            //チャプターIDを元にのすべての問題番号データの件数を取得
            long numberCount = service.countAll(sessionChapterId);

            putRequestScope(AttributeConst.NUMBERS, numbers); //取得したチャプターデータ
            putRequestScope(AttributeConst.NUMBER_COUNT, numberCount); //全ての問題番号データの件数
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

            String[] deleteNumberIds = getRequestParams(AttributeConst.DELETE_NUMBER_ID);

            for (String deleteNumberId : deleteNumberIds) {
                //idを条件にチャプターデータを削除する
                service.destroy(toNumber(deleteNumberId));
            }

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_NUM, ForwardConst.CMD_INDEX);
        }

    }
}
