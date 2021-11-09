package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 回答結果のDTOモデル
 */

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_RES_GET_ALL,
            query = JpaConst.Q_RES_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_RES_COUNT,
            query = JpaConst.Q_RES_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_RES_DELETE_BY_WORKBOOK_ID_AND_CHAPTER_ID_AND_USER_ID,
            query = JpaConst.Q_RES_INFOMATION_DELETE_BY_WORKBOOK_ID_AND_CHAPTER_ID_AND_USER_ID),
    @NamedQuery(
            name = JpaConst.Q_RES_DELETE_BY_WORKBOOK_ID,
            query = JpaConst.Q_RES_INFOMATION_DELETE_BY_WORKBOOK_ID),
    @NamedQuery(
            name = JpaConst.Q_RES_DELETE_BY_CHAPTER_ID,
            query = JpaConst.Q_RES_INFOMATION_DELETE_BY_CHAPTER_ID),
    @NamedQuery(
            name = JpaConst.Q_RES_GET_BY_USER_ID_AND_CHAPTER_ID_AND_NUMBER,
            query = JpaConst.Q_RES_GET_BY_USER_ID_AND_CHAPTER_ID_AND_NUMBER_DEF)
})

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
     * 問題集のID
     */
    @Column(name = JpaConst.RES_COL_WORKBOOK_ID)
    private Integer workbookId;

    /**
     * チャプターのID
     */
    @Column(name = JpaConst.RES_COL_CHAPTER_ID)
    private Integer chapterId;

    /**
     * 問題番号
     */
    @Column(name = JpaConst.RES_COL_NUMBER)
    private Integer number;

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
