package payapps.zoker.com.payapp.model;

import com.zekers.ui.view.recycler.VisitableTypeControl;

import java.io.Serializable;
import java.util.List;

import payapps.zoker.com.payapp.view.Constant;

/**
 * 付款订单详情
 * Created by Zoker on 2017/3/2.
 */

public class Details implements VisitableTypeControl.Visitable,Serializable{
    private Constant.Pay_Type type;

    private String OrderID; //订单ID
    private String OrderNo;//订单编号
    private String UserID;//收款人ID
    private String PayUserID;//付款人ID
    private String PayUserName;//付款人姓名
    private String PayMobilephone;//付款人电话
    private String Amount;//支付金额
    private String ProductPrice;//商品金额
    private String FreightPrice;//运费
    private int OrderStatus;//订单状态
    private String OrderTime;//下单时间
    private List<Goods> OrderProductList;
    private User User;

    public String getPayUserName() {
        return PayUserName;
    }

    public void setPayUserName(String payUserName) {
        PayUserName = payUserName;
    }

    public String getPayMobilephone() {
        return PayMobilephone;
    }

    public void setPayMobilephone(String payMobilephone) {
        PayMobilephone = payMobilephone;
    }

    public List<Goods> getOrderProductList() {
        return OrderProductList;
    }

    public void setOrderProductList(List<Goods> orderProductList) {
        OrderProductList = orderProductList;
    }

    public payapps.zoker.com.payapp.model.User getUser() {
        return User;
    }

    public void setUser(payapps.zoker.com.payapp.model.User user) {
        User = user;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPayUserID() {
        return PayUserID;
    }

    public void setPayUserID(String payUserID) {
        PayUserID = payUserID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getFreightPrice() {
        return FreightPrice;
    }

    public void setFreightPrice(String freightPrice) {
        FreightPrice = freightPrice;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public Constant.Pay_Type getType() {
        return type;
    }

    public void setType(Constant.Pay_Type type) {
        this.type = type;
    }

    @Override
    public int type(VisitableTypeControl.TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
