package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.UserView;
import constants.MessageConst;
import services.UserService;

/**
 * ユーザーインスタンスに設定されている値のバリデーションを行うクラス
 *
 */
public class UserValidator {

    /**
     * ユーザーインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param uv UserServiceのインスタンス
     * @param userDuplicateCheckFlag ユーザーIDの重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            UserService service, UserView uv, Boolean userDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //ユーザーIDのチェック
        String userError = validateUser(service, uv.getUserId(), userDuplicateCheckFlag);
        if (!userError.equals("")) {
            errors.add(userError);
        }

        //パスワードのチェック
        String passError = validatePassword(uv.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    /**
     * ユーザーの入力チェックを行い、エラーメッセージを返却
     * @param service UserServiceのインスタンス
     * @param userId ユーザーID
     * @param userDuplicateCheckFlag ユーザーの重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateUser(UserService service, String userId, Boolean userDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (userId == null || userId.equals("")) {
            return MessageConst.E_NOEMP_USERID.getMessage();
        }

        if (userDuplicateCheckFlag) {
            //ユーザーIDの重複チェックを実施

            long usersCount = isDuplicateUser(service, userId);

            //同一ユーザーIDが既に登録されている場合はエラーメッセージを返却
            if (usersCount > 0) {
                return MessageConst.E_USER_USERID_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service UserServiceのインスタンス
     * @param userId ユーザーID
     * @return ユーザーテーブルに登録されている同一ユーザーIDのデータの件数
     */
    private static long isDuplicateUser(UserService service, String userId) {

        long usersCount = service.countByCode(userId);
        return usersCount;
    }

    /**
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }
}