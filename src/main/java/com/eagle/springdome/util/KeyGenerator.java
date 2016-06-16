package com.eagle.springdome.util;

import java.util.UUID;

/**
 * Created by Wang Yong on 16-6-16.
 */
public class KeyGenerator{

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();
        key = key.replaceAll("-","");
        return key;
    }
}
