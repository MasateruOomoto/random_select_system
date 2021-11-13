package services;

import java.util.List;

import actions.views.NumberConverter;
import actions.views.NumberView;
import constants.JpaConst;
import models.Number;
import models.validators.NumberValidator;

/**
 * 問題集テーブルの操作に関わる処理を行うクラス
 */
public class NumberService extends ServiceBase {

    /**
     * 一覧画面に表示するデータを取得し、WorkNumberViewのリストで返却する
     * @param page ページ数
     * @Param chapterId チャプターのID
     * @return 表示するデータのリスト
     */
    public List<NumberView> getAll(int chapterId) {
        List<Number> Numbers = em.createNamedQuery(JpaConst.Q_NUM_GET_ALL, Number.class)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .getResultList();

        return NumberConverter.toViewList(Numbers);
    }

    /**
     * 問題番号テーブルのデータの件数を取得し、返却する
     * @param chapterId チャプターのID
     * @return 問題番号テーブルのデータの件数
     */
    public long countAll(int chapterId) {
        long NumberCount = (long) em.createNamedQuery(JpaConst.Q_NUM_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .getSingleResult();

        return NumberCount;
    }

    /**
     * 問題番号の登録内容を元にデータを1件作成し、問題番号テーブルに登録する
     * @param nv 画面から入力された問題集の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public void create(NumberView nv, int inputNumber, int workbookId, int chapterId) {

        //登録内容のバリデーションを行う
        boolean exsist = NumberValidator.validateExsist(this, inputNumber, workbookId, chapterId);

        //バリデーションエラーがなければデータを登録する
        if (exsist == false) {
            em.getTransaction().begin();
            em.persist(NumberConverter.toModel(nv));
            em.getTransaction().commit();
        }
    }

    /**
     * idを条件の問題番号データを削除する
     * @param deleteNimber 削除番号
     */
    public void destroy(Integer deleteNumberId) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        em.createNamedQuery(JpaConst.Q_NUM_DELETE)
                .setParameter(JpaConst.JPQL_PARM_DELETE_NUMBER_ID, deleteNumberId)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }

    /**
     * チャプターidを条件の問題番号データを削除する
     * @param chapterId チャプターID
     */
    public void destroyByChapterId(Integer chapterId) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        em.createNamedQuery(JpaConst.Q_NUM_DELETE_BY_CHAPTER_ID)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }

    /**
     * 問題集idを条件の問題番号データを削除する
     * @param workbookId 問題集ID
     */
    public void destroyByWorkbookId(Integer workbookId) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        em.createNamedQuery(JpaConst.Q_NUM_DELETE_BY_WORKBOOK_ID)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }

    /**
     * 問題番号を条件に該当するデータの件数を取得し、返却する
     * @param inputNumber 登録予定の問題番号
     * @return 該当するデータの件数
     */
    public long countByNumber(Integer inputNumber, Integer workbookId, Integer chapterId) {

        //指定したユーザーIDを保持する問題番号の件数を取得する
        long numbers_count = (long) em.createNamedQuery(JpaConst.Q_NUM_COUNT_RESISTERED_BY_NUMBER_NUMBER, Long.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .setParameter(JpaConst.JPQL_PARM_NUMBER, inputNumber)
                .getSingleResult();
        return numbers_count;
    }


}