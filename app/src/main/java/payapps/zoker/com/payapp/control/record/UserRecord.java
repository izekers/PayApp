package payapps.zoker.com.payapp.control.record;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zekers.utils.cache.BaseRecord;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/13.
 */

public class UserRecord extends BaseRecord<User> {
    private static final String UserID = "userId";
    private static final String Sex = "sex";
    private static final String ProvinceID = "provinceId";
    private static final String CityID = "cityId";
    private static final String IdCardAuth = "idCardAuth";
    private static final String BusinessLicenseAuth = "businessLicenseAuth";
    private static final String UserStatus = "userStatus";
    private static final String LoginTime = "loginTime";
    private static final String RegisterTime = "registerTime";
    private static final String TimeSignature = "timeSignature";

    private static final String Mobilephone = "Mobilephone";
    private static final String NickName = "NickName";
    private static final String HeadImage = "HeadImage";


    private static final String provinceName = "provinceName";
    private static final String cityName = "cityName";
    private static final String signature = "signature";
    private static final String userName = "userName";
    private static final String idCardImage = "idCardImage";
    private static final String idCardNumber = "idCardNumber";
    private static final String companyName = "companyName";
    private static final String businessAddress = "businessAddress";
    private static final String companyProfile = "companyProfile";
    private static final String telephone = "telephone";
    private static final String fax = "fax";
    private static final String businessLicenseImage = "businessLicenseImage";
    private static final String businessLicenseNumber = "businessLicenseNumber";
    private static final String email = "email";
    private static final String qq = "qq";
    private static final String weixin = "weixin";


    private static UserRecord instance;

    private UserRecord() {
    }

    public static UserRecord getInstance() {
        if (instance == null) {
            synchronized (UserRecord.class) {
                instance = new UserRecord();
            }
        }
        return instance;
    }

    @Override
    protected boolean Save(SharedPreferences.Editor editor, User user) {
        Logger.d("UserRecord", new Gson().toJson(user));
        editor.putString(UserID, user.getId());
        editor.putInt(Sex, user.getSexValue());
        editor.putString(ProvinceID, user.getProvinceID());
        editor.putString(CityID, user.getCityID());
        editor.putInt(IdCardAuth, user.getIdCardAuth());
        editor.putInt(BusinessLicenseAuth, user.getBusinessLicenseAuth());
        editor.putString(UserStatus, user.getUserStatus());
        editor.putString(LoginTime, user.getLoginTime());
        editor.putString(RegisterTime, user.getRegisterTime());
        editor.putString(TimeSignature, user.getTimeSignature());

        editor.putString(provinceName, user.getProvinceName());
        editor.putString(cityName, user.getCityName());
        editor.putString(signature, user.getSignature());
        editor.putString(userName, user.getUserName());
        editor.putString(idCardImage, user.getIdCardImage());
        editor.putString(companyName, user.getCompanyName());
        editor.putString(businessAddress, user.getBusinessAddress());
        editor.putString(companyProfile, user.getCompanyProfile());
        editor.putString(telephone, user.getTelephone());
        editor.putString(fax, user.getFax());
        editor.putString(businessLicenseImage, user.getBusinessLicenseImage());
        editor.putString(businessLicenseNumber, user.getBusinessLicenseNumber());
        editor.putString(email, user.getEmail());
        editor.putString(qq, user.getQq());
        editor.putString(weixin, user.getWeixin());

        editor.putString(Mobilephone, user.getMobilephone());
        editor.putString(NickName, user.getNickName());
        editor.putString(HeadImage, user.getHeadImage());
        clearInfo();
        Logger.d("UserRecord", "" + editor.commit());
        return true;
    }

    @Override
    protected User load(SharedPreferences sharedPreferences) {
        User user = new User();
        user.setId(sharedPreferences.getString(UserID, null));
        user.setSex(sharedPreferences.getInt(Sex, 0));
        user.setProvinceID(sharedPreferences.getString(ProvinceID, null));
        user.setCityID(sharedPreferences.getString(CityID, null));
        try {
            user.setIdCardAuth(sharedPreferences.getInt(IdCardAuth, -1));
        } catch (ClassCastException e) {
            user.setIdCardAuth(sharedPreferences.getString(IdCardAuth, null));
        }

        try {
            user.setBusinessLicenseAuth(sharedPreferences.getInt(BusinessLicenseAuth, -1));
        } catch (ClassCastException e) {
            user.setBusinessLicenseAuth(sharedPreferences.getString(BusinessLicenseAuth, null));
        }
        user.setUserStatus(sharedPreferences.getString(UserStatus, null));
        user.setLoginTime(sharedPreferences.getString(LoginTime, null));
        user.setRegisterTime(sharedPreferences.getString(RegisterTime, null));
        user.setTimeSignature(sharedPreferences.getString(TimeSignature, null));


        user.setProvinceName(sharedPreferences.getString(provinceName, null));
        user.setCityName(sharedPreferences.getString(cityName, null));
        user.setSignature(sharedPreferences.getString(signature, null));
        user.setUserName(sharedPreferences.getString(userName, null));
        user.setIdCardImage(sharedPreferences.getString(idCardImage, null));
        user.setCompanyName(sharedPreferences.getString(companyName, null));
        user.setBusinessAddress(sharedPreferences.getString(businessAddress, null));
        user.setCompanyProfile(sharedPreferences.getString(companyProfile, null));
        user.setTelephone(sharedPreferences.getString(telephone, null));
        user.setFax(sharedPreferences.getString(fax, null));
        user.setBusinessLicenseImage(sharedPreferences.getString(businessLicenseImage, null));
        user.setBusinessLicenseNumber(sharedPreferences.getString(businessLicenseNumber, null));
        user.setEmail(sharedPreferences.getString(email, null));
        user.setQq(sharedPreferences.getString(qq, null));
        user.setWeixin(sharedPreferences.getString(weixin, null));

        user.setMobilephone(sharedPreferences.getString(Mobilephone, null));
        user.setNickName(sharedPreferences.getString(NickName, null));
        user.setHeadImage(sharedPreferences.getString(HeadImage, null));
        Logger.d("UserRecord", new Gson().toJson(user));
        return user;
    }

    @Override
    protected String SPName() {
        return "User";
    }
}
