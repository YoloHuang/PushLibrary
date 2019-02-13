package com.kidosc.pushlibrary.rom.jiguang;

import android.content.Context;

import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * 自定义的alin/tag设置接口回调
 * Created by luoming on 2018/5/28.
 */

public class IJPushEventReceiver extends JPushMessageReceiver {
    private static final String TAG = "IJPushEventReceiver";

    @Override
    public void onTagOperatorResult(Context var1, JPushMessage var2) {
    }

    @Override
    public void onCheckTagOperatorResult(Context var1, JPushMessage var2) {

    }

    @Override
    public void onAliasOperatorResult(Context var1, JPushMessage var2) {
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(var1.getString(R.string.tip_setalias));
        aliasInfo.setContent(var2.getAlias());
        aliasInfo.setPushTarget(PushTargetEnum.JPUSH);
        PushReceiverHandleManager.getInstance().onAliasSet(var1, aliasInfo);
    }

    @Override
    public void onMobileNumberOperatorResult(Context var1, JPushMessage var2) {

    }
}
