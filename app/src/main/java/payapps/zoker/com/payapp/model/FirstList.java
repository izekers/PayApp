package payapps.zoker.com.payapp.model;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class FirstList {
    private List<Data> FirstList;

    public List<Data> getFirstList() {
        return FirstList;
    }

    public void setFirstList(List<Data> firstList) {
        FirstList = firstList;
    }

    public static class Data{
        String CateID;
        String CateName;
        String FirstCateID;
        String SecondCateID;
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
