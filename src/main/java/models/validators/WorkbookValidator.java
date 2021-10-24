package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.WorkbookView;
import constants.MessageConst;
import services.WorkbookService;

/**
 * 問題集インスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class WorkbookValidator {

    /**
     * 問題集インスタンスの各項目についてバリデーションを行う
     * チェック内容は増えることを踏まえて作成
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param wv WorkbookServiceのインスタンス
     * @param workbookDuplicateCheckFlag 問題集の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            WorkbookService service, WorkbookView wv, Boolean workbookDuplicateCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //問題集名の名前のチェック
        String workbookNameError = validateName(service, wv.getWorkbookName(), workbookDuplicateCheckFlag);
        if (!workbookNameError.equals("")) {
            errors.add(workbookNameError);
        }

        return errors;
    }

    /**
     * 問題集名の入力チェックを行い、エラーメッセージを返却
     * @param service WorkbookServiceのインスタンス
     * @param workbookName 問題集名
     * @param workbookDuplicateCheckFlag 問題集名の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateName(WorkbookService service, String workbookName, Boolean workbookDuplicateCheckFlag) {
      //入力値がなければエラーメッセージを返却
        if (workbookName == null || workbookName.equals("")) {
            return MessageConst.E_NOWORKBOOK_NAME.getMessage();
        }

        if (workbookDuplicateCheckFlag) {
            //問題集名の重複チェックを実施

            long workbooksCount = isDuplicateWorkbook(service, workbookName);

            //同一問題集名が既に登録されている場合はエラーメッセージを返却
            if (workbooksCount > 0) {
                return MessageConst.E_WORKBOOK_NAME_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service WorkbookServiceのインスタンス
     * @param workbookName 問題集名
     * @return 問題集テーブルに登録されている同一問題集名のデータの件数
     */
    private static long isDuplicateWorkbook(WorkbookService service, String workbookName) {

        long workbooksCount = service.countByName(workbookName);
        return workbooksCount;
    }

}