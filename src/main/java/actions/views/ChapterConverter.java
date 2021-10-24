package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Chapter;

/**
 * チャプターデータのDTOモデル⇔Viewモデルの変換を行うクラス
 */
public class ChapterConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param cv ChapterViewのインスタンス
     * @return Chapterのインスタンス
     */
    public static Chapter toModel(ChapterView cv) {

        return new Chapter(
                cv.getId(),
                cv.getWorkbookId(),
                cv.getChapterName(),
                cv.getSort());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param c Chapterのインスタンス
     * @return ChapterViewのインスタンス
     */
    public static ChapterView toView(Chapter c) {

        if(c == null) {
            return null;
        }

        return new ChapterView(
                c.getId(),
                c.getWorkbookId(),
                c.getChapterName(),
                c.getSort());
    }

    /**
     * ViewモデルのリストからMTOモデルのリストを作成する
     * @param list Viewモデルのリスト
     * @return DTOモデルのリスト
     */
    public static List<Chapter> toModelList(List<ChapterView> list) {
        List<Chapter> cs = new ArrayList<>();

        for (ChapterView cv : list) {
            cs.add(toModel(cv));
        }

        return cs;
    }



    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ChapterView> toViewList(List<Chapter> list) {
        List<ChapterView> cvs = new ArrayList<>();

        for (Chapter c : list) {
            cvs.add(toView(c));
        }

        return cvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param c DTOモデル(コピー先)
     * @param cv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Chapter c, ChapterView cv) {
        c.setId(cv.getId());
        c.setWorkbookId(cv.getWorkbookId());
        c.setChapterName(cv.getChapterName());
        c.setSort(cv.getSort());
    }


    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param c DTOモデル(コピー元)
     * @param cv Viewモデル(コピー先)
     */
    public static void copyModelToView(Chapter c, ChapterView cv) {
        cv.setId(c.getId());
        cv.setWorkbookId(c.getWorkbookId());
        cv.setChapterName(c.getChapterName());
        cv.setSort(c.getSort());
    }
}