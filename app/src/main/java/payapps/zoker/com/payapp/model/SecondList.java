package payapps.zoker.com.payapp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class SecondList {
    private List<Data> SecondList;

    public List<Data> getSecondList() {
        return SecondList;
    }

    public void setSecondList(List<Data> secondList) {
        this.SecondList = secondList;
    }

    public static class Data{
        String CateID;
        String CateName;
        String FirstCateID;
        String SecondCateID="1";
        String  LevelID;
        String CateSort;

        public String getCateID() {
            return CateID;
        }

        public void setCateID(String cateID) {
            CateID = cateID;
        }

        public String getCateName() {
            return CateName;
        }

        public void setCateName(String cateName) {
            CateName = cateName;
        }

        public String getFirstCateID() {
            return FirstCateID;
        }

        public void setFirstCateID(String firstCateID) {
            FirstCateID = firstCateID;
        }

        public String getSecondCateID() {
            return SecondCateID;
        }

        public void setSecondCateID(String secondCateID) {
            SecondCateID = secondCateID;
        }

        public String getLevelID() {
            return LevelID;
        }

        public void setLevelID(String levelID) {
            LevelID = levelID;
        }

        public String getCateSort() {
            return CateSort;
        }

        public void setCateSort(String cateSort) {
            CateSort = cateSort;
        }

        @Override
        public String toString() {
            return CateName;
        }
    }
}
