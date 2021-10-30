package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.ActionBase;
import actions.views.NumberView;
import constants.MessageConst;
import services.NumberService;

/**
 * 問題番号に設定されている値のバリデーションを行うクラス
 *
 */
public class NumberValidator {

    /**
     * 入力された問題番号の各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param nv NumberServiceのインスタンス
     * @param firstNumber 入力された最初の番号
     * @param lastNujmber 入力された最後の番号
     * @param numberDuplicateCheckFlag チャプターの重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validateNumber(String firstNumber, String lastNumber) {


        List<String> error = new ArrayList<String>();

        if (firstNumber == null || firstNumber.equals("") || lastNumber == null || lastNumber.equals("")) {
            //入力がされていなかったとき

            error.add(MessageConst.E_NONUMBER.getMessage());

        } else if (ActionBase.toNumber(firstNumber) > ActionBase.toNumber(lastNumber)){
            //最初の数字の方が大きかった場合
            error.add(MessageConst.E_ERR_NUMBER.getMessage());
        }

        return error;

    }

    public static List<String> ValidateExsist(NumberService sercive, NumberView nv) {

        List<String> exsist = new ArrayList<String>();


        return exsist;

    }

    /**
     * 問題番号の重複チェックを行う、エラーメッセージを返却
     * @param service NumberServiceのインスタンス
     * @param inputNumber 登録番号
     * @return trueまたはfalse
     */
    public static boolean validateExsist(NumberService service, int inputNumber, int workbookId, int chapterId) {

        boolean exsist = false;

        //同一問題番号の重複チェックを実施
        long numbersCount = isDuplicateNumber(service, inputNumber, workbookId, chapterId);

        //同一問題番号が既に登録されている場合はexsistをtrueに変更
        if (numbersCount > 0) {
            exsist = true;
        }

        //重複チェックの結果を返す
        return exsist;
    }

    /**
     * @param service NumberServiceのインスタンス
     * @param inputNumber 登録予定の番号
     * @return 問題番号テーブルに登録されている同一問題番号のデータの件数
     */
    private static long isDuplicateNumber(NumberService service, int inputNumber, int workbookId, int chapterId) {

        long numbersCount = service.countByNumber(inputNumber, workbookId, chapterId);
        return numbersCount;
    }










}
