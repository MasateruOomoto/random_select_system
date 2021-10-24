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
 * 問題集のDTOモデル
 */

@Table(name = JpaConst.TABLE_WOR)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_WOR_GET_ALL,
            query = JpaConst.Q_WOR_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_COUNT,
            query = JpaConst.Q_WOR_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_COUNT_RESISTERED_BY_WOR_NAME,
            query = JpaConst.Q_WOR_COUNT_RESISTERED_BY_WOR_NAME_DEF),
    @NamedQuery(
            name = JpaConst.Q_WOR_DELETE,
            query = JpaConst.Q_WOR_INFOMATION_DELETE)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Workbook {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.WOR_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 科目名
     */
    @Column(name = JpaConst.WOR_COL_WORKBOOK_FLG, nullable = false)
    private String workbookFlag;

    /**
     * 問題集名
     */
    @Column(name = JpaConst.WOR_COL_WORKBOOK_NAME, nullable = false)
    private String workbookName;
}