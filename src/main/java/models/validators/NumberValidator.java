package models.validators;

import java.util.ArrayList;
import java.util.List;

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
    public static List<String> validateNumber(
            NumberService service, Integer firstNumber, Integer lastNumber) {


        List<String> errors = new ArrayList<String>();

        if (firstNumber == null || firstNumber.equals("") || lastNumber == null || lastNumber.equals("")) {
            errors.add(MessageConst.E_NONUMBER.getMessage());
        } else {
            if (firstNumber > lastNumber) {
            errors.add(MessageConst.E_ERR_NUMBER.getMessage());
            }
        }

        return errors;

    }

    public static List<String> ValidateExsist(NumberService sercive, NumberView nv) {

        List<String> exsist = new ArrayList<String>();


        return exsist;

    }
}
