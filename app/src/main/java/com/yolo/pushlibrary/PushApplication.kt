package com.yolo.pushlibrary

import android.app.Application
import com.kidosc.pushlibrary.PushTargetManager

/**
 * @author yolo.huang
 * @date 2019/1/25
 */
class PushApplication: Application(){


    override fun onCreate() {
        super.onCreate()

        PushTargetManager.getInstance().setDebug(true)
        PushTargetManager.getInstance().init(this)
    }
}