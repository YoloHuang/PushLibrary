package com.kidosc.pushlibrary.rom.huawei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;
import com.kidosc.pushlibrary.util.PushLogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 处理华为推送点击事件
 * 需要与后端协商好数据结构
 * scheme ：ipush://router/huawei
 * 作者：luoming on 2018/10/13.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class HuaweiLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            String action = getIntent().getData().getQueryParameter("action");
            PushLogUtil.i("action="+action);
            ReceiverInfo info = new ReceiverInfo();
            info.setExtra(action);
            info.setPushTarget(PushTargetEnum.HUAWEI);
            PushReceiverHandleManager.getInstance().onNotificationOpened(this,info);
        }catch (Exception e ){

        }
        finish();
    }

}
