package com.kidosc.pushlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.coloros.mcssdk.PushManager;
import com.kidosc.pushlibrary.rom.BasePushTargetInit;
import com.kidosc.pushlibrary.rom.huawei.HuaweiInit;
import com.kidosc.pushlibrary.rom.jiguang.JPushInit;
import com.kidosc.pushlibrary.rom.oppo.OppoInit;
import com.kidosc.pushlibrary.rom.vivo.VivoInit;
import com.kidosc.pushlibrary.rom.xiaomi.XiaomiInit;
import com.kidosc.pushlibrary.util.PushLogUtil;
import com.kidosc.pushlibrary.util.RomUtils;
import com.vivo.push.PushClient;

/**
 * @author yolo.huang
 * @date 2018/12/21
 */

public class PushTargetManager {


    private static PushTargetManager instance;


    /**
     * 当前的推送平台，默认为极光 JPUSH
     */
    private BasePushTargetInit mPushTarget;

    private static final int HUAWEI = 1, OPPO = 2, XIAOMI = 3, VIVO = 4, JPUSH = 5;

    private boolean enableVivoPush = true;
    private boolean enableOppoPush = true;
    private boolean enableHWPush = true;
    private boolean enableXIAOMIPush = true;


    public static PushTargetManager getInstance() {
        if (instance == null) {
            synchronized (PushTargetManager.class) {
                if (instance == null) {
                    instance = new PushTargetManager();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Application context) {
        /**
         * 根据设备厂商和使用者设置来选择推送平台,小米的使用小米推送，华为使用华为推送...其他的使用极光推送
         */
        switch (getPhoneType(context.getApplicationContext())) {
            case XIAOMI:
                mPushTarget = new XiaomiInit(context);
                break;
            case HUAWEI:
                mPushTarget = new HuaweiInit(context);
                break;
            case OPPO:
                mPushTarget = new OppoInit(context);
                break;
            case VIVO:
                mPushTarget = new VivoInit(context);
                break;
            case JPUSH:
                mPushTarget = new JPushInit(context);
                break;
            default:
                mPushTarget = new JPushInit(context);
                break;
        }

    }

    /**
     * 初始化
     *
     * @param application 项目application
     * @param enableHWPush 是否允许华为推送
     * @param enableOppoPush 是否允许OPPO推送
     * @param enableVivoPush 是否允许VIVO推送
     * @param enableXIAOMIPush 是否允许小米推送
     */
    public void init(Application application,boolean enableHWPush,boolean enableOppoPush,boolean enableVivoPush ,boolean enableXIAOMIPush){
        this.enableVivoPush =enableVivoPush;
        this.enableXIAOMIPush = enableXIAOMIPush;
        this.enableOppoPush = enableOppoPush;
        this.enableHWPush = enableHWPush;
        init(application);
    }

    public void setDebug(boolean debug){
        PushLogUtil.setDebug(debug);
    }

    /**
     * 登录，华为登录需要在activity中
     * @param activity
     */
    public void loginIn(Activity activity) {
        mPushTarget.loginIn(activity);
    }


    /**
     * 登录后设置alias,华为推送是获取token
     */
    public void setAlias(String alias) {
        mPushTarget.setAlias(alias);
    }

    /**
     * 登出，登出后，设置alias为空，或者传递token给服务器为空
     */
    public void loginOut() {
        mPushTarget.loginOut();
    }


    public int getPhoneType(Context context) {
        int phoneType;
        if (RomUtils.isOPPORom() && PushManager.isSupportPush(context) && enableOppoPush) {
            phoneType = OPPO;
        } else if (RomUtils.isHuaweiRom() && enableHWPush) {
            phoneType = HUAWEI;
        } else if (RomUtils.isMiuiRom() && enableXIAOMIPush) {
            phoneType = XIAOMI;
        } else if (RomUtils.isVivoRom() && PushClient.getInstance(context).isSupport() && enableVivoPush) {
            phoneType = VIVO;
        } else {
            phoneType = JPUSH;
        }
        return phoneType;
    }


    public void setEnableVivoPush(boolean enableVivoPush) {
        this.enableVivoPush = enableVivoPush;
    }


    public void setEnableHWPush(boolean enableHWPush) {
        this.enableHWPush = enableHWPush;
    }

    public void setEnableOppoPush(boolean enableOppoPush) {
        this.enableOppoPush = enableOppoPush;
    }

    public void setEnableXIAOMIPush(boolean enableXIAOMIPush) {
        this.enableXIAOMIPush = enableXIAOMIPush;
    }
}
