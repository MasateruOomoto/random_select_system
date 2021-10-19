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
 * 問題番号のDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_NUM)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkNumber {

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
    @Column(name = JpaConst.NUM_COL_MONDAI_ID)
    private Integer mondaiId;

    /**
     * チャプターのID
     */
    @Column(name =JpaConst.NUM_COL_CHAPTER_ID)
    private Integer chapterId;

    /**
     * 問題番号
     */
    @Column(name = JpaConst.NUM_COL_MONDAI_NO)
    private Integer mondaiNo;

}
