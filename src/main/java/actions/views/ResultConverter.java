package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Result;

/**
 * 回答結果データのDTOモデル⇔Viewモデルの変換を行うクラス
 */
public class ResultConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ResultViewのインスタンス
     * @return r Resultのインスタンス
     */
    public static Result toModel(ResultView rv) {

        return new Result(
                rv.getId(),
                rv.getUserId(),
                rv.getNumberId(),
                rv.getAnswerFlag(),
                rv.getViewCount());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Resultのインスタンス
     * @return ResultViewのインスタンス
     */
    public static ResultView toView(Result r) {

        if(r == null) {
            return null;
        }

        return new ResultView(
                r.getId(),
                r.getUserId(),
                r.getNumberId(),
                r.getAnswerFlag(),
                r.getViewCount());
    }

    /**
     * ViewモデルのリストからMTOモデルのリストを作成する
     * @param list Viewモデルのリスト
     * @return DTOモデルのリスト
     */
    public static List<Result> toModelList(List<ResultView> list) {
        List<Result> rs = new ArrayList<>();

        for (ResultView rv : list) {
            rs.add(toModel(rv));
        }

        return rs;
    }



    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ResultView> toViewList(List<Result> list) {
        List<ResultView> rvs = new ArrayList<>();

        for (Result r : list) {
            rvs.add(toView(r));
        }

        return rvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Result r, ResultView rv) {
        r.setId(rv.getId());
        r.setUserId(rv.getUserId());
        r.setNumberId(rv.getNumberId());
        r.setAnswerFlag(rv.getAnswerFlag());
        r.setViewCount(rv.getViewCount());
    }


    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param r DTOモデル(コピー元)
     * @param rv Viewモデル(コピー先)
     */
    public static void copyModelToView(Result r, ResultView rv) {
        rv.setId(r.getId());
        rv.setUserId(r.getUserId());
        rv.setNumberId(r.getNumberId());
        rv.setAnswerFlag(r.getAnswerFlag());
        rv.setViewCount(r.getViewCount());
    }
}