package payapps.zoker.com.payapp.control.record;

import android.content.SharedPreferences;
import android.nfc.Tag;

import com.zekers.utils.cache.BaseRecord;
import com.zekers.utils.logger.Logger;

import payapps.zoker.com.payapp.model.User;

/**
 * Created by Administrator on 2017/3/9.
 */
public class TokenRecord extends BaseRecord<String>{
    private static final String TOKEN = "tokenStr";
    private static TokenRecord instance;
    private static String token=null;
    private TokenRecord(){}
    public static TokenRecord getInstance(){
        if (instance==null){
            synchronized (TokenRecord.class){
                instance=new TokenRecord();
            }
        }
        return instance;
    }
    @Override
    protected boolean Save(SharedPreferences.Editor editor, String token) {
        clearInfo();
        Logger.d("TokenRecord",token);
        editor.putString(TOKEN,token);
        Logger.d("TokenRecord",""+editor.commit());
        return true;
    }

    @Override
    protected String load(SharedPreferences sharedPreferences) {
        if (token!=null)
            return token;
        token=sharedPreferences.getString(TOKEN,null);
        Logger.d("TokenRecord",""+token);
        return token;
    }

    @Override
    public void clearInfo() {
        super.clearInfo();
        token=null;
    }

    @Override
    protected String SPName() {
        return "token";
    }
}
