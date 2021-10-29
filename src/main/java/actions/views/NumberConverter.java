package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Number;

/**
 * 問題番号データのDTOモデル⇔Viewモデルの変換を行うクラス
 */
public class NumberConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param nv WorkNumberViewのインスタンス
     * @return WorkNumberのインスタンス
     */
    public static Number toModel(NumberView nv) {

        return new Number(
                nv.getId(),
                nv.getWorkbookId(),
                nv.getChapterId(),
                nv.getNumber());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param n WorkNumberのインスタンス
     * @return WorkNumberViewのインスタンス
     */
    public static NumberView toView(Number n) {

        if(n == null) {
            return null;
        }

        return new NumberView(
                n.getId(),
                n.getWorkbookId(),
                n.getChapterId(),
                n.getNumber());
    }

    /**
     * ViewモデルのリストからMTOモデルのリストを作成する
     * @param list Viewモデルのリスト
     * @return DTOモデルのリスト
     */
    public static List<Number> toModelList(List<NumberView> list) {
        List<Number> ns = new ArrayList<>();

        for (NumberView nv : list) {
            ns.add(toModel(nv));
        }

        return ns;
    }



    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<NumberView> toViewList(List<Number> list) {
        List<NumberView> nvs = new ArrayList<>();

        for (Number n : list) {
            nvs.add(toView(n));
        }

        return nvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param n DTOモデル(コピー先)
     * @param nv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Number n, NumberView nv) {
        n.setId(nv.getId());
        n.setWorkbookId(nv.getWorkbookId());
        n.setChapterId(nv.getChapterId());
        n.setNumber(nv.getNumber());
    }


    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param n DTOモデル(コピー元)
     * @param nv Viewモデル(コピー先)
     */
    public static void copyModelToView(Number n, NumberView nv) {
        nv.setId(n.getId());
        nv.setWorkbookId(n.getWorkbookId());
        nv.setChapterId(n.getChapterId());
        nv.setNumber(n.getNumber());
    }
}