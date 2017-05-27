package payapps.zoker.com.payapp.view;

/**
 * Created by Zoker on 2017/3/2.
 */

public class Constant {
    public static final String rsaCode="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhQg6PquOyX15BywcKQuo1MjihrPEkHw57JbH1PNAFWaMUKgmk8rnb6RQNY4OlyuULBnHEZIbNSJd1sA+FPDZ8n50wvgaZ+ITZG/jhp7lvmFNbt5R69mD3i4GA9BlM2VDeqdrGDw/o+mnD3MxdAFALCmSSTo6W7ohYN5M3ltL4WQIDAQAB";
    public static final String rsaCodes="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhQg6PquOyX15BywcKQuo1MjihrPEkHw57JbH1PNAFWaMUKgmk8rnb6RQNY4OlyuULBnHEZIbNSJd1sA+FPDZ8n50wvgaZ+ITZG/jhp7lvmFNbt5R69mD3i4GA9BlM2VDeqdrGDw/o+mnD3MxdAFALCmSSTo6W7ohYN5M3ltL4WQIDAQAB";
    public static final int QR_RUQST = 323;
    public static final String TRAD_TYPE="trad_type";
    public enum Trad_Type{
        PAY,COLLECTION
    }
    public static final String PAY_TYPE="pay_type";
    public enum  Pay_Type{
        HAS,WAIT,ALL
    }
    public static final String FRAGMENT_TYPE="fragment_type";
    public enum  Fragment_Type{
        USER,RECORD
    }
    public static final String IS_HIDE_BACK="is_hide_back";

    public static final String CHANGETYPE="changeType";
    public enum ChangeType{
        NAME,EMAIL,ADDRESS,SEX,SIGNATURE
    }
    public static final String SEX="sex";
    public static final String CHANGE_TXT="change_text";
    public static final String CHANGE_TXT_BIG="change_text_big";
    public static final String CHANGE_PROVINCE="change_province";
    public static final String CHANGE_CITY="change_city";
    public enum SexType{
        MAN,WOMAN
    }
    public static final String ORDER_ID="ORDER_ID";
    public static final String IS_SEARCH_ORDER_="IS_SEARCH_ORDER_";
    public static final String keyWord = "keyWord";
}
