package com.kidosc.pushlibrary.rom.vivo;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.kidosc.pushlibrary.PushTargetManager;
import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.rom.BasePushTargetInit;
import com.kidosc.pushlibrary.util.PushLogUtil;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;

/**
 * @author yolo.huang
 * @date 2019/1/2
 */

public class VivoInit extends BasePushTargetInit {
    /**
     * 推送初始化
     *
     * @param application
     */
    public VivoInit(Application application) {
        super(application);
        init();
    }

    private void init() {
        PushClient.getInstance(mApplication).initialize();
    }

    @Override
    public void setAlias(final String alias) {
        super.setAlias(alias);
        PushClient.getInstance(mApplication).bindAlias(alias, new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                PushLogUtil.i( "vivo setAlias state =" + i);
                if (i == 0 || i == 1) {
                    ReceiverInfo aliasInfo = new ReceiverInfo();
                    aliasInfo.setTitle(mContext.getString(R.string.tip_setalias));
                    aliasInfo.setContent(mAlias);
                    aliasInfo.setPushTarget(PushTargetEnum.VIVO);
                    PushReceiverHandleManager.getInstance().onAliasSet(mActivity, aliasInfo);
                } else {
                    PushTargetManager.getInstance().setEnableVivoPush(false);
                    PushTargetManager.getInstance().init(mApplication);
                    PushTargetManager.getInstance().loginIn(mActivity);
                    PushTargetManager.getInstance().setAlias(mAlias);
                }
            }
        });
    }


    @Override
    public void loginIn(Activity activity) {
        super.loginIn(activity);
        PushClient.getInstance(mApplication).turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                PushLogUtil.i("vivo loginIn state =" + i);
                if (i == 0 || i == 1) {
                    Log.i("loginIn", "vivo loginIn success");
                    ReceiverInfo aliasInfo = new ReceiverInfo();
                    aliasInfo.setTitle(mContext.getString(R.string.tip_loginIn));
                    aliasInfo.setContent("vivo loginIn success");
                    aliasInfo.setPushTarget(PushTargetEnum.VIVO);
                    PushReceiverHandleManager.getInstance().onRegistration(mActivity, aliasInfo);
                } else {
                    PushTargetManager.getInstance().setEnableVivoPush(false);
                    PushTargetManager.getInstance().init(mApplication);
                    PushTargetManager.getInstance().loginIn(mActivity);
                }
            }
        });

    }

    @Override
    public void loginOut() {
        PushClient.getInstance(mApplication).turnOffPush(null);
        PushClient.getInstance(mApplication).unBindAlias(mAlias, null);
        ReceiverInfo aliasInfo = new ReceiverInfo();
        aliasInfo.setTitle(mContext.getString(R.string.tip_loginOut));
        aliasInfo.setContent(mAlias);
        aliasInfo.setPushTarget(PushTargetEnum.VIVO);
        PushReceiverHandleManager.getInstance().onLoginOut(mContext,aliasInfo);
    }
}
