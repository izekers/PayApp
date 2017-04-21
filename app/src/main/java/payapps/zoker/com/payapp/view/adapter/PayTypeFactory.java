package payapps.zoker.com.payapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.ui.view.recycler.VisitableViewHolder;
import com.zekers.utils.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.model.BankCard;
import payapps.zoker.com.payapp.model.Collection;
import payapps.zoker.com.payapp.model.ContactMan;
import payapps.zoker.com.payapp.model.Details;
import payapps.zoker.com.payapp.model.Goods;
import payapps.zoker.com.payapp.model.Records;
import payapps.zoker.com.payapp.view.adapter.viewholder.BankCardViewHolder;
import payapps.zoker.com.payapp.view.adapter.viewholder.CollectionViewHolder;
import payapps.zoker.com.payapp.view.adapter.viewholder.ContactManViewHolder;
import payapps.zoker.com.payapp.view.adapter.viewholder.GoodsViewHolder;
import payapps.zoker.com.payapp.view.adapter.viewholder.PayViewHolder;
import payapps.zoker.com.payapp.view.adapter.viewholder.RecordsViewHolder;

/**
 * 这里处理分类的情况，其他的任何情况都不处理
 * Created by Zoker on 2017/2/22.
 */
public class PayTypeFactory implements VisitableTypeControl.TypeFactory {
    private final int records_type = 1;
    private final int collection_type = 2;
    private final int pay_type = 3;
    private final int contactMan_type=4;
    private final int goods_type=5;
    private final int bankcard_type=6;
    private static PayTypeFactory instance;
    private Map<Integer, Integer> res_map= new HashMap<>();
    {
        res_map.put(records_type, R.layout.item_records);
        res_map.put(collection_type, R.layout.item_pay);
        res_map.put(pay_type, R.layout.item_pay);
        res_map.put(contactMan_type, R.layout.item_normal);
//        res_map.put(contactMan_type, R.layout.item_normal_2);
        res_map.put(goods_type, R.layout.item_goods);
//        res_map.put(bankcard_type, R.layout.item_normal);
        res_map.put(bankcard_type, R.layout.item_normal_2);
    }

    private PayTypeFactory() {
    }

    public static PayTypeFactory getInstance() {
        if (instance == null) {
            synchronized (PayTypeFactory.class) {
                instance = new PayTypeFactory();
            }
        }
        return instance;
    }

    @Override
    public int type(VisitableTypeControl.Visitable visitable) {
        if (visitable instanceof Records)
            return type((Records) visitable);
        if (visitable instanceof Details)
            return type((Details) visitable);
        if (visitable instanceof ContactMan)
            return type((ContactMan) visitable);
        if (visitable instanceof Goods)
            return type((Goods) visitable);
        if (visitable instanceof Collection)
            return type((Collection)visitable);
        if (visitable instanceof BankCard)
            return type((BankCard)visitable);
        return 0;
    }

    public int type(Records records) {
        return records_type;
    }

    public int type(Details details) {
        return pay_type;
    }

    public int type(ContactMan details) {
        return contactMan_type;
    }

    public int type(Goods goods) {
        return goods_type;
    }

    public int type(Collection collection){
        return collection_type;
    }
    public int type(BankCard bankCard){
        return bankcard_type;
    }
    @Override
    public VisitableViewHolder createViewHolder(int type, View itemView) {
        switch (type) {
            case records_type:
                return new RecordsViewHolder(itemView);
            case pay_type:
                return new PayViewHolder(itemView);
            case contactMan_type:
                return new ContactManViewHolder(itemView);
            case goods_type:
                return new GoodsViewHolder(itemView);
            case collection_type:
                return new CollectionViewHolder(itemView);
            case bankcard_type:
                return new BankCardViewHolder(itemView);
        }
        return null;
    }

    @Override
    public VisitableViewHolder createViewHolder(int type, ViewGroup parent) {
        switch (type) {
            case records_type:
                return null;
        }
        return null;
    }

    @Override
    public int getRes(int type) {
        return res_map.get(type);
    }
}
