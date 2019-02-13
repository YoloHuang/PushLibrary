package com.kidosc.pushlibrary.rom.xiaomi;

import android.app.Application;
import android.util.Log;

import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.rom.BasePushTargetInit;
import com.kidosc.pushlibrary.util.ApplicationUtil;
import com.xiaomi.mipush.sdk.MiPushClient;

/**
 * 小米推送的初始化
 * Created by luoming on 2018/5/28.
 */

public class XiaomiInit extends BasePushTargetInit {

    public XiaomiInit(Application context) {
        super(context);
        //注册SDK
        String appId = ApplicationUtil.getMetaData(context, "XMPUSH_APPID");
        String appKey = ApplicationUtil.getMetaData(context, "XMPUSH_APPKEY");
        MiPushClient.registerPush(context, appId.replaceAll(" ", ""), appKey.replaceAll(" ", ""));
    }


    @Override
    public void setAlias(String alias) {
        MiPushClient.setAlias(mContext, alias, null);
    }

    @Override
    public void loginOut() {
        MiPushClient.unsetAlias(mContext, mAlias, null);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(mContext.getString(R.string.tip_loginOut));
        aliasInfo.setContent(mAlias);
        aliasInfo.setPushTarget(PushTargetEnum.XIAOMI);
        PushReceiverHandleManager.getInstance().onLoginOut(mContext,aliasInfo);
    }
}
