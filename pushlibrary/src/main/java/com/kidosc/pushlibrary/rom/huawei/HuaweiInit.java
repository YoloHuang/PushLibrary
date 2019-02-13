package com.kidosc.pushlibrary.rom.huawei;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.DeleteTokenHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.kidosc.pushlibrary.PushTargetManager;
import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.cache.PushTokenCache;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.rom.BasePushTargetInit;
import com.kidosc.pushlibrary.util.PushLogUtil;

/**
 * @author yolo.huang
 *         华为推送初始化
 */

public class HuaweiInit extends BasePushTargetInit {


    public HuaweiInit(Application application) {
        super(application);
        init();
        PushLogUtil.d( "HuaweiInit");

    }

    private void init() {
        boolean init = HMSAgent.init(mApplication);
        if (!init) {
            PushTargetManager.getInstance().setEnableHWPush(false);
            PushTargetManager.getInstance().init(mApplication);
        }
        PushLogUtil.i("HMSAgent.init success? = " + init);
    }

    @Override
    public void setAlias(String alias) {
        super.setAlias(alias);
        //华为没有设置alias的功能
    }

    @Override
    public void loginIn(Activity activity) {
        super.loginIn(activity);
        HMSAgent.connect(activity, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                PushLogUtil.i( "huawei-hmsagents connect onConnect=" + rst);
                if (rst == HMSAgent.AgentResultCode.HMSAGENT_SUCCESS) {
                    //连接成功就获取token和设置打开推送等，获取成功后，在HuaweiPushBroadcastReceiver的回调中get到token，然后传递给服务器
                    HMSAgent.Push.getToken(new GetTokenHandler() {
                        @Override
                        public void onResult(int rst) {
                            PushLogUtil.i( "huawei-hmsagents getToken onResult=" + rst);
                            if (rst == HMSAgent.AgentResultCode.HMSAGENT_SUCCESS) {
                                ReceiverInfo info = new ReceiverInfo();
                                info.setPushTarget(PushTargetEnum.HUAWEI);
                                info.setTitle(mContext.getString(R.string.tip_loginIn));
                                PushReceiverHandleManager.getInstance().onRegistration(mActivity, info);
                            } else {
                                PushTargetManager.getInstance().setEnableHWPush(false);
                                PushTargetManager.getInstance().init(mApplication);
                                PushTargetManager.getInstance().loginIn(mActivity);
                                PushTargetManager.getInstance().setAlias(mAlias);
                            }
                        }
                    });
                } else {
                    PushTargetManager.getInstance().setEnableHWPush(false);
                    PushTargetManager.getInstance().init(mApplication);
                    PushTargetManager.getInstance().loginIn(mActivity);
                }
            }
        });

    }

    @Override
    public void loginOut() {
        //传递空token给服务器
        final String token = PushTokenCache.getToken(mContext);
        if (!TextUtils.isEmpty(token)) {
            HMSAgent.Push.deleteToken(token, new DeleteTokenHandler() {
                @Override
                public void onResult(int rst) {
                    PushLogUtil.d("deleteToken onResult= " + rst);
                }
            });
        }
        ReceiverInfo info = new ReceiverInfo();
        info.setContent("");
        info.setPushTarget(PushTargetEnum.HUAWEI);
        PushReceiverHandleManager.getInstance().setToken(mContext, info);
    }
}
