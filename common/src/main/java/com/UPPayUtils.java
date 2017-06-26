package com;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * Created by Administrator on 2017/6/7.
 */

public class UPPayUtils {
    /**
     * "00" - 启动银联正式环境 "01" - 连接银联测试环境
     */
    private static final String mMode = "01";

    public void init(Context context, String tn) {
        UPPayAssistEx.startPayByJAR(context, PayActivity.class, null, null,
                tn, mMode);
    }

    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data,PayFinishAction action) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }
        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
//                try {
//                    JSONObject resultJson = new JSONObject(result);
//                    String sign = resultJson.getString("sign");
//                    String dataOrg = resultJson.getString("data");
//                    // 验签证书同后台验签证书
//                    // 此处的verify，商户需送去商户后台做验签
//                    boolean ret = verify(dataOrg, sign, mMode);
//                    if (ret) {
//                        // 验证通过后，显示支付结果
//                        msg = "支付成功！";
//                    } else {
//                        // 验证不通过后的处理
//                        // 建议通过商户后台查询支付结果
                //               msg = "支付失败！";
//                    }
//                } catch (JSONException e) {
//                }
//            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                //               msg = "支付成功！";
//            }
                msg = "支付成功！";
                if (action!=null)
                    action.success();
            } else if (str.equalsIgnoreCase("fail")) {
                msg = "支付失败！";
                if (action!=null)
                    action.fail();
            } else if (str.equalsIgnoreCase("cancel")) {
                msg = "用户取消了支付";
                if (action!=null)
                    action.cancle();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setInverseBackgroundForced(true);
            // builder.setCustomTitle();
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }

    public interface PayFinishAction{
        void success();
        void fail();
        void cancle();
    }
}
