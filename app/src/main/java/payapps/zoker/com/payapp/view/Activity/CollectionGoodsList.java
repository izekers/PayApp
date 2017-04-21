package payapps.zoker.com.payapp.view.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zekers.utils.GsonUtils;

import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.Goods;

/**
 * Created by Administrator on 2017/3/25.
 */

public class CollectionGoodsList extends BaseActivity{
    List<Goods> goodslist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_goods);
        String goodsList=getIntent().getStringExtra("GoodsList");
        goodslist= GsonUtils.parseJsonArrayWithGson(goodsList,Goods.class);
    }
}
