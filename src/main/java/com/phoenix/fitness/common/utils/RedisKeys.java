

package com.phoenix.fitness.common.utils;

/**
 * Redis所有Keys
 *
 * @author Mark sm516116978@outlook.com
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
