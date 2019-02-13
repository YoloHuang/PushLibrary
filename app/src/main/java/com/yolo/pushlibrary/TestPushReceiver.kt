package com.yolo.pushlibrary

import android.content.Context
import android.content.Intent
import com.kidosc.pushlibrary.handle.BasePushReceiver
import com.kidosc.pushlibrary.model.ReceiverInfo

/**
 * @author yolo.huang
 * @date 2019/1/25
 */
class TestPushReceiver : BasePushReceiver() {

    companion object {
        val ACTION_BROADCAST:String = "com.yolo.pushlibrary.ACTION_PUSH"
        val PUSH_LOG = "push_log"
    }




    override fun onReceiveNotification(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onReceiveNotificationClick(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onReceiveMessage(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onTokenSet(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onInitResult(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onLoginOut(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }

    override fun onSetAlias(context: Context, info: ReceiverInfo) {
        sendBroadCast(context,info)
    }



    fun sendBroadCast(context: Context,info: ReceiverInfo){
        val intent= Intent(ACTION_BROADCAST)
        intent.putExtra(PUSH_LOG,info)
        intent.`package` = context.packageName
        context.sendBroadcast(intent)

    }



}