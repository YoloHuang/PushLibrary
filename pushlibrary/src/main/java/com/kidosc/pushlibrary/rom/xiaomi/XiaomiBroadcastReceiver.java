package com.kidosc.pushlibrary.rom.xiaomi;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.kidosc.pushlibrary.R;
import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.util.PushLogUtil;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

/**
 * 自定义的小米推送接收
 * Created by luoming on 2018/5/28.
 */

public class XiaomiBroadcastReceiver extends PushMessageReceiver {
    @Override
    public void onReceivePassThroughMessage(Context var1, MiPushMessage var2) {
        PushReceiverHandleManager.getInstance().onMessageReceived(var1, convert2ReceiverInfo(var2));
    }

    @Override
    public void onNotificationMessageClicked(Context var1, MiPushMessage var2) {
        PushReceiverHandleManager.getInstance().onNotificationOpened(var1, convert2ReceiverInfo(var2));
    }

    @Override
    public void onNotificationMessageArrived(Context var1, MiPushMessage var2) {
        PushReceiverHandleManager.getInstance().onNotificationReceived(var1, convert2ReceiverInfo(var2));
    }

    @Override
    public void onReceiveRegisterResult(Context var1, MiPushCommandMessage var2) {

    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        super.onCommandResult(context, message);
        PushLogUtil.i( "XiaomiBroadcastReceiver :onCommandResult = var2=" + message.toString());
        String command = message.getCommand();

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                ReceiverInfo info = convert2ReceiverInfo(message);
                info.setTitle(context.getString(R.string.tip_loginIn));
                info.setContent(message.getCommand());
                PushReceiverHandleManager.getInstance().onRegistration(context, info);
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                ReceiverInfo info = convert2ReceiverInfo(message);
                info.setTitle(context.getString(R.string.tip_setalias));
                info.setContent(message.getCommandArguments().get(0));
                PushReceiverHandleManager.getInstance().onAliasSet(context, info);
            } else {

            }
        }

    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param miPushMessage
     * @return
     */
    private ReceiverInfo convert2ReceiverInfo(MiPushMessage miPushMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(miPushMessage.getContent());
        info.setPushTarget(PushTargetEnum.XIAOMI);
        info.setTitle(miPushMessage.getTitle());
        if (miPushMessage.getExtra() != null) {
            info.setExtra(new Gson().toJson(miPushMessage.getExtra()));
        }
        return info;
    }

    /**
     * 将intent的数据转化为ReceiverInfo用于处理
     *
     * @param miPushCommandMessage
     * @return
     */
    private ReceiverInfo convert2ReceiverInfo(MiPushCommandMessage miPushCommandMessage) {
        ReceiverInfo info = new ReceiverInfo();
        info.setContent(miPushCommandMessage.getCommand());
        info.setPushTarget(PushTargetEnum.XIAOMI);
        return info;
    }
}
