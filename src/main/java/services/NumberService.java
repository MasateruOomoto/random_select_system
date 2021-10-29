package services;

import java.util.ArrayList;
import java.util.List;

import actions.views.NumberConverter;
import actions.views.NumberView;
import constants.JpaConst;
import models.Number;

/**
 * 問題集テーブルの操作に関わる処理を行うクラス
 */
public class NumberService extends ServiceBase {

    /**
     * 一覧画面に表示するデータを取得し、WorkNumberViewのリストで返却する
     * @param page ページ数
     * @Param workbookId 問題集のID
     * @Param chapterId チャプターのID
     * @return 表示するデータのリスト
     */
    public List<NumberView> getPerPage(int page, int workbookId, int chapterId) {
        List<Number> Numbers = em.createNamedQuery(JpaConst.Q_NUM_GET_ALL, Number.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .getResultList();

        return NumberConverter.toViewList(Numbers);
    }

    /**
     * 問題番号テーブルのデータの件数を取得し、返却する
     * @return 問題番号テーブルのデータの件数
     */
    public long countAll(int workbookId, int chapterId) {
        long NumberCount = (long) em.createNamedQuery(JpaConst.Q_NUM_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .getSingleResult();

        return NumberCount;
    }

    /**
     * 問題番号の登録内容を元にデータを1件作成し、問題番号テーブルに登録する
     * @param nv 画面から入力された問題集の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(NumberView nv) {

        List<String> exsist = new ArrayList<String>();

        //バリデーションエラーがなければデータを登録する
        //if (exsist.size() == 0) {
            em.getTransaction().begin();
            em.persist(NumberConverter.toModel(nv));
            em.getTransaction().commit();
       // }

        //エラーを返却（エラーがなければ0件の空リスト）
        return exsist;
    }

    /**
     * idを条件の問題番号データを削除する
     * @param deleteNimber 削除番号
     */
    public void destroy(Integer deleteNumberId) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_NUM_DELETE)
                .setParameter(JpaConst.JPQL_PARM_DELETE_NUMBER, deleteNumberId)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }


}