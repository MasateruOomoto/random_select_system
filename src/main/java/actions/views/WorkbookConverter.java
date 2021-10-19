package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Workbook;

/**
 * 問題集データのDTOモデル⇔Viewモデルの変換を行うクラス
 */
public class WorkbookConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param wv WorkbookViewのインスタンス
     * @return Workbookのインスタンス
     */
    public static Workbook toModel(WorkbookView wv) {

        return new Workbook(
                wv.getId(),
                wv.getWorkbookFlag(),
                wv.getWorkbookName());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param w Workbookのインスタンス
     * @return WorkbookViewのインスタンス
     */
    public static WorkbookView toView(Workbook w) {

        if (w == null) {
            return null;
        }

        return new WorkbookView(
                w.getId(),
                w.getWorkbookFlag(),
                w.getWorkbookName());
    }

    /**
     * ViewモデルのリストからMTOモデルのリストを作成する
     * @param list Viewモデルのリスト
     * @return DTOモデルのリスト
     */
    public static List<Workbook> toModelList(List<WorkbookView> list) {
        List<Workbook> ws = new ArrayList<>();

        for (WorkbookView wv : list) {
            ws.add(toModel(wv));
        }

        return ws;
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<WorkbookView> toViewList(List<Workbook> list) {
        List<WorkbookView> wvs = new ArrayList<>();

        for (Workbook w : list) {
            wvs.add(toView(w));
        }

        return wvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param w DTOモデル(コピー先)
     * @param wv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Workbook w, WorkbookView wv) {
        w.setId(wv.getId());
        w.setWorkbookFlag(wv.getWorkbookFlag());
        w.setWorkbookName(wv.getWorkbookName());
    }

    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param w DTOモデル(コピー元)
     * @param wv Viewモデル(コピー先)
     */
    public static void copyModelToView(Workbook w, WorkbookView wv) {
        wv.setId(w.getId());
        wv.setWorkbookFlag(w.getWorkbookFlag());
        wv.setWorkbookName(w.getWorkbookName());
    }
}