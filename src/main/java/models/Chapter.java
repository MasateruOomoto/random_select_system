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
 * チャプターのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_CHA)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_CHA_GET_ALL,
            query = JpaConst.Q_CHA_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_CHA_COUNT,
            query = JpaConst.Q_CHA_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_CHA_COUNT_RESISTERED_BY_CHA_NAME_AND_WOR_ID,
            query = JpaConst.Q_CHA_COUNT_RESISTERED_BY_CHA_NAME_AND_WOR_ID_DEF),
    @NamedQuery(
            name = JpaConst.Q_CHA_DELETE,
            query = JpaConst.Q_CHA_INFOMATION_DELETE),
    @NamedQuery(
            name = JpaConst.Q_CHA_DELETE_BY_WORKBOOK_ID,
            query = JpaConst.Q_CHA_INFOMATION_DELETE_BY_WORKBOOK_ID),
    @NamedQuery(
            name = JpaConst.Q_CHA_COUNT_BY_SORT,
            query = JpaConst.Q_CHA_COUNT_BY_SORT_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Chapter {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.CHA_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 問題集のID
     */
    @Column(name = JpaConst.CHA_COL_WORKBOOK_ID)
    private Integer workbookId;

    /**
     * チャプター名
     */
    @Column(name = JpaConst.CHA_COL_CHAPTER_NAME)
    private String chapterName;

    /**
     * 表示順
     */
    @Column(name = JpaConst.CHA_COLSORT)
    private Integer sort;

}
