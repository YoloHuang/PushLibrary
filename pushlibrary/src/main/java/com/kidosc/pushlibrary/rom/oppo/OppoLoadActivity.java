package com.kidosc.pushlibrary.rom.oppo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kidosc.pushlibrary.handle.PushReceiverHandleManager;
import com.kidosc.pushlibrary.model.PushTargetEnum;
import com.kidosc.pushlibrary.model.ReceiverInfo;

import java.util.Set;

/**
 * 处理OPPO的通知栏点击事件
 * action：ipush.router.oppo
 * 作者：luoming on 2018/10/12.
 * 邮箱：luomingbear@163.com
 * 版本：v1.0
 */
public class OppoLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取出bundle里面的参数数据改为json格式
        Bundle bundle = getIntent().getExtras();
        Set<String> set = bundle.keySet();
        StringBuffer sb = new StringBuffer();
        if (set != null) {
            sb.append("{");
            for (String key : set) {
                String vaues = bundle.getString(key);
                sb.append("\"");
                sb.append(key);
                sb.append("\"");
                sb.append(":");
                sb.append("\"");
                sb.append(vaues);
                sb.append("\"");
                sb.append(",");
            }
            sb.append("}");
        }
        //发送到处理中心
        ReceiverInfo openInfo = new ReceiverInfo();
        openInfo.setPushTarget(PushTargetEnum.OPPO);
        openInfo.setExtra(sb.replace(sb.length() - 2, sb.length(), "}").toString());
        PushReceiverHandleManager.getInstance().onNotificationOpened(this, openInfo);
        finish();
    }
}
