package payapps.zoker.com.payapp.model;


import com.google.gson.annotations.SerializedName;
import com.zekers.ui.view.recycler.VisitableTypeControl;

import java.io.Serializable;
import java.util.List;

/**
 * 联系人
 * Created by Administrator on 2017/3/4.
 */

public class ContactMan implements VisitableTypeControl.Visitable,Serializable {
    @SerializedName("UserID")
    private String id;
    @SerializedName("Mobilephone")
    private String mobilephone;
    @SerializedName("Sex")
    private int sex;
    @SerializedName("ProvinceID")
    private String provinceID;
    @SerializedName("NickName")
    private String nickName;
    @SerializedName("HeadImage")
    private String headImage;
    @SerializedName("CityID")
    private String cityID;
    @SerializedName("IdCardAuth")
    private String idCardAuth;
    @SerializedName("BusinessLicenseAuth")
    private String businessLicenseAuth;
    @SerializedName("UserStatus")
    private String userStatus;
    @SerializedName("LoginTime")
    private String loginTime;
    @SerializedName("RegisterTime")
    private String registerTime;
    @SerializedName("TimeSignature")
    private String timeSignature;

    @SerializedName("ProvinceName")
    private String provinceName;
    @SerializedName("CityName")
    private String cityName;
    @SerializedName("Signature")
    private String signature;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("IdCardImage")
    private String idCardImage;
    @SerializedName("IdCardNumber")
    private String idCardNumber;
    @SerializedName("CompanyName")
    private String companyName;
    @SerializedName("BusinessAddress")
    private String businessAddress;
    @SerializedName("CompanyProfile")
    private String companyProfile;
    @SerializedName("Telephone")
    private String telephone;
    @SerializedName("Fax")
    private String fax;
    @SerializedName("BusinessLicenseImage")
    private String businessLicenseImage;
    @SerializedName("BusinessLicenseNumber")
    private String businessLicenseNumber;
    @SerializedName("Email")
    private String email;
    @SerializedName("QQ")
    private String qq;
    @SerializedName("Weixin")
    private String weixin;

    private boolean isShowDel=false;

    public boolean isShowDel() {
        return isShowDel;
    }

    public void setShowDel(boolean showDel) {
        isShowDel = showDel;
    }

    private boolean isSelect=false;

    public void setIsSelect(boolean isSelect){
        this.isSelect=isSelect;
    }

    public boolean isSelect(){
        return isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getIdCardAuth() {
        return idCardAuth;
    }

    public void setIdCardAuth(String idCardAuth) {
        this.idCardAuth = idCardAuth;
    }

    public String getBusinessLicenseAuth() {
        return businessLicenseAuth;
    }

    public void setBusinessLicenseAuth(String businessLicenseAuth) {
        this.businessLicenseAuth = businessLicenseAuth;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardImage() {
        return idCardImage;
    }

    public void setIdCardImage(String idCardImage) {
        this.idCardImage = idCardImage;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBusinessLicenseImage() {
        return businessLicenseImage;
    }

    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Override
    public int type(VisitableTypeControl.TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
