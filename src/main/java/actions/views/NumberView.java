package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 問題番号情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class NumberView {

    /**
     * id
     */
    private Integer id;

    /**
     * 問題集のID
     */
    private Integer workbookId;

    /**
     * チャプターのID
     */
    private Integer chapterId;

    /**
     * 問題番号
     */
    private Integer number;
}