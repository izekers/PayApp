package payapps.zoker.com.payapp.control.api;

import com.zekers.network.data.DataWrapper;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import payapps.zoker.com.payapp.model.BankCard;
import payapps.zoker.com.payapp.model.BankCardList;
import payapps.zoker.com.payapp.model.CityList;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.ContactMan;
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
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/9.
 */

public interface PayService {
    /**
     * 发送验证码
     * ##
     * @return
     */
    @GET("webservice/webapi.asmx/SendMobileCaptcha")
    Observable<DataWrapper> sendMobileCaptcha(@Query("Mobilephone") String mobilePhone, @Query("CaptchaType") int captchaType);

    /**
     * 登录
     * ##
     * @param mobilePhone phone
     * @param password    token
     * @param captcha     vercode
     * @return
     */
    @GET("webservice/webapi.asmx/UserLogin")
    Observable<DataWrapper<User>> userLogin(@Query("Mobilephone") String mobilePhone, @Query("Password") String password, @Query("Captcha") String captcha);
    /**
     * 忘记密码
     * ##
     * @param mobilePhone phone
     * @param password    token
     * @param captcha     vercode
     * @return
     */
    @GET("webservice/webapi.asmx/ForgotPassword")
    Observable<DataWrapper<User>> ForgotPassword(@Query("Mobilephone") String mobilePhone, @Query("NewPassword") String password, @Query("Captcha") String captcha);

    /**
     * 注册
     * ##
     * @param mobilePhone
     * @param password
     * @param captcha
     * @return
     */
    @GET("webservice/webapi.asmx/UserRegister")
    Observable<DataWrapper<User>> userRegister(@Query("Mobilephone") String mobilePhone, @Query("Password") String password, @Query("Captcha") String captcha);

    /**
     * 上传身份证
     * ##
     * @param userId      id
     * @param password    token
     * @param imageString img
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UploadIdCardImage")
    Observable<DataWrapper<ImageResult>> UploadIdCardImage(@Field("UserID") String userId, @Field("Password") String password, @Field("ImageString") String imageString);

    /**
     * 上传头像
     *  ##
     * UserID
     * Password
     * ImageString
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UploadHeadImage")
    Observable<DataWrapper<ImageResult>> UploadHeadImage(@FieldMap Map<String, String> map);

    /**
     * 上传营业执照
     * ##
     * @param userId
     * @param password
     * @param imageString
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UploadBusinessLicenseImage")
    Observable<DataWrapper<ImageResult>> UploadBusinessLicenseImage(@Field("UserID") String userId, @Field("Password") String password, @Field("ImageString") String imageString);

    /**
     * 更新用户营业执照认证信息
     * ##
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdateUserBusinessLicenseAuth")
    Observable<DataWrapper> UpdateUserBusinessLicenseAuth(@Field("UserID") String userId, @Field("Password") String password, @Field("CompanyName") String companyName, @Field("BusinessLicenseImage") String businessLicenseImage, @Field("BusinessLicenseNumber") String businessLicenseNumber);

    /**
     * 获取省份列表
     * ##
     * @return
     */
    @GET("webservice/webapi.asmx/GetProvinceList")
    Observable<DataWrapper<ProvinceList>> GetProvinceList();

    /**
     * 获取省份通过id
     * ##
     * @return
     */
    @GET("webservice/webapi.asmx/GetCityListByProvinceID")
    Observable<DataWrapper<CityList>> GetCityListByProvinceID(@Query("ProvinceID") String province);
    /**
     * 修改手机号码
     * ##
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdateMobilephone")
    Observable<DataWrapper> UpdateMobilephone(@Field("UserID") String userId, @Field("Password") String password, @Field("NewMobilephone") String newMobilephone, @Field("Captcha") String captcha);

    /**
     * 修改密码
     * ##
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdatePassword")
    Observable<DataWrapper> UpdatePassword(@Field("UserID") String userId, @Field("Password") String password, @Field("NewPassword") String newPassword);

    /**
     * 获取用户信息
     * ##
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetUserInfo")
    Observable<DataWrapper<User>> GetUserInfo(@Field("UserID") String userId, @Field("Password") String password);

    /**
     * 更新用户身份认证信息
     * ##
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdateUserIdCardAuth")
    Observable<DataWrapper> UpdateUserIdCardAuth(@Field("UserID") String userId, @Field("Password") String password, @Field("UserName") String userName, @Field("IdCardImage") String idCardImage, @Field("IdCardNumber") String idCardNumber);

    /**
     * 更新企业基础信息
     * ##
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdateCompanyBasicInfo")
    Observable<DataWrapper> UpdateCompanyBasicInfo(@Field("UserID") String userId, @Field("Password") String password, @Field("CompanyName") String companyName, @Field("BusinessLicenseNumber") String businessLicenseNumber, @Field("BusinessAddress") String businessAddress, @Field("Telephone") String telephone, @Field("CompanyProfile") String companyProfile);
    //账号   18102799710
    //密码   123456
    //验证码 8523

    /**
     * 更新用户基础信息
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdateUserBasicInfo")
    Observable<DataWrapper> UpdateUserBasicInfo(@Field("UserID") String userId, @Field("Password") String password, @Field("NickName") String nickName, @Field("HeadImage") String headImage, @Field("Email") String email, @Field("Sex") int sex, @Field("ProvinceID") String provinceID, @Field("ProvinceName") String provinceName, @Field("cityID") String cityID, @Field("cityName") String cityName,@Field("Signature") String signature);

    //商品
    /**
     * 添加商品
     * @param productName   货品标题
     * @param firstCateID   一级类别ID
     * @param secondCateID  二级类别ID
     * @param threeCateID   三级类别ID
     * @param price         销售价
     * @param unit          单位
     * @param productCover  货品封面
     * @param productImage  货品图片
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/AddProduct")
    Observable<DataWrapper> AddProduct(@Field("UserID") String userId, @Field("Password") String password, @Field("ProductName") String productName, @Field("FirstCateID") String firstCateID, @Field("SecondCateID") String secondCateID, @Field("ThreeCateID") String threeCateID, @Field("Price") String price, @Field("Unit") String unit, @Field("ProductCover") String productCover, @Field("ProductImage") String productImage);

    /**
     * 获取商品列表
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetProductList")
    Observable<DataWrapper<ProductList>> GetProductList(@Field("UserID") String userId, @Field("Password") String password, @Field("PageIndex") int PageIndex);

    /**
     * 删除商品
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/DeleteProduct")
    Observable<DataWrapper> DeleteProduct(@Field("UserID") String userId, @Field("Password") String password, @Field("ProductID") String ProductID);

    /**
     * 获取一级类别
     *
     * @return
     */
    @GET("webservice/webapi.asmx/GetFirstList")
    Observable<DataWrapper<FirstList>> GetFirstList();

    /**
     * 获取二级类别
     * @param firstCateID
     * @return
     */
    @GET("webservice/webapi.asmx/GetSecondList")
    Observable<DataWrapper<SecondList>> GetSecondList(@Query("FirstCateID") String firstCateID);

    //银行卡
    /**
     * 绑定银行卡
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/AddBankCard")
    Observable<DataWrapper<BankCard>> AddBankCard(@Field("UserID") String userId, @Field("Password") String password, @Field("BankName") String BankName, @Field("CardNumber") String CardNumber, @Field("BankMobilephone") String BankMobilephone, @Field("Captcha") String Captcha);
    /**
     * 获取银行卡列表
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetBankCardList")
    Observable<DataWrapper<BankCardList>> GetBankCardList(@Field("UserID") String userId, @Field("Password") String password);

    /**
     * 删除银行卡
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/DeleteBankCard")
    Observable<DataWrapper> DeleteBankCard(@Field("UserID") String userId, @Field("Password") String password,@Field("BankCardID") String BankCardID);

    /**
     * 设置银行卡活跃状态
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/UpdateBankCardStatus")
    Observable<DataWrapper> UpdateBankCardStatus(@Field("UserID") String userId, @Field("Password") String password,@Field("BankCardID") String BankCardID);




    //通讯录
    /**
     * 删除通讯录联系人
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/DeleteAddressBook")
    Observable<DataWrapper> DeleteAddressBook(@Field("UserID") String userId, @Field("Password") String password,@Field("AddressBookID") String AddressBookID);

    /**
     * 获取通讯录
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetAddressBook")
    Observable<DataWrapper<ContactMans>> GetAddressBook(@Field("UserID") String userId, @Field("Password") String password);



    //订单模块
    /**
     * 创建付款单
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/AddOrder")
    Observable<DataWrapper<Object>> AddOrder(@Field("UserID") String userId, @Field("Password") String password,@Field("PayUserName") String PayUserName,@Field("PayMobilephone") String PayMobilephone,@Field("FirstCateID") String FirstCateID,@Field("SecondCateID") String SecondCateID, @Field("OrderProductJson") String OrderProductJson);

    /**
     * 取消付款单
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/CancelPayOrder")
    Observable<DataWrapper> CancelPayOrder(@Field("UserID") String userId, @Field("Password") String password,@Field("OrderID") String OrderID);

    /**
     * 获取付款单列表
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetPayOrderList")
    Observable<DataWrapper<List<Details>>> GetPayOrderList(@Field("UserID") String userId, @Field("Password") String password, @Field("OrderStatus") int OrderStatus, @Field("PageIndex") int PageIndex);

    /**
     * 获取付款单详情
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetPayOrderDetail")
    Observable<DataWrapper<Details>> GetPayOrderDetail(@Field("UserID") String userId, @Field("Password") String password,@Field("OrderID") String OrderID);


    /**
     * 获取收款单搜索列表
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetOrderSearchList")
    Observable<DataWrapper<List<Collection>>> GetOrderSearchList(@Field("UserID") String userId, @Field("Password") String password, @Field("KeyWords") String KeyWords, @Field("OrderStatus") int OrderStatus, @Field("PageIndex") int PageIndex);

    /**
     * 获取付款单搜索列表
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetPayOrderSearchList")
    Observable<DataWrapper<List<Details>>> GetPayOrderSearchList(@Field("UserID") String userId, @Field("Password") String password, @Field("KeyWords") String KeyWords, @Field("OrderStatus") int OrderStatus, @Field("PageIndex") int PageIndex);


    /**
     * 获取收款单列表
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetOrderList")
    Observable<DataWrapper<List<Collection>>> GetOrderList(@Field("UserID") String userId, @Field("Password") String password, @Field("OrderStatus") int OrderStatus, @Field("PageIndex") int PageIndex);

    /**
     * 获取收款单详情
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetOrderDetail")
    Observable<DataWrapper<Collection>> GetOrderDetail(@Field("UserID") String userId, @Field("Password") String password,@Field("OrderID") String OrderID);

    /**
     * 取消收款单
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/CancelOrder")
    Observable<DataWrapper> CancelOrder(@Field("UserID") String userId, @Field("Password") String password,@Field("OrderID") String OrderID);

    /**
     * 取消收款单
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/webapi.asmx/GetTransactionList")
    Observable<DataWrapper<List<Records>>> GetTransactionList(@Field("UserID") String userId, @Field("Password") String password, @Field("PageIndex") int PageIndex);

}
