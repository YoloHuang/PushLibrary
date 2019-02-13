package com.kidosc.pushlibrary.model;

import java.io.Serializable;

/**
 * 接收到的推送的消息
 * Created by yolo.huang on 2018/5/28.
 */

public class ReceiverInfo implements Serializable {
    /**
     * 推送平台
     */
    private PushTargetEnum pushTarget = PushTargetEnum.JPUSH;
    /**
     * 标题
     */
    private String title = "";
    /**
     * 内容
     */
    private String content = "";

    /**
     * 额外数据
     */
    private String extra = "";

    /**
     * 额外数据
     */
    private String type = "";

    public ReceiverInfo() {
    }

    public ReceiverInfo(PushTargetEnum pushTarget, String title, String content, String extra,String type) {
        this.pushTarget = pushTarget;
        this.title = title;
        this.content = content;
        this.extra = extra;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PushTargetEnum getPushTarget() {
        return pushTarget;
    }

    public void setPushTarget(PushTargetEnum pushTarget) {
        this.pushTarget = pushTarget;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }


    @Override
    public String toString() {
        return "ReceiverInfo{" +
                "pushTarget=" + pushTarget +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", extra='" + extra + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
