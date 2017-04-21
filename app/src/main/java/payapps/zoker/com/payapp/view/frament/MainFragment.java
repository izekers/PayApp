package payapps.zoker.com.payapp.view.frament;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zekers.pjutils.BaseApplication;
import com.zekers.utils.CommonUtils;
import com.zekers.utils.logger.Logger;
import com.zekers.utils.permission.PermissionCallback;
import com.zekers.utils.permission.PermissionManager;

import payapps.zoker.com.payapp.R;
import payapps.zoker.com.payapp.view.Activity.CollectionActivity;
import payapps.zoker.com.payapp.view.Activity.GoodsActivity;
import payapps.zoker.com.payapp.view.Activity.LoadFragmentActivity;
import payapps.zoker.com.payapp.view.Activity.OrderCollectionActivity;
import payapps.zoker.com.payapp.view.Activity.OrderPayActivity;
import payapps.zoker.com.payapp.view.Activity.PayActivity;
import payapps.zoker.com.payapp.view.Constant;


/**
 * Created by Zoker on 2017/3/2.
 */
public class MainFragment extends Fragment {
    View root_view, collectionView, qrView;
    View user_layout,trad_layout,new_order_layout,goods_layout,pay_layout,collection_layout;
    EditText search;
    public static MainFragment getInstance() {
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initListener();
        return root_view;
    }

    public void initView() {
        qrView = root_view.findViewById(R.id.home_qr);
        collectionView = root_view.findViewById(R.id.home_collection);
        user_layout = root_view.findViewById(R.id.user_layout);
        trad_layout = root_view.findViewById(R.id.trad_layout);
        new_order_layout = root_view.findViewById(R.id.new_order_layout);
        goods_layout = root_view.findViewById(R.id.goods_layout);
        pay_layout = root_view.findViewById(R.id.pay_layout);
        collection_layout = root_view.findViewById(R.id.collection_layout);
        search=(EditText) root_view.findViewById(R.id.search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //回车键
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    BaseApplication.Instance.showToast("搜索");
                }
                return true;
            }
        });
    }

    protected static void checkPermisstionAndrThenLoad(Activity activity, final String permission, final PermissionCallback PermisstionCallBack) {
        Logger.d("PhotoSelectUtils", "#checkPermisstionAndrThenLoad");
        if (PermissionManager.checkPermission(permission)) {
            Logger.d("permissionManager", "#checkPermisstionAndrThenLoad");
            PermisstionCallBack.permissionGranted();
        } else {
            if (PermissionManager.shouldShowRequestPermissionRationale(activity, permission)) {
                Logger.d("permissionManager", "#shouldShowRequestPermissionRationale");
                PermissionManager.askForPermission(activity, permission, PermisstionCallBack);
            } else {
                PermissionManager.askForPermission(activity, permission, PermisstionCallBack);
            }
        }
    }

    public void initListener() {

        //扫码付
        qrView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isMarshmallow()) {
                    checkPermisstionAndrThenLoad(getActivity(), Manifest.permission.CAMERA, new PermissionCallback() {
                        @Override
                        public void permissionGranted() {
                            Intent intent = new Intent(getContext(), CaptureActivity.class);
                            startActivityForResult(intent, Constant.QR_RUQST);
                        }

                        @Override
                        public void permissionRefused() {
                            BaseApplication.Instance.showToast("无权限调用照相机");
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), CaptureActivity.class);
                    startActivityForResult(intent, Constant.QR_RUQST);
                }
            }
        });

        //收款
        collectionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PayActivity.class);
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.COLLECTION);
                startActivity(intent);
            }
        });

        //用户中心
        user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadFragmentActivity.class);
                intent.putExtra(Constant.FRAGMENT_TYPE, Constant.Fragment_Type.USER);
                startActivity(intent);
            }
        });

        //交易记录
        trad_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoadFragmentActivity.class);
                intent.putExtra(Constant.FRAGMENT_TYPE, Constant.Fragment_Type.RECORD);
                startActivity(intent);
            }
        });
        /**
         *
         */
        //新建订单
        new_order_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CollectionActivity.class);
                startActivity(intent);
            }
        });

        //管理商品
        goods_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GoodsActivity.class);
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.COLLECTION);
                startActivity(intent);
            }
        });

        /**
         *
         */
        //付款单
        pay_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PayActivity.class);
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.PAY);
                startActivity(intent);
            }
        });
        //收款单
        collection_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PayActivity.class);
                intent.putExtra(Constant.TRAD_TYPE, Constant.Trad_Type.COLLECTION);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.QR_RUQST) {
            if (resultCode == Activity.RESULT_OK) {
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) return;
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        if (result.contains("&")){
                            String[] results=result.split("&");
                            if (results.length==2){
                                String OrderID= results[0];
                                String getPayUserID=results[1];

                                Intent intent = new Intent(getContext(), OrderCollectionActivity.class);
                                intent.putExtra(Constant.ORDER_ID, OrderID);
                                startActivity(intent);
                            }
                        }
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
