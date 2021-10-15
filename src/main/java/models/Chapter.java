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
 * チャプターのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_CHA)

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
    @Column(name = JpaConst.CHA_COL_MONDAI_ID)
    private Integer mondaiId;

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
