package services;

import java.util.List;

import actions.views.WorkbookConverter;
import actions.views.WorkbookView;
import constants.JpaConst;
import models.Workbook;
import models.validators.WorkbookValidator;

/**
 * ユーザーテーブルの操作に関わる処理を行うクラス
 */
public class WorkbookService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、WorkbookViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<WorkbookView> getPerPage(int page) {
        List<Workbook> workbooks = em.createNamedQuery(JpaConst.Q_WOR_GET_ALL, Workbook.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return WorkbookConverter.toViewList(workbooks);
    }

    /**
     * 問題集テーブルのデータの件数を取得し、返却する
     * @return 問題集テーブルのデータの件数
     */
    public long countAll() {
        long workbookCount = (long) em.createNamedQuery(JpaConst.Q_WOR_COUNT, Long.class)
                .getSingleResult();

        return workbookCount;
    }

    /**
     * 画面から入力された問題集の登録内容を元にデータを1件作成し、問題集テーブルに登録する
     * @param wv 画面から入力された問題集の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(WorkbookView wv) {

        //登録内容のバリデーションを行う
        List<String> errors = WorkbookValidator.validate(this, wv, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(WorkbookConverter.toModel(wv));
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件に取得したデータをWorkbookViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public WorkbookView findOne(int id) {
        Workbook w = findOneInternal(id);
        return WorkbookConverter.toView(w);
    }

    /**
     * idを条件にデータを1件取得し、Workbookのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Workbook findOneInternal(int id) {
        Workbook w = em.find(Workbook.class, id);

        return w;
    }



    /**
     * 画面から入力された問題集の更新内容を元にデータを1件作成し、問題集テーブルを更新する
     * @param wv 画面から入力された問題集の登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(WorkbookView wv) {

        //idを条件に登録済みの問題集情報を取得する
        WorkbookView savedWorkbook = findOne(wv.getId());

        boolean validateName = false;
        if (!savedWorkbook.getWorkbookName().equals(wv.getWorkbookName())) {
            //問題集名に変更がある場合

            validateName = true; //バリデーションを行う
            savedWorkbook.setWorkbookName(wv.getWorkbookName()); //変更後の問題集名を設定する
        }

        savedWorkbook.setWorkbookFlag(wv.getWorkbookFlag()); //変更後の科目フラグを設定する

        //更新内容についてのバリデーションを行う
        List<String> errors = WorkbookValidator.validate(this, savedWorkbook, validateName);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            em.getTransaction().begin();
            Workbook w = findOneInternal(savedWorkbook.getId());
            WorkbookConverter.copyViewToModel(w, savedWorkbook);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件の問題集データを削除する
     * @param id
     */
    public void destroy(Integer id) {

        // トランザクション開始
        em.getTransaction().begin();

        //データの削除を行う
        int delete = em.createNamedQuery(JpaConst.Q_WOR_DELETE)
                .setParameter(JpaConst.JPQL_PARM_ID, id)
                .executeUpdate();

        // トランザクション終了
        em.getTransaction().commit();
    }

    /**
     * 問題集名を条件に該当するデータの件数を取得し、返却する
     * @param workbookName 問題集名
     * @return 該当するデータの件数
     */
    public long countByName(String workbookName) {

        //指定したユーザーIDを保持するユーザーの件数を取得する
        long workbooks_count = (long) em.createNamedQuery(JpaConst.Q_WOR_COUNT_RESISTERED_BY_WOR_NAME, Long.class)
                .setParameter(JpaConst.JPQL_PARM_WORKBOOK_NAME, workbookName)
                .getSingleResult();
        return workbooks_count;
    }

}