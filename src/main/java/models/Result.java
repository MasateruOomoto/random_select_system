package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 回答結果のDTOモデル
 */
@Table(name = JpaConst.TABLE_RES)

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Result {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.RES_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ユーザーID
     */
    @Column(name = JpaConst.RES_COL_USER_ID)
    private Integer userId;

    /**
     * 問題番号のID
     */
    @Column(name = JpaConst.RES_COL_NUMBER_ID)
    private Integer numberId;

    /**
     * 回答フラグ
     */
    @Column(name = JpaConst.RES_COL_ANSWER_FLG)
    private Integer answerFlag;

    /**
     * 閲覧回数
     */
    @Column(name = JpaConst.RES_COL_VIEW_COUNT)
    private Integer viewCount;
}
