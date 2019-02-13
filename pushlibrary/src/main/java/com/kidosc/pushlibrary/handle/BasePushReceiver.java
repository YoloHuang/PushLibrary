package com.kidosc.pushlibrary.handle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kidosc.pushlibrary.model.ReceiverInfo;

/**
 * @author yolo.huang
 * @date 2018/12/27
 */

public abstract class BasePushReceiver extends BroadcastReceiver implements IPushReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ReceiverInfo info = (ReceiverInfo) intent.getSerializableExtra(PushReceiverHandleManager.INTENT_RECEIVER_INFO);
        if (PushAction.RECEIVE_TOKEN_SETED.equals(action)) {
            onTokenSet(context, info);
        } else if (PushAction.RECEIVE_INIT_RESULT.equals(action)) {
            onInitResult(context, info);
        } else if (PushAction.RECEIVE_NOTIFICATION.equals(action)) {
            onReceiveNotification(context, info);
        } else if (PushAction.RECEIVE_NOTIFICATION_CLICK.equals(action)) {
            onReceiveNotificationClick(context, info);
        } else if (PushAction.RECEIVE_MESSAGE.equals(action)) {
            onReceiveMessage(context, info);
        }else if(PushAction.RECEIVE_LOGIN_OUT.equals(action)){
            onLoginOut(context,info);
        }else if (PushAction.RECEIVE_SET_ALIAS.equals(action)){
            onSetAlias(context,info);
        }
    }
}
