package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.ChapterView;
import constants.MessageConst;
import services.ChapterService;

/**
 * チャプターインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class ChapterValidator {


    /**
     * 入力された値についてバリデーションを行う
     * @param chapterName チャプターの名前
     * @param sort 入力されたソートの値
     * @return エラーのリスト
     */
    public static List<String> validateInput(String chapterName, String sort) {

        List<String> inputError = new ArrayList<String>();

        //チャプター名が入力されているかチェック
        String inputNameError = validateInputName(chapterName);
        if (!inputNameError.equals("")) {
            inputError.add(inputNameError);
        }

        //正しく入力されているかチェック
        String inputSortError = validateInputSort(sort);
        if (!inputSortError.equals("")) {
            inputError.add(inputSortError);
        }

        return inputError;

    }

    /**
     * チャプターの名前が入力されているかについてバリデーションを行う
     * @param chapterName チャプターの名前
     * @return エラーのリスト
     */
    private static String validateInputName(String chapterName) {

        //入力値がなければエラーメッセージを返却
        if (chapterName == null || chapterName.equals("")) {
            return MessageConst.E_NOCHAPTER_NAME.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * 問題番号が正しく入力されているかについてバリデーションを行う
     * @param firstNumber 入力された最初の番号
     * @param lastNujmber 入力された最後の番号
     * @return エラーのリスト
     */
    private static String validateInputSort(String sort) {

        //入力値がなければエラーメッセージを返却
        if (sort == null || sort.equals("")) {
            return MessageConst.E_NOSORT.getMessage();
        }

        //入力値が数値でなければエラーメッセ―ジを返却
        boolean result = validateSortCheck(sort);

        if (result == false) {
            return MessageConst.E_NOTSORT.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * ソートに入力されている値は数値かどうかバリデーションを行う
     * @param sort ソートの値
     * @return 数値を入力できていればtrue、できていなければfalseで返却
     */
    private static boolean validateSortCheck(String sort) {

        boolean result = true;

        //最初の文字が数値か判定する
        for (int i = 0; i < sort.length(); i++) {

            //i文字目の文字についてCharacter.isDigitメソッドで判定する
            if (Character.isDigit(sort.charAt(i))) {

                //数字の場合は次の文字の判定へ
                continue;

            } else {

                //数字出ない場合は検証結果をfalseに上書きする
                result = false;
                break;
            }
        }

        return result;
    }


    /**
     * チャプターインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param cv ChapterServiceのインスタンス
     * @param chapterDuplicateCheckFlag チャプターの重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            ChapterService service, ChapterView cv, Boolean chapterNameDuplicateCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //チャプター名の名前のチェック
        String chapterNameError = validateName(service, cv.getChapterName(), cv.getWorkbookId(), chapterNameDuplicateCheckFlag);
        if (!chapterNameError.equals("")) {
            errors.add(chapterNameError);
        }

        return errors;
    }

    /**
     * チャプター名の入力チェックを行い、エラーメッセージを返却
     * @param service ChapterServiceのインスタンス
     * @param chapterName 問題集名
     * @param chapterNameDuplicateCheckFlag チャプター名の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateName(ChapterService service, String chapterName, Integer workbookId, Boolean chapterNameDuplicateCheckFlag) {

        if (chapterNameDuplicateCheckFlag) {
            //チャプター名の重複チェックを実施

            long chaptersCount = isDuplicateChapter(service, chapterName, workbookId);

            //同一チャプター名が既に登録されている場合はエラーメッセージを返却
            if (chaptersCount > 0) {
                return MessageConst.E_CHAPTER_NAME_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service ChapterServiceのインスタンス
     * @param chapterName チャプター名
     * @return チャプターテーブルに登録されている同一チャプター名のデータの件数
     */
    private static long isDuplicateChapter(ChapterService service, String chapterName, Integer workbookId) {

        long chaptersCount = service.countByName(chapterName, workbookId);
        return chaptersCount;
    }


}
