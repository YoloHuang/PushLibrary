package com.kidosc.pushlibrary.rom.jiguang;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.rom.BasePushTargetInit;
import com.kidosc.pushlibrary.util.PushLogUtil;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送的初始化服务
 * Created by luoming on 2018/5/28.
 */

public class JPushInit extends BasePushTargetInit {

    public JPushInit(Application application) {
        super(application);
        JPushInterface.init(application);
        PushLogUtil.d("JPushInit");
    }

    @Override
    public void loginIn(Activity activity) {
        super.loginIn(activity);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(mContext.getString(R.string.tip_loginIn));
        aliasInfo.setContent(mAlias);
        aliasInfo.setPushTarget(PushTargetEnum.JPUSH);
        PushReceiverHandleManager.getInstance().onRegistration(mContext,aliasInfo);
    }

    @Override
    public void setAlias(String alias) {
        super.setAlias(alias);
        JPushInterface.setAlias(mContext, 0, alias);
    }

    @Override
    public void loginOut() {
        JPushInterface.deleteAlias(mContext, 0);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(mContext.getString(R.string.tip_loginOut));
        aliasInfo.setContent(mAlias);
        aliasInfo.setPushTarget(PushTargetEnum.JPUSH);
        PushReceiverHandleManager.getInstance().onLoginOut(mContext,aliasInfo);
    }

}
