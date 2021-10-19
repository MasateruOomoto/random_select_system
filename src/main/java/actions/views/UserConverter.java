package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.User;

/**
 * ユーザーデータのDTOモデル⇔Viewモデルの変換を行うクラス
 */
public class UserConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param uv ChapterViewのインスタンス
     * @return Chapterのインスタンス
     */
    public static User toModel(UserView uv) {

        return new User(
                uv.getId(),
                uv.getUserId(),
                uv.getPassword(),
                uv.getAdminFlag());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param u Chapterのインスタンス
     * @return ChapterViewのインスタンス
     */
    public static UserView toView(User u) {

        if(u == null) {
            return null;
        }

        return new UserView(
                u.getId(),
                u.getUserId(),
                u.getPassword(),
                u.getAdminFlag());
    }

    /**
     * ViewモデルのリストからMTOモデルのリストを作成する
     * @param list Viewモデルのリスト
     * @return DTOモデルのリスト
     */
    public static List<User> toModelList(List<UserView> list) {
        List<User> us = new ArrayList<>();

        for (UserView uv : list) {
            us.add(toModel(uv));
        }

        return us;
    }



    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<UserView> toViewList(List<User> list) {
        List<UserView> uvs = new ArrayList<>();

        for (User u : list) {
            uvs.add(toView(u));
        }

        return uvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param u DTOモデル(コピー先)
     * @param uv Viewモデル(コピー元)
     */
    public static void copyViewToModel(User u, UserView uv) {
        u.setId(uv.getId());
        u.setUserId(uv.getUserId());
        u.setPassword(uv.getPassword());
        u.setAdminFlag(uv.getAdminFlag());
    }


    /**
     * DTOモデルの全フィールドの内容をViewモデルのフィールドにコピーする
     * @param u DTOモデル(コピー元)
     * @param uv Viewモデル(コピー先)
     */
    public static void copyModelToView(User u, UserView uv) {
        uv.setId(u.getId());
        uv.setUserId(u.getUserId());
        uv.setPassword(u.getPassword());
        uv.setAdminFlag(u.getAdminFlag());
    }
}