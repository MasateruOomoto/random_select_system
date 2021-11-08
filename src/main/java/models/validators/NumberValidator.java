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

        //数値が入力されているかチェック
        String inputError = validateInput(firstNumber, lastNumber);
        if (!inputError.equals("")) {
            error.add(inputError);

        } else {
            //入力された数値の順番が正しいかチェック
            String orderError = validateOrderError(ActionBase.toNumber(firstNumber), ActionBase.toNumber(lastNumber));
            if (!orderError.equals("")) {
                error.add(orderError);
            }
        }

        return error;

    }


    /**
     * 問題番号が正しく入力されているかについてバリデーションを行う
     * @param firstNumber 入力された最初の番号
     * @param lastNujmber 入力された最後の番号
     * @return エラーのリスト
     */
    private static String validateInput(String firstNumber, String lastNumber) {

        //入力値がなければエラーメッセージを返却
        if (firstNumber == null || firstNumber.equals("") || lastNumber == null || lastNumber.equals("")) {
            return MessageConst.E_NONUMBER.getMessage();
        }

        //入力値が数値でなければエラーメッセ―ジを返却
        boolean result = validateNumberCheck(firstNumber, lastNumber);

        if (result == false) {
            return MessageConst.E_NOTNUMBER.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }


    /**
     * 問題番号が数値で入力されているかについてバリデーションを行う
     * @param firstNumber 入力された最初の番号
     * @param lastNujmber 入力された最後の番号
     * @return 数値を入力できていればtrue、できていなければfalseで返却
     */
    private static boolean validateNumberCheck(String firstNumber, String lastNumber) {

        boolean result = true;

        //最初の文字が数値か判定する
        for (int i = 0; i < firstNumber.length(); i++) {

            //i文字目の文字についてCharacter.isDigitメソッドで判定する
            if (Character.isDigit(firstNumber.charAt(i))) {

                //数字の場合は次の文字の判定へ
                continue;

            } else {

                //数字出ない場合は検証結果をfalseに上書きする
                result = false;
                break;
            }
        }


        if (result == true) {
            //最初の文字が数値だった場合は最後の文字が数値だったか判定する
            for (int i = 0; i < lastNumber.length(); i++) {

                //i文字目の文字についてCharacter.isDigitメソッドで判定する
                if (Character.isDigit(lastNumber.charAt(i))) {

                    //数字の場合は次の文字の判定へ
                    continue;

                } else {

                    //数字出ない場合は検証結果をfalseに上書きする
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 入力された数値の順番が正しいかについてバリデーションを行う
     * @param firstNumber 入力された最初の番号
     * @param lastNujmber 入力された最後の番号
     * @return エラーリスト
     */
    private static String validateOrderError(int firstNumber, int lastNumber) {

        //入力値がなければエラーメッセージを返却
        if (firstNumber > lastNumber) {
            return MessageConst.E_NUMBER_ORDER.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";

    }

























    public static List<String> ValidateExsist(NumberService sercive, NumberView nv) {

        List<String> exsist = new ArrayList<String>();


        return exsist;

    }

    /**
     * 問題番号の重複チェックを行う、エラーメッセージを返却
     * @param service NumberServiceのインスタンス
     * @param inputNumber 登録番号
     * @return 重複している場合はtrue、重複していない場合はfalseで返却
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
