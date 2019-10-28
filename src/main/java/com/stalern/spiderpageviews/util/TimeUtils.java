package com.stalern.spiderpageviews.util;

import java.util.Map;

/**
 * @author stalern
 * @date 2019/10/28--21:34
 */
public class TimeUtils {
    private TimeUtils(){}
    public static void time2Night(Map<String,String> map) {
        for (int i = 0; i < 10; i++) {
            System.out.println("0" + i + ": " + map.get("0" + i));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("1" + i + ": " + map.get("1" + i));
        }
        for (int i = 0; i < 4; i++) {
            System.out.println("2" + i + ": " + map.get("2" + i));
        }

    }
}
