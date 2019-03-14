package com.maidrobot.activity.wxapi;

import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseResp;

/**
 * Created by chandler on 16/4/12.
 */
public class WXPayEntryActivity extends qsbk.app.pay.wxapi.WXPayEntryActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 发起支付请求前需要更新微信支付APP_ID
        // PayConstants.WECHAT_APP_ID = "wechat_app_id";
    }

    @Override
    public void handleResp(BaseResp resp) {
        // 处理自己的微信支付响应
    }

}