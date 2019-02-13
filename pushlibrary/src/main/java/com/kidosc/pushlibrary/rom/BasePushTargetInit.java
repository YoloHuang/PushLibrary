package com.kidosc.pushlibrary.rom;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


/**
 * 初始化推送平台的基类
 * Created by luoming on 2018/5/28.
 */

public abstract class BasePushTargetInit {
    protected static int MAX_RETRY_COUNT = 5;
    protected Application mApplication;
    protected Context mContext;
    protected String mAlias;
    protected Activity mActivity;

    /**
     * 推送初始化
     *
     * @param application
     */
    public BasePushTargetInit(Application application) {
        this.mApplication = application;
        this.mContext = mApplication.getApplicationContext();
    }

    /**
     * 设置别名
     *
     * @param alias
     */
    public void setAlias(String alias) {
        this.mAlias = alias;
    }

    public void loginOut() {
    }

    public void loginIn(Activity activity) {
        this.mActivity = activity;
    }
}
