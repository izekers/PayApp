package payapps.zoker.com.payapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/16.
 */

public class CityList {
    private List<Data> CityList;

    public List<Data> getProvinceList() {
        return CityList;
    }

    public void setProvinceList(List<Data> CityList) {
        this.CityList = CityList;
    }

    public static class Data implements Serializable {
        private String AddressID;
        private String AddressName;
        private         String AddressSpell;
        private         String AddressShortSpell;
        private         String FirstSpell;
        private         String ProvinceID;
        private   String CityID;
        private String DistrictID;
        private String LevelID;
        private String AddressSort;

        public String getAddressID() {
            return AddressID;
        }

        public void setAddressID(String addressID) {
            AddressID = addressID;
        }

        public String getAddressSpell() {
            return AddressSpell;
        }

        public void setAddressSpell(String addressSpell) {
            AddressSpell = addressSpell;
        }

        public String getAddressShortSpell() {
            return AddressShortSpell;
        }

        public void setAddressShortSpell(String addressShortSpell) {
            AddressShortSpell = addressShortSpell;
        }

        public String getFirstSpell() {
            return FirstSpell;
        }

        public void setFirstSpell(String firstSpell) {
            FirstSpell = firstSpell;
        }

        public String getProvinceID() {
            return ProvinceID;
        }

        public void setProvinceID(String provinceID) {
            ProvinceID = provinceID;
        }

        public String getDistrictID() {
            return DistrictID;
        }

        public void setDistrictID(String districtID) {
            DistrictID = districtID;
        }

        public String getLevelID() {
            return LevelID;
        }

        public void setLevelID(String levelID) {
            LevelID = levelID;
        }

        public String getAddressSort() {
            return AddressSort;
        }

        public void setAddressSort(String addressSort) {
            AddressSort = addressSort;
        }

        public String getCityID() {
            return CityID;
        }

        public void setCityID(String cityID) {
            CityID = cityID;
        }

        public String getAddressName() {
            return AddressName;
        }

        public void setAddressName(String addressName) {
            AddressName = addressName;
        }

        @Override
        public String toString() {
            return AddressName;
        }
    }
}
