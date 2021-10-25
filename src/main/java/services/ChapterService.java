package services;

import java.util.List;

import actions.views.ChapterConverter;
import actions.views.ChapterView;
import constants.JpaConst;
import models.Chapter;
import models.validators.ChapterValidator;

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

    /**
     * 画面から入力されたチャプターの登録内容を元にデータを1件作成し、チャプターテーブルに登録する
     * @param cv 画面から入力された問題集の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(ChapterView cv) {

        //登録内容のバリデーションを行う
        List<String> errors = ChapterValidator.validate(this, cv, true, true);    //ソートと名前二つ必要

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(ChapterConverter.toModel(cv));
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * チャプター名を条件に該当するデータの件数を取得し、返却する
     * @param chapterName チャプター名
     * @param workbookId 問題集のID
     * @return 該当するデータの件数
     */
    public long countByName(String chapterName, Integer workbookId) {

        //指定したチャプター名を保持するチャプターの件数を取得する
        long chapters_count = (long) em.createNamedQuery(JpaConst.Q_CHA_COUNT_RESISTERED_BY_CHA_NAME_AND_WOR_ID, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CHAPTER_NAME, chapterName)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .getSingleResult();
        return chapters_count;
    }

    /**
     * idを条件に取得したデータをChapterViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ChapterView findOne(int id) {
        Chapter c = findOneInternal(id);
        return ChapterConverter.toView(c);
    }

    /**
     * idを条件にデータを1件取得し、Chapterのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Chapter findOneInternal(int id) {
        Chapter c = em.find(Chapter.class, id);

        return c;
    }
}
