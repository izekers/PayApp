package payapps.zoker.com.payapp.view.Activity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.zekers.network.base.RxSubscribe;
import com.zekers.pjutils.BaseApplication;
import com.zekers.ui.view.widget.AbilityToolBar;
import com.zekers.utils.ContactUtils;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.rx.EventBus.Events;
import com.zekers.utils.rx.EventBus.RxBus;
import com.zoker.base.DialogUtils;

import java.util.List;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.control.action.PayAction;
import payapps.zoker.com.payapp.control.contract.AddBookContract;
import payapps.zoker.com.payapp.control.presenter.AddBookPresenter;
import payapps.zoker.com.payapp.model.ContactMan;
import payapps.zoker.com.payapp.model.ProductList;
import payapps.zoker.com.payapp.view.Constant;
import payapps.zoker.com.payapp.view.adapter.ContactManSelectAdapter;
import payapps.zoker.com.payapp.view.adapter.GoodsSelectAdapter;
import payapps.zoker.com.payapp.view.adapter.SelectTypeFactory;
import rx.functions.Action1;

/**
 * Created by Administrator on 2017/3/25.
 */

public class ContactSelectActivity extends BaseActivity implements AddBookContract.View {
    AbilityToolBar abilityToolBar;
    RecyclerView recyclerView;
    ContactManSelectAdapter selectAdapter;
    int pagIndex = 1;
    PayAction payAction = new PayAction();

    AddBookContract.Presenter presenter = new AddBookPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        presenter.attachView(this);

        abilityToolBar = (AbilityToolBar) findViewById(R.id.ability_toolbar);
        abilityToolBar.setTitle("选择联系人");
        abilityToolBar.setOnBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        abilityToolBar.setMenuRes(R.menu.contact_menu);
        abilityToolBar.setOnMenuItemClickListener(new AbilityToolBar.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view) {
                if (view.getId() == R.id.add_book) {
                    ContactUtils.startActivityForResult(ContactSelectActivity.this);
                }
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        selectAdapter = new ContactManSelectAdapter(SelectTypeFactory.getInstance());
        recyclerView.setAdapter(selectAdapter);
        RxBus.getSubscriber().setEvent(Events.SELECT_CONTACTMAN)
                .onNext(new Action1<Events<?>>() {
                    @Override
                    public void call(Events<?> events) {
                        ContactMan contactMan = events.getContent();
                        returnResult(contactMan);
                    }
                })
                .create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null)
                return;
            Uri contactData = data.getData();
            if (contactData == null)
                return;
            ContentResolver reContentResolverol = getContentResolver();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            String phone_number = "";
            String contact_man = "";
            ContactMan contactMan = new ContactMan();
            if (cursor != null && cursor.moveToNext()) {
                contact_man = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                phone_number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                contactMan.setUserName(contact_man);
                Logger.d("tag", contact_man);
                Logger.d("tag", phone_number);
            }

            if (phone_number.equalsIgnoreCase("1")) {
                phone_number = "true";
            } else {
                phone_number = "false";
            }
            if (Boolean.parseBoolean(phone_number)) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null);
                while (phone.moveToNext()) {
                    phone_number = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactMan.setMobilephone(ContactUtils.getPhoneNumber(phone_number));
                    Logger.d("tag", "" + phone_number);
                    returnResult(contactMan);
                }
                phone.close();
            }else {
                try{
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null);
                    while (phone.moveToNext()) {
                        phone_number = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactMan.setMobilephone(phone_number);
                        Logger.d("tag", "" + phone_number);
                        returnResult(contactMan);
                    }
                    phone.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try{
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor phone = reContentResolverol.query(Uri.parse("content://icc/adn"),
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null);
                    while (phone.moveToNext()) {
                        phone_number = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactMan.setMobilephone(phone_number);
                        Logger.d("tag", "" + phone_number);
                        returnResult(contactMan);
                    }
                    phone.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                BaseApplication.Instance.showToast("获取联系人电话信息失败");
            }

        }
    }

    private void returnResult(ContactMan contactMan) {
        Intent intent = new Intent();
        intent.putExtra("contactmanSelect", contactMan);
        setResult(RESULT_OK, intent);
        finish();
    }

    Dialog dialog;

    @Override
    public void onResume() {
        super.onResume();
        presenter.GetAddressBook(false);
    }

    @Override
    public void success(List<ContactMan> data) {
        DialogUtils.success(dialog);
        selectAdapter.setmDatas(data);
    }

    @Override
    public void fail(String msg) {
        DialogUtils.fail(dialog, "获取通讯录失败，" + msg);
    }


    @Override
    public void loading() {
        dialog = DialogUtils.getLoadingDialog(this, "正在加载通讯录");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        DialogUtils.destroy(dialog);
    }
}
