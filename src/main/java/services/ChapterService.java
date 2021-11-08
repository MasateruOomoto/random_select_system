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
        List<String> errors = ChapterValidator.validate(this, cv, true);

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

    /**
     * 画面から入力されたチャプターの更新内容を元にデータを1件作成し、チャプターテーブルを更新する
     * @param cv 画面から入力されたチャプターの登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(ChapterView cv) {

        //idを条件に登録済みのチャプター情報を取得する
        ChapterView savedChapter = findOne(cv.getId());

        boolean validateName = false;
        if (!savedChapter.getChapterName().equals(cv.getChapterName())) {
            //チャプター名に変更がある場合

            validateName = true; //バリデーションを行う
            savedChapter.setChapterName(cv.getChapterName()); //変更後のチャプター名を設定する
        }

        savedChapter.setSort(cv.getSort()); //変更後のソート番号を設定する

        //更新内容についてのバリデーションを行う
        List<String> errors = ChapterValidator.validate(this, savedChapter, validateName);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            em.getTransaction().begin();
            Chapter c = findOneInternal(savedChapter.getId());
            ChapterConverter.copyViewToModel(c, savedChapter);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件のチャプターデータを削除する
     * @param id
     */
    public void destroy(Integer id) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_CHA_DELETE)
                .setParameter(JpaConst.JPQL_PARM_ID, id)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }

    /**
     * 問題集idを条件のチャプターデータを削除する
     * @param workbookId 問題集ID
     */
    public void destroyByWorkbookId(Integer workbookId) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_CHA_DELETE_BY_WORKBOOK_ID)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_ID, workbookId)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }

}
