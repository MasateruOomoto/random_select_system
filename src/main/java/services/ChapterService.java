package services;

import java.util.List;

import actions.views.ChapterConverter;
import actions.views.ChapterView;
import constants.JpaConst;
import models.Chapter;

/**
 * チャプターテーブルの操作に関わる処理を行うクラス
 */
public class ChapterService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、ChapoterViewのリストで返却する
     * @param page ページ数
     * @Param workbookId 問題集のID
     * @return 表示するデータのリスト
     */
    public List<ChapterView> getPerPage(int page, int workbookId) {
        List<Chapter> chapters = em.createNamedQuery(JpaConst.Q_CHA_GET_ALL, Chapter.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return ChapterConverter.toViewList(chapters);

    }

    /**
     * チャプターテーブルのデータの件数を取得し、返却する
     * @return チャプターテーブルのデータの件数
     */
    public long countAll(int workbookId) {
        long chapterCount = (long) em.createNamedQuery(JpaConst.Q_CHA_COUNT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .getSingleResult();

        return chapterCount;
    }

}
