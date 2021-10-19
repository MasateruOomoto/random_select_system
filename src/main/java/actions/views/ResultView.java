package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 回答結果情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class ResultView {

    /**
     * id
     */
    private Integer id;

    /**
     * ユーザーID
     */
    private Integer userId;

    /**
     * 問題番号のID
     */
    private Integer mondaiNoId;

    /**
     * 回答フラグ
     */
    private Integer answerFlag;

    /**
     * 閲覧回数
     */
    private Integer viewCount;
}