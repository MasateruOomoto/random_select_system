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
 * 問題集のDTOモデル
 */

@Table(name = JpaConst.TABLE_WOR)


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
    @Column(name = JpaConst.WOR_COL_KAMOKU_FLG, nullable = false, unique = true)
    private String workbookFlag;

    /**
     * 問題集名
     */
    @Column(name = JpaConst.WOR_COL_MONDAI_NAME, nullable = false)
    private Integer workbookName;
}