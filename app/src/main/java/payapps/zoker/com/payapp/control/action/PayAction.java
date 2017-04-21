package payapps.zoker.com.payapp.control.action;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.zekers.network.base.HttpMethod;
import com.zekers.network.base.RxNetHelper;
import com.zekers.network.data.DataWrapper;
import com.zekers.network.exception.MethodException;
import com.zekers.utils.encryption.RsaUtil;
import com.zekers.utils.logger.Logger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import payapps.zoker.com.payapp.control.api.PayService;
import payapps.zoker.com.payapp.control.record.TokenRecord;
import payapps.zoker.com.payapp.control.record.UserRecord;
import payapps.zoker.com.payapp.model.BankCard;
import payapps.zoker.com.payapp.model.BankCardList;
import payapps.zoker.com.payapp.model.CityList;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.ContactMans;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.FirstList;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.ImageResult;
import payapps.zoker.com.payapp.model.ProductList;
import payapps.zoker.com.payapp.model.ProvinceList;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.model.SecondList;
import payapps.zoker.com.payapp.model.User;
import payapps.zoker.com.payapp.view.Constant;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/9.
 */

public class PayAction {
    private PayService service;
    private final String TAG = this.getClass().getSimpleName();
    public static final int vercode_logup = 1;
    public static final int vercode_login = 2;
    public static final int vercode_forget = 3;
    public static final int vercode_AddBankCard = 5;
    public static final int vercode_phone_change = 4;

    {
        service = HttpMethod.getService(HttpMethod.API_PAY, PayService.class);
        if (service == null)
            throw new MethodException(MethodException.API_NULL);
    }

    /**
     * 发送验证码
     *
     * @param mobilePhone
     * @param captchaType 0 常规 1 注册 2 登录 3 找回密码 4 更改手机  5 添加银行卡
     * @return
     */
    public Observable<DataWrapper> sendMobileCaptcha(String mobilePhone, int captchaType) {
        return service.sendMobileCaptcha(mobilePhone, captchaType)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers())
                .map(new RxNetHelper.LogFunc<DataWrapper>());
    }

    /**
     * 登陆
     *
     * @param phone
     * @param password
     * @param captcha
     * @return
     */
    public Observable<User> userLogin(String phone, String password, String captcha) {
        try {
            password = getRsaPasswordwithNow(password);
            Logger.d(TAG, password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return service.userLogin(phone, password, captcha)
                .compose(RxNetHelper.<User>handleResult())
                .map(new RxNetHelper.LogFunc<User>())
                .compose(RxNetHelper.<User>applySchedulers());
    }

    /**
     * 忘记密码
     *
     * @param phone
     * @param password
     * @param captcha
     * @return
     */
    public Observable<User> ForgotPassword(String phone, String password, String captcha) {
        try {
            password = getRsaPasswordwithNow(password);
            Logger.d(TAG, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return service.ForgotPassword(phone, password, captcha)
                .compose(RxNetHelper.<User>handleResult())
                .map(new RxNetHelper.LogFunc<User>())
                .compose(RxNetHelper.<User>applySchedulers());
    }

    /**
     * 注册
     * @param phone
     * @param password
     * @param captcha
     * @return
     */
    public Observable<User> userRegister(String phone, String password, String captcha) {
        try {
            password = getRsaPasswordwithNow(password);
            Logger.d(TAG, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return service.userRegister(phone, password, captcha)
                .compose(RxNetHelper.<User>handleResult())
                .map(new RxNetHelper.LogFunc<User>())
                .compose(RxNetHelper.<User>applySchedulers());
    }

    /**
     * 上传身份证
     *
     * @param imageString
     * @return
     */
    public Observable<ImageResult> UploadIdCardImage(String imageString) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("userId=" + userId + "token=" + password + "imgString=" + imageString);
        return service.UploadIdCardImage(userId, password, imageString)
                .compose(RxNetHelper.<ImageResult>handleResult())
                .compose(RxNetHelper.<ImageResult>applySchedulers());
    }

    /**
     * 上传头像
     *
     * @param imageString
     * @return
     */
    public Observable<ImageResult> UploadHeadImage(String imageString) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Map<String, String> params = new HashMap<>();
        params.put("UserID", userId);
        params.put("ImageString", imageString);
        params.put("Password", password);
        Logger.d("userId=" + userId + "token=" + password + "imgString=" + imageString);
        return service.UploadHeadImage(params)
                .compose(RxNetHelper.<ImageResult>handleResult())
                .compose(RxNetHelper.<ImageResult>applySchedulers());
    }

    /**
     * 上传营业执照
     *
     * @param imageString
     * @return
     */
    public Observable<ImageResult> UploadBusinessLicenseImage(String imageString) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("userId=" + userId + "token=" + password + "imgString=" + imageString);
        return service.UploadBusinessLicenseImage(userId, password, imageString)
                .compose(RxNetHelper.<ImageResult>handleResult())
                .compose(RxNetHelper.<ImageResult>applySchedulers());
    }

    /**
     * 修改手机号码
     *
     * @return
     */
    public Observable<DataWrapper> UpdateMobilephone(String newMobilephone, String captcha) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.UpdateMobilephone(userId, password, newMobilephone, captcha)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 修改密码
     *
     * @return
     */
    public Observable<DataWrapper> UpdatePassword(String newPassword) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.UpdatePassword(userId, password, newPassword)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 更新用户营业执照认证信息
     *
     * @return
     */
    public Observable<DataWrapper> UpdateUserBusinessLicenseAuth(String companyName, String businessLicenseImage, String businessLicenseNumber) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.UpdateUserBusinessLicenseAuth(userId, password, companyName, businessLicenseImage, businessLicenseNumber)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 更新企业基础信息
     * 通过
     * @return
     */
    public Observable<DataWrapper> UpdateCompanyBasicInfo(String companyName, String businessLicenseNumber, String businessAddress, String telephone, String companyProfile) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.UpdateCompanyBasicInfo(userId, password, companyName, businessLicenseNumber, businessAddress, telephone, companyProfile)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 更新用户身份认证信息
     *
     * @return
     */
    public Observable<DataWrapper> UpdateUserIdCardAuth(String userName, String idCardImage, String idCardNumber) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.UpdateUserIdCardAuth(userId, password, userName, idCardImage, idCardNumber)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 更新用户基础信息
     *
     * @return
     */
    public Observable<DataWrapper> UpdateUserBasicInfo(User user) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("payAction","user="+new Gson().toJson(user));
        return service.UpdateUserBasicInfo(userId, password, user.getNickName(),user.getHeadImage(),user.getEmail(),user.getSexValue(),user.getProvinceID(),user.getProvinceName(),user.getCityID(),user.getCityName(),user.getSignature())
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }


    /**
     * 获取省份列表
     *
     * @return
     */
    public Observable<ProvinceList> GetProvinceList() {
        return service.GetProvinceList()
                .compose(RxNetHelper.<ProvinceList>handleResult())
                .compose(RxNetHelper.<ProvinceList>applySchedulers());
    }

    /**
     * 获取城市通过id
     *
     * @return
     */
    public Observable<CityList> GetCityListByProvinceID(String province) {
        return service.GetCityListByProvinceID(province)
                .compose(RxNetHelper.<CityList>handleResult())
                .compose(RxNetHelper.<CityList>applySchedulers());
    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public Observable<User> GetUserInfo() {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("password=" + password + ",userId=" + userId);
        return service.GetUserInfo(userId, password)
                .compose(RxNetHelper.<User>handleResult())
                .compose(RxNetHelper.<User>applySchedulers());
    }


    //银行卡
    /**
     * 获取银行卡列表
     */
    public Observable<BankCardList> GetBankCardList() {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.GetBankCardList(userId,password)
                .compose(RxNetHelper.<BankCardList>handleResult())
                .compose(RxNetHelper.<BankCardList>applySchedulers());
    }

    /**
     * 绑定银行卡
     */
    public Observable<BankCard> AddBankCard(String BankName, String CardNumber, String BankMobilephone, String Captcha) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        return service.AddBankCard(userId,password,BankName,CardNumber,BankMobilephone,Captcha)
                .compose(RxNetHelper.<BankCard>handleResult())
                .compose(RxNetHelper.<BankCard>applySchedulers());
    }


    /**
     * 删除银行卡
     */
    public Observable<DataWrapper> DeleteBankCard(String BankCardID) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"BankCardID="+BankCardID);
        return service.DeleteBankCard(userId,password,BankCardID)
                .compose(RxNetHelper.<DataWrapper>handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }


    /**
     * 设置银行卡活跃状态
     */
    public Observable<DataWrapper> UpdateBankCardStatus(String BankCardID) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"BankCardID="+BankCardID);
        return service.UpdateBankCardStatus(userId,password,BankCardID)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }



    //订单模块
    /**
     * 创建付款单
     */
    public Observable<Object> AddOrder(String PayUserName,String PayMobilephone,String FirstCateID,String SecondCateID,String OrderProductJson) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d(TAG,"payUserName="+PayUserName);
        Logger.d(TAG,"PayMobilephone="+PayMobilephone);
        Logger.d(TAG,"FirstCateID="+FirstCateID);
        Logger.d(TAG,"SecondCateID="+SecondCateID);
        Logger.d(TAG,"OrderProductJson="+OrderProductJson);
        return service.AddOrder(userId,password,PayUserName,PayMobilephone, FirstCateID,SecondCateID,OrderProductJson)
                .compose(RxNetHelper.<Object>handleResult())
                .compose(RxNetHelper.<Object>applySchedulers());
    }

    /**
     * 取消付款单
     */
    public Observable<DataWrapper> CancelPayOrder(String OrderID){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"OrderID"+OrderID);
        return service.CancelPayOrder(userId,password,OrderID)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 获得付款订单列表
     * 1 未付款 2已付款 3已取消
     */
    public Observable<List<Details>> GetPayOrderList(int OrderStatus, int PageIndex){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"OrderStatus="+OrderStatus+" PageIndex"+PageIndex);
        return service.GetPayOrderList(userId,password,OrderStatus,PageIndex)
                .compose(RxNetHelper.<List<Details>>handleResult())
                .compose(RxNetHelper.<List<Details>>applySchedulers());
    }


    /**
     * 获取付款订单详情
     */
    public Observable<Details> GetPayOrderDetail(String OrderID){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"OrderID="+OrderID);
        return service.GetPayOrderDetail(userId,password,OrderID)
                .compose(RxNetHelper.<Details>handleResult())
                .compose(RxNetHelper.<Details>applySchedulers());
    }




    /**
     * 取消收款单
     */
    public Observable<DataWrapper> CancelOrder(String OrderID){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"OrderID"+OrderID);
        return service.CancelOrder(userId,password,OrderID)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 获取收款单列表
     * 1 未付款 2已付款 3已取消
     */
    public Observable<List<Collection>> GetOrderList(int OrderStatus,int PageIndex){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"OrderStatus="+OrderStatus+" PageIndex"+PageIndex);
        return service.GetOrderList(userId,password,OrderStatus,PageIndex)
                .compose(RxNetHelper.<List<Collection>>handleResult())
                .compose(RxNetHelper.<List<Collection>>applySchedulers());
    }


    /**
     * 获取订单详情
     */
    public Observable<Collection> GetOrderDetail(String OrderID){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"OrderID="+OrderID);
        return service.GetOrderDetail(userId,password,OrderID)
                .compose(RxNetHelper.<Collection>handleResult())
                .compose(RxNetHelper.<Collection>applySchedulers());
    }







    //商品模块
    /**
     * 添加商品
     *  productName,    货品标题
     *  firstCateID,    一级类别ID
     *  secondCateID,   二级类别ID
     *  threeCateID,    三级类别ID
     *  price,          销售价
     *  unit,           单位
     *  productCover,   货品封面
     *  productImage    货品图片
     * @return
     */
    public Observable<DataWrapper> AddProduct(String productName, String firstCateID, String secondCateID, String threeCateID, String price, String unit, String productCover, String productImage) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("password=" + password + ",userId=" + userId);
        Logger.d("productName=" + productName + ",firstCateID=" + firstCateID + ",secondCateID=" + secondCateID + ",threeCateID=" + threeCateID + ",price=" + price + ",unit=" + unit + ",productCover=" + productCover + ",productImage=" + productImage);
        return service.AddProduct(userId, password, productName, firstCateID, secondCateID, threeCateID, price, unit, productCover, productImage)
                .compose(RxNetHelper.handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    /**
     * 获取一级类别
     *
     * @return
     */
    public Observable<FirstList> GetFirstList() {
        return service.GetFirstList()
                .compose(RxNetHelper.<FirstList>handleResult())
                .compose(RxNetHelper.<FirstList>applySchedulers());
    }

    /**
     * 获取二级类别
     *
     * @return
     */
    public Observable<SecondList> GetSecondList(String firstCateID) {
        return service.GetSecondList(firstCateID)
                .compose(RxNetHelper.<SecondList>handleResult())
                .compose(RxNetHelper.<SecondList>applySchedulers());
    }
    /**
     * 获取商品列表
     *
     * @return
     */
    public Observable<ProductList> GetProductList(int PageIndex) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("password=" + password + ",userId=" + userId);
        return service.GetProductList(userId, password,PageIndex)
                .compose(RxNetHelper.<ProductList>handleResult())
                .compose(RxNetHelper.<ProductList>applySchedulers());
    }
    /**
     * 删除商品
     *
     * @return
     */
    public Observable<DataWrapper> DeleteProduct(String ProductID) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("ProductID=" + ProductID);
        return service.DeleteProduct(userId, password, ProductID)
                .compose(RxNetHelper.<DataWrapper>handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }

    //通讯录模块
    /**
     * 获取通讯录
     *      **
     * @return
     */
    public Observable<ContactMans> GetAddressBook() {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Logger.d("password=" + password + ",userId=" + userId);
        return service.GetAddressBook(userId, password)
                .compose(RxNetHelper.<ContactMans>handleResult())
                .compose(RxNetHelper.<ContactMans>applySchedulers());
    }

    /**
     * 删除联系人
     */
    public Observable<DataWrapper> DeleteAddressBook(String AddressBookID) {
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"AddressBookID="+AddressBookID);
        return service.DeleteAddressBook(userId,password,AddressBookID)
                .compose(RxNetHelper.<DataWrapper>handleDataWrapper())
                .compose(RxNetHelper.<DataWrapper>applySchedulers());
    }



    /**
     * 获取收款单列表
     * 1 未付款 2已付款 3已取消
     */
    public Observable<List<Records>> GetTransactionList(int PageIndex){
        String password = TokenRecord.getInstance().load();
        String userId = UserRecord.getInstance().load().getId();
        Log.d(TAG,"PageIndex="+PageIndex);
        return service.GetTransactionList(userId,password,PageIndex)
                .compose(RxNetHelper.<List<Records>>handleResult())
                .compose(RxNetHelper.<List<Records>>applySchedulers());
    }



    /**
     * Rsa加密
     */
    public static String getRsaPassword(String password) throws Exception {
        Logger.d("payAction", "password=" + password);
        byte[] encryptByte = RsaUtil.encryptByPublicKey(password.getBytes(), Constant.rsaCode);
        return Base64.encodeToString(encryptByte, Base64.DEFAULT);
    }

    /**
     * Rsa加密//加上提交时间点
     */
    public static String getRsaPasswordwithNow(String password) throws Exception {
        String timePassword = password + String.valueOf(System.currentTimeMillis()).substring(0, 10);
        Logger.d("payAction", "timePassword=" + timePassword);
        byte[] encryptByte = RsaUtil.encryptByPublicKey(timePassword.getBytes(), Constant.rsaCode);
        return Base64.encodeToString(encryptByte, Base64.DEFAULT);
    }
}
