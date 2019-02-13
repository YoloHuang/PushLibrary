package com.kidosc.pushlibrary.rom.jiguang;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义的极光推送接收
 * Created by luoming on 2018/5/28.
 */

public class JPushBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }

        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            return;
        }

        if ("cn.jpush.android.intent.REGISTRATION".equals(action)) {
            //用户注册SDK的intent
            ReceiverInfo info = new ReceiverInfo();
            info.setPushTarget(PushTargetEnum.JPUSH);
            info.setTitle(context.getString(R.string.tip_loginIn));
            PushReceiverHandleManager.getInstance().onRegistration(context, info);
        } else if ("cn.jpush.android.intent.MESSAGE_RECEIVED".equals(action)) {
            //用户接收SDK消息的intent
            PushReceiverHandleManager.getInstance().onMessageReceived(context, convert2MessageReceiverInfo(intent));
        } else if ("cn.jpush.android.intent.NOTIFICATION_RECEIVED".equals(action)) {
            //用户接收SDK通知栏信息的intent
            PushReceiverHandleManager.getInstance().onNotificationReceived(context, convert2NotificationReceiverInfo(intent));
        } else if ("cn.jpush.android.intent.NOTIFICATION_OPENED".equals(action)) {
            //用户打开自定义通知栏的intent
            PushReceiverHandleManager.getInstance().onNotificationOpened(context, convert2NotificationReceiverInfo(intent));
        }
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param intent
     * @return
     */
    private ReceiverInfo convert2NotificationReceiverInfo(Intent intent) {
        ReceiverInfo info = new ReceiverInfo();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        info.setTitle(title);
        info.setContent(message);
        info.setExtra(extras);
        info.setPushTarget(PushTargetEnum.JPUSH);
        return info;
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param intent
     * @return
     */
    private ReceiverInfo convert2MessageReceiverInfo(Intent intent) {
        ReceiverInfo info = new ReceiverInfo();
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        info.setTitle(title);
        info.setContent(message);
        info.setExtra(extras);
        info.setPushTarget(PushTargetEnum.JPUSH);
        return info;
    }
}
