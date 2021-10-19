package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.WorkNumber;

/**
 * 問題番号データのDTOモデル⇔Viewモデルの変換を行うクラス
 */
public class WorkNumberConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param nv WorkNumberViewのインスタンス
     * @return WorkNumberのインスタンス
     */
    public static WorkNumber toModel(WorkNumberView nv) {

        return new WorkNumber(
                nv.getId(),
                nv.getMondaiId(),
                nv.getChapterId(),
                nv.getMondaiNo());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param n WorkNumberのインスタンス
     * @return WorkNumberViewのインスタンス
     */
    public static WorkNumberView toView(WorkNumber n) {

        if(n == null) {
            return null;
        }

        return new WorkNumberView(
                n.getId(),
                n.getMondaiId(),
                n.getChapterId(),
                n.getMondaiNo());
    }

    /**
     * ViewモデルのリストからMTOモデルのリストを作成する
     * @param list Viewモデルのリスト
     * @return DTOモデルのリスト
     */
    public static List<WorkNumber> toModelList(List<WorkNumberView> list) {
        List<WorkNumber> ns = new ArrayList<>();

        for (WorkNumberView nv : list) {
            ns.add(toModel(nv));
        }

        return ns;
    }



    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<WorkNumberView> toViewList(List<WorkNumber> list) {
        List<WorkNumberView> nvs = new ArrayList<>();

        for (WorkNumber n : list) {
            nvs.add(toView(n));
        }

        return nvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param n DTOモデル(コピー先)
     * @param nv Viewモデル(コピー元)
     */
    public static void copyViewToModel(WorkNumber n, WorkNumberView nv) {
        n.setId(nv.getId());
        n.setMondaiId(nv.getMondaiId());
        n.setChapterId(nv.getChapterId());
        n.setMondaiNo(nv.getMondaiNo());
    }


    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param n DTOモデル(コピー元)
     * @param nv Viewモデル(コピー先)
     */
    public static void copyModelToView(WorkNumber n, WorkNumberView nv) {
        nv.setId(n.getId());
        nv.setMondaiId(n.getMondaiId());
        nv.setChapterId(n.getChapterId());
        nv.setMondaiNo(n.getMondaiNo());
    }
}