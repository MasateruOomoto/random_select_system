package services;

import java.util.List;

import javax.persistence.NoResultException;

import actions.views.ResultConverter;
import actions.views.ResultView;
import constants.JpaConst;
import models.Result;

/**
 * 回答結果テーブルの操作に関わる処理を行うクラス
 */
public class ResultService extends ServiceBase {

    /**
     * 一覧画面に表示するデータを取得し、ResultViewのリストで返却する
     * @Param chapterId チャプターのID
     * @param userId ユーザーID
     * @return 表示するデータのリスト
     */
    public List<ResultView> getAll(int chapterId, int userId) {

        List<Result> Results = em.createNamedQuery(JpaConst.Q_RES_GET_ALL, Result.class)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .setParameter(JpaConst.JPQL_PARM_USER_ID, userId)
                .getResultList();

        return ResultConverter.toViewList(Results);

    }

    /**
     * 回答結果テーブルのデータの件数を取得し、返却する
     * @param chapterId チャプターID
     * @param userId ユーザーID
     * @return 回答結果テーブルのデータの件数
     */
    public long countAll(int chapterId, int userId) {
        long resultCount = (long) em.createNamedQuery(JpaConst.Q_RES_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .setParameter(JpaConst.JPQL_PARM_USER_ID, userId)
                .getSingleResult();

        return resultCount;

    }

    /**
     * 回答データを更新する
     * @param rv 画面から入力された回答情報の登録内容
     */
    public void update(ResultView rv) {

        em.getTransaction().begin();
        Result r = findOneInternal(rv.getId());
        ResultConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }

    /**
     * idを条件にデータを1件取得し、Resultのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Result findOneInternal(int id) {
        Result r = em.find(Result.class, id);

        return r;
    }

    /**
     * 回答結果データを1件登録する
     * @param rv 回答結果データ
     * @return 登録結果(成功:true 失敗:false)
     */
    public void create(ResultView rv) {

        em.getTransaction().begin();
        em.persist(ResultConverter.toModel(rv));
        em.getTransaction().commit();

    }

    /**
     * 回答結果データを1件削除する
     * @param chapterId チャプターのID
     * @param userId ユーザーのID
     * @return 登録結果(成功:true 失敗:false)
     */
    public void destroy(int chapterId, int userId) {

        //トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_RES_DELETE_BY_CHAPTER_ID_AND_USER_ID)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .setParameter(JpaConst.JPQL_PARM_USER_ID, userId)
                .executeUpdate();

        //トランザクション終了
        em.getTransaction().commit();
    }

    /**
     * 問題集IDを元に回答結果データを削除する
     * @param workbookId 問題集のID
     * @return 登録結果(成功:true 失敗:false)
     */
    public void destroyByWorkbookId(int workbookId) {

        //トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_RES_DELETE_BY_WORKBOOK_ID)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .executeUpdate();

        //トランザクション終了
        em.getTransaction().commit();

    }

    /**
     * チャプターIDを元に回答結果データを削除する
     * @param chapterId チャプターのID
     * @return 登録結果(成功:true 失敗:false)
     */
    public void destroyByChapterId(int chapterId) {

        //トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_RES_DELETE_BY_CHAPTER_ID)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .executeUpdate();

        //トランザクション終了
        em.getTransaction().commit();

    }

    /**
     * ユーザーIDを元に回答結果データを削除する
     * @param userId ユーザーID
     * @return 登録結果(成功:true 失敗:false)
     */
    public void destroyByUserId(int userId) {

        //トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_RES_DELETE_BY_USER_ID)
                .setParameter(JpaConst.JPQL_PARM_USER_ID, userId)
                .executeUpdate();

        //トランザクション終了
        em.getTransaction().commit();

    }

    /**
     * チャプターID、ユーザーID、問題番号を条件に取得したデータをResultViewのインスタンスで返却する
     * @param chapterId チャプターID
     * @param userId ユーザーID
     * @param number 問題番号
     * @return 取得データのインスタンス 取得できない場合null
     */
    public ResultView findOne(int chapterId, int userId, int number) {

        Result r = null;

        try {
            //回答結果データから該当するチャプターの問題番号を取得する
            r = em.createNamedQuery(JpaConst.Q_RES_GET_BY_USER_ID_AND_CHAPTER_ID_AND_NUMBER, Result.class)
                    .setParameter(JpaConst.JPQL_PARM_USER_ID, userId)
                    .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                    .setParameter(JpaConst.JPQL_PARM_NUMBER, number)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return ResultConverter.toView(r);

    }

}
