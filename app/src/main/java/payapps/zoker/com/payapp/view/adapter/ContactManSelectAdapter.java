package payapps.zoker.com.payapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zekers.ui.view.recycler.VisitableTypeControl;
import com.zekers.ui.view.recycler.VisitableViewHolder;

import java.util.ArrayList;
import java.util.List;

import payapps.zoker.com.payapp.model.ContactMan;

/**
 * 这里只用在意排列的问题，不需要在意怎么分类，数据的绑定情况
 * Created by Zoker on 2017/2/22.
 */
public class ContactManSelectAdapter extends RecyclerView.Adapter<VisitableViewHolder> {
    private VisitableTypeControl.TypeFactory typeFactory;
    private List<ContactMan> mDatas;
    public ContactManSelectAdapter(VisitableTypeControl.TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
        this.mDatas=new ArrayList<>();
    }

    @Override
    public VisitableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return typeFactory.createViewHolder(viewType, LayoutInflater.from(parent.getContext()).inflate(typeFactory.getRes(viewType),parent,false));
    }

    @Override
    public void onBindViewHolder(VisitableViewHolder holder, int position) {
        holder.setUpView(mDatas.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.isEmpty()){
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas != null) {
            return mDatas.get(position).type(typeFactory);
        }
        return super.getItemViewType(position);
    }

    public void setmDatas(List<ContactMan> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<ContactMan> getmDatas() {
        return mDatas;
    }

    public ContactMan getSelectMan(){
        if (mDatas==null || mDatas.isEmpty())
            return null;
        for (ContactMan contactMan:mDatas){
            if (contactMan.isSelect())
                return contactMan;
        }
        return null;
    }
}
