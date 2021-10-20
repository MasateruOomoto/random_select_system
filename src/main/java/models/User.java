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
 * ユーザーのDTOモデル
 */

@Table(name = JpaConst.TABLE_USE)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_USER_GET_ALL,
            query = JpaConst.Q_USER_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_COUNT,
            query = JpaConst.Q_USER_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_COUNT_RESISTERED_BY_USERID,
            query = JpaConst.Q_USER_COUNT_RESISTERED_BY_USERID_DEF),
    @NamedQuery(
            name = JpaConst.Q_USER_GET_BY_USERID_AND_PASS,
            query = JpaConst.Q_USER_GET_BY_USERID_AND_PASS_DEF)
})


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
     * パスワード
     */
    @Column(name = JpaConst.USE_COL_PASSWORD, length = 64, nullable = false)
    private String password;

    /**
     * 管理者権限があるかどうか(生徒:0, 教師:1)
     */
    @Column(name = JpaConst.USE_COL_ADMIN_FLG, nullable = false)
    private Integer adminFlag;
}
