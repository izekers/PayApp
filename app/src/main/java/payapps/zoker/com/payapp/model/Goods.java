package payapps.zoker.com.payapp.model;

import android.util.Log;

import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.utils.logger.Logger;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 货物详情
 * Created by Administrator on 2017/3/4.
 */

public class Goods implements VisitableTypeControl.Visitable,Serializable {
    private String OrderProductID="0";
    private String OrderID="0";
    private String OrderNo="";
    private float Cost=0;
    private String ProductID="0";
    private String UserID="0";
    private String ProductName="";
    private String FirstCateID="0";
    private String SecondCateID="0";
    private String ThreeCateID="0";
    private float Price=0;
    private String Unit="单";
    private String ProductCover="";
    private String ProductImage="";
    private int Quantity=0;
    private String ProductTime;

    public String getProductTime() {
        return ProductTime;
    }

    public void setProductTime(String productTime) {
        ProductTime = productTime;
    }

    public String getOrderProductID() {
        return OrderProductID;
    }

    public void setOrderProductID(String orderProductID) {
        OrderProductID = orderProductID;
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

    public float getCost() {
        if (Cost==0)
            return Cost;
        return Float.valueOf(decaima(Cost));
    }

    public static String decaima(float cost){
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p=decimalFormat.format(cost);//format 返回的是字符串
        Logger.e("Goods","cost="+cost);
        Logger.e("Goods","p="+p);
        if (cost<1)
            p="0"+p;
        return p;
    }
    public void setCost(float cost) {
        Cost = cost;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public void addQuantity() {
        Quantity++;
    }
    public void jianQuantity() {
        if (Quantity>0)
            Quantity--;
    }


    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductID() {
        return ProductID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
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

    public String getThreeCateID() {
        return ThreeCateID;
    }

    public void setThreeCateID(String threeCateID) {
        ThreeCateID = threeCateID;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getProductCover() {
        return ProductCover;
    }

    public void setProductCover(String productCover) {
        ProductCover = productCover;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    @Override
    public int type(VisitableTypeControl.TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public String toString() {
        return ProductName+"         数量:"+Quantity;
    }
}
