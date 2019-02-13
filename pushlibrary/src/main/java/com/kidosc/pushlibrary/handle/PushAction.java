package com.kidosc.pushlibrary.handle;

/**
 * @author yolo.huang
 * @date 2018/12/27
 */

public interface PushAction {

    String RECEIVE_NOTIFICATION = "com.kidosc.pushlibrary.ACTION_RECEIVE_NOTIFICATION";
    String RECEIVE_NOTIFICATION_CLICK = "com.kidosc.pushlibrary.ACTION_RECEIVE_NOTIFICATION_CLICK";
    String RECEIVE_MESSAGE = "com.kidosc.pushlibrary.ACTION_RECEIVE_MESSAGE";
    String RECEIVE_TOKEN_SETED = "com.kidosc.pushlibrary.ACTION_RECEIVE_TOKEN_SET";
    String RECEIVE_INIT_RESULT = "com.kidosc.pushlibrary.ACTION_RECEIVE_INIT_RESULT";
    String RECEIVE_SET_ALIAS = "com.kidosc.pushlibrary.ACTION_RECEIVE_SET_ALIAS";
    String RECEIVE_LOGIN_OUT = "com.kidosc.pushlibrary.ACTION_RECEIVE_LOGIN_OUT";
}
