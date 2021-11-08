package services;

import java.util.List;

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
     * @param page ページ数
     * @Param workbookId 問題集のID
     * @Param chapterId チャプターのID
     * @return 表示するデータのリスト
     */
    public List<ResultView> getAll(int workbookId, int chapterId, int userId) {

        List<Result> Results = em.createNamedQuery(JpaConst.Q_RES_GET_ALL, Result.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_ID, chapterId)
                .setParameter(JpaConst.JPQL_PARM_USER_ID, userId)
                .getResultList();

        return ResultConverter.toViewList(Results);

    }

    /**
     * 回答結果テーブルのデータの件数を取得し、返却する
     * @return 回答結果テーブルのデータの件数
     */
    public long countAll(int workbookId, int chapterId, int userId) {
        long resultCount = (long) em.createNamedQuery(JpaConst.Q_RES_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
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

}
