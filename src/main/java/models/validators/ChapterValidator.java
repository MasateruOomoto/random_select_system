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
        //入力値がなければエラーメッセージを返却
        if (chapterName == null || chapterName.equals("")) {
            return MessageConst.E_NOCHAPTER_NAME.getMessage();
        }

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
