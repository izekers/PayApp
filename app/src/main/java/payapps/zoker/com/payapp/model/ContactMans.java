package payapps.zoker.com.payapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ContactMans {
    private List<ContactMan> AddressBookList;

    public List<payapps.zoker.com.payapp.model.ContactMan> getContactMan() {
        return AddressBookList;
    }

    public void setContactMan(List<payapps.zoker.com.payapp.model.ContactMan> contactMan) {
        AddressBookList = contactMan;
    }
}
