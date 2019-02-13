package com.kidosc.pushlibrary.rom.vivo;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.util.PushLogUtil;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

/**
 * @author yolo.huang
 * @date 2019/1/2
 */

public class VivoMessageReceiverImpl extends OpenClientPushMessageReceiver {


    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        PushLogUtil.d("vivo : upsNotificationMessage=" + upsNotificationMessage.toString());
        if (upsNotificationMessage != null) {
            ReceiverInfo info = new ReceiverInfo();
            info.setContent(upsNotificationMessage.getContent());
            info.setPushTarget(PushTargetEnum.VIVO);
            info.setTitle(upsNotificationMessage.getTitle());
            info.setExtra(new Gson().toJson(upsNotificationMessage.getParams()));
            PushReceiverHandleManager.getInstance().onNotificationOpened(context, info);
        }

    }

    @Override
    public void onReceiveRegId(Context context, String s) {

    }
}
