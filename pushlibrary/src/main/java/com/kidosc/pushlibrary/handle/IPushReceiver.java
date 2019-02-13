package com.kidosc.pushlibrary.handle;

import android.content.Context;

import com.kidosc.pushlibrary.model.ReceiverInfo;

/**
 * @author yolo.huang
 * @date 2018/12/27
 */

public interface IPushReceiver {


    /**
     * 收到通知后会调用此接口
     * @param context
     * @param info
     */
    void onReceiveNotification(Context context, ReceiverInfo info);


    /**
     * 点击通知后会调用此接口
     * @param context
     * @param info
     */
    void onReceiveNotificationClick(Context context, ReceiverInfo info);


    /**
     * 收到消息后会调用此接口
     * @param context
     * @param info
     */
    void onReceiveMessage(Context context, ReceiverInfo info);


    /**
     * 华为推送获取到token后会调用此接口传递过来
     * @param context
     * @param info
     */
    void onTokenSet(Context context, ReceiverInfo info);


    /**
     * 初始化成功后会调用此接口
     * @param context
     * @param info
     */
    void onInitResult(Context context, ReceiverInfo info);

    /**
     * 设置别名成功后会调用此接口
     * @param context
     * @param info
     */
    void onSetAlias(Context context,ReceiverInfo info);

    /**
     * 登出后会调用此接口
     * @param context
     * @param info
     */
    void onLoginOut(Context context,ReceiverInfo info);
}
