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
 * ユーザーのDTOモデル
 */

@Table(name = JpaConst.TABLE_USE)


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.USE_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ユーザーID
     */
    @Column(name = JpaConst.USE_COL_USER_ID, nullable = false, unique = true)
    private String userId;

    /**
     * 管理者権限があるかどうか(生徒:0, 教師:1)
     */
    @Column(name = JpaConst.USE_COL_ADMIN_FLG, nullable = false)
    private Boolean adminFlag;
}
