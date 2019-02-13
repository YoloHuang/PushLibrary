package com.kidosc.pushlibrary.model;

/**
 * @author yolo.huang
 *         推送平台
 */

public enum PushTargetEnum {
    /**
     * 极光
     */
    JPUSH("JPUSH"),

    /**
     * XIAOMI
     */
    XIAOMI("XIAOMI"),

    /**
     * HUAWEI
     */
    HUAWEI("HUAWEI"),

    /**
     * OPPO
     */
    OPPO("OPPO"),

    /**
     * VIVO
     */
    VIVO("VIVO");

    public String brand;

    PushTargetEnum(String brand) {
        this.brand = brand;
    }

}
