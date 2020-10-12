package com.henley.android.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 设备系统类型
 *
 * @author Henley
 * @since 2020/5/26 15:32
 */
@IntDef({
        SystemType.SYSTEM_UNKNOWN,
        SystemType.SYSTEM_EMUI,
        SystemType.SYSTEM_MIUI,
        SystemType.SYSTEM_FLYME,
        SystemType.SYSTEM_OPPO,
        SystemType.SYSTEM_VIVO,
        SystemType.SYSTEM_SMARTISAN,
        SystemType.SYSTEM_GIONEE,
        SystemType.SYSTEM_LENOVO,
        SystemType.SYSTEM_360
})
@Retention(RetentionPolicy.SOURCE)
public @interface SystemType {

    /**
     * 其它
     */
    int SYSTEM_UNKNOWN = 0;
    /**
     * 华为
     */
    int SYSTEM_EMUI = 1;
    /**
     * 小米
     */
    int SYSTEM_MIUI = 2;
    /**
     * 魅族
     */
    int SYSTEM_FLYME = 3;
    /**
     * OPPO
     */
    int SYSTEM_OPPO = 4;
    /**
     * VIVO
     */
    int SYSTEM_VIVO = 5;
    /**
     * 锤子
     */
    int SYSTEM_SMARTISAN = 6;
    /**
     * 金立
     */
    int SYSTEM_GIONEE = 7;
    /**
     * 联想
     */
    int SYSTEM_LENOVO = 8;
    /**
     * 360
     */
    int SYSTEM_360 = 9;

}
