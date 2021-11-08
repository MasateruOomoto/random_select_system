package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.ChapterView;
import actions.views.NumberView;
import actions.views.ResultView;
import actions.views.UserView;
import constants.AttributeConst;
import constants.ForwardConst;
import services.ChapterService;
import services.NumberService;
import services.ResultService;

/**
 * 回答結果に関わる処理を行うActionクラス
 *
 */
public class ResultAction extends ActionBase{

    private ResultService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ResultService();

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

        //jspでchapter_idについてパラメータの引き渡しがあればセッションスコープのchapter_idの値を変更
        if (!(getRequestParam(AttributeConst.CHAPTER_ID) == null)) {

            putSessionScope(AttributeConst.SESSION_CHAPTER_ID, getRequestParam(AttributeConst.CHAPTER_ID));
        }

        //セッションスコープに登録されている値session_chapter_idに該当するチャプターのViewを取得
        ChapterService serviceChapter = new ChapterService();
        ChapterView cv = serviceChapter.findOne(toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID)));

        //セッションスコープに登録されている値session_workbook_idとsession_chapter_idとuser_idを元に各種データを取得
        int SessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));
        int SessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));
        UserView SessionLoginUser = getSessionScope(AttributeConst.LOGIN_USER);

        //一覧画面に表示するデータを取得
        List<ResultView> results = service.getAll(SessionWorkbookId, SessionChapterId, SessionLoginUser.getId());

        //指定されたチャプターのすべての問題番号データの件数を取得
        long resultCount = service.countAll(SessionWorkbookId, SessionChapterId, SessionLoginUser.getId());

        putRequestScope(AttributeConst.RESULTS, results); //取得した回答結果
        putRequestScope(AttributeConst.RESULT_COUNT, resultCount); //全ての回答番号データの件数
        putRequestScope(AttributeConst.CHAPTER, cv); //チャプターの情報をセット

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_RES_NUM_INDEX);
    }

    /**
     * 登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //セッションスコープに登録されている値session_workbook_idとsession_chapter_idとuser_idを元に各種データを取得
        int SessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));
        int SessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));
        UserView SessionLoginUser = getSessionScope(AttributeConst.LOGIN_USER);

        //問題番号データからsession_workbook_idとsession_chapter_idに該当するデータを取得する
        NumberService nService = new NumberService();
        List<NumberView> numbers = nService.getAll(SessionWorkbookId, SessionChapterId);
        nService.close();

        //numbersのリストの問題番号を持つNumberViewのリストを作成
        List<ResultView> results = new ArrayList<ResultView>(); //回答結果のリストを初期化

        for (NumberView number : numbers) {
            //問題番号を条件に回答結果データを作成し、Listに加える


            //パラメータとNumberViewの値を元に回答結果情報のインスタンスを作成する
            ResultView rv = new ResultView(
                    null,
                    SessionLoginUser.getId(),
                    SessionWorkbookId,
                    SessionChapterId,
                    number.getNumber(),
                    2,
                    null);


            results.add(rv);
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putSessionScope(AttributeConst.RESULTS, results); //回答結果インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_RES_EDIT);

    }

    /**
     * 登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {


            List<ResultView> results = getSessionScope(AttributeConst.RESULTS);
            String[] answerFlags = getRequestParams(AttributeConst.ANSWER_FLAG);

            //チェックされた問題番号を登録する
            for (int i = 0; i < results.size(); i++) {
                //リストから要素を取得
                ResultView result = results.get(i);

                //問題番号に対応するanswerFlagを取得
                String answerFlag = answerFlags[i];

                //resultのanswerFlagに入力された値を代入し登録する
                result.setAnswerFlag(Integer.parseInt(answerFlag));

                service.create(result);
            }

            //新規登録画面を再表示
            forward(ForwardConst.FW_RES_NUM_INDEX);

        }

    }

    /**
     * 回答結果からランダムで表示を行う
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //問題を解き終わってanswerFlagに変更があった場合回答結果データの内容を変更する


        //セッションスコープに登録されている値session_workbook_idとsession_chapter_idとuser_idを元に各種データを取得
        int SessionWorkbookId = toNumber(getSessionScope(AttributeConst.SESSION_WORKBOOK_ID));
        int SessionChapterId = toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID));
        UserView SessionLoginUser = getSessionScope(AttributeConst.LOGIN_USER);

        //回答結果データからsession_workbook_idとsession_chapter_idとUser_idに該当するデータを取得する
        List<ResultView> results = service.getAll(SessionWorkbookId, SessionChapterId, SessionLoginUser.getId());

        ResultView result = results.get((int)(Math.random() * results.size()));

        //ランダム表示する問題番号をセッションスコープにセットする
        putSessionScope(AttributeConst.RESULT, result);

        //セッションスコープに登録されている値session_chapter_idに該当するチャプターのViewを取得
        ChapterService serviceChapter = new ChapterService();
        ChapterView cv = serviceChapter.findOne(toNumber(getSessionScope(AttributeConst.SESSION_CHAPTER_ID)));

        putRequestScope(AttributeConst.CHAPTER, cv); //チャプターの情報をセット
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //新規登録画面を表示
        forward(ForwardConst.FW_RES_SHOW);

    }

    /**
     * ランダム表示画面で回答フラグの変更があった場合変更を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションスコープからランダム表示した問題を取得する
            ResultView rv = getSessionScope(AttributeConst.RESULT);

            //リクエストスコープからフラグの変更を取得する
            int answerFlag = Integer.parseInt(getRequestParam(AttributeConst.ANSWER_FLAG));

            //変更があった場合データの変更を行う
            if (!(rv.getAnswerFlag() == answerFlag)) {

                rv.setAnswerFlag(answerFlag);

                service.update(rv);

            }

            //ランダム表示画面にリダイレクト
            redirect(ForwardConst.ACT_RES, ForwardConst.CMD_SHOW);
        }

    }

}
