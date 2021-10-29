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
 * 問題番号のDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_NUM)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_NUM_GET_ALL,
            query = JpaConst.Q_NUM_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_NUM_COUNT,
            query = JpaConst.Q_NUM_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_NUM_DELETE,
            query = JpaConst.Q_NUM_INFOMATION_DELETE)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Number {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.NUM_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 問題集のID
     */
    @Column(name = JpaConst.NUM_COL_WORKBOOK_ID)
    private Integer workbookId;

    /**
     * チャプターのID
     */
    @Column(name =JpaConst.NUM_COL_CHAPTER_ID)
    private Integer chapterId;

    /*
     * 問題番号
     */
    @Column(name = JpaConst.NUM_COL_NUMBER)
    private Integer number;


}
