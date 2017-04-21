package payapps.zoker.com.payapp.model;

import com.zekers.ui.view.recycler.VisitableTypeControl;

/**
 * Created by Administrator on 2017/3/20.
 */

public class BankCard implements VisitableTypeControl.Visitable{
    private String BankCardID="";
    private String UserID="";
    private String BankName="";
    private String CardNumber="";
    private String UserName="";
    private String BankMobilephone="";
    private String CardType="";
    private String CardName="";
    private String Province="";
    private String City="";
    private String BankBranch="";
    private String CardStatus="";
    private String CreateTime="";

    public String getBankCardID() {
        return BankCardID;
    }

    public void setBankCardID(String bankCardID) {
        BankCardID = bankCardID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBankMobilephone() {
        return BankMobilephone;
    }

    public void setBankMobilephone(String bankMobilephone) {
        BankMobilephone = bankMobilephone;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getBankBranch() {
        return BankBranch;
    }

    public void setBankBranch(String bankBranch) {
        BankBranch = bankBranch;
    }

    public String getCardStatus() {
        return CardStatus;
    }

    public void setCardStatus(String cardStatus) {
        CardStatus = cardStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    @Override
    public int type(VisitableTypeControl.TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
