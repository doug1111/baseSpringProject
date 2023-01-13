package com.template.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统数组工具类
 *
 * @author Doug Liu
 * @since 2023-01-13
 */
public class SystemArrayUtil {

    /**
     * 求并集
     *
     * @param m
     * @param n
     * @return
     */
    public static String[] getUnion(String[] m, String[] n) {
        // 将数组转换为set集合
        Set<String> set1 = new HashSet<>(Arrays.asList(m));
        Set<String> set2 = new HashSet<>(Arrays.asList(n));

        // 合并两个集合
        set1.addAll(set2);
        String[] arr = {};
        return set1.toArray(arr);
    }

    /**
     * 求并集
     *
     * @param m
     * @param n
     * @return
     */
    public static Field[] getUnion(Field[] m, Field[] n) {
        // 将数组转换为set集合
        Set<Field> set1 = new HashSet<>(Arrays.asList(m));
        Set<Field> set2 = new HashSet<>(Arrays.asList(n));

        // 合并两个集合
        set1.addAll(set2);
        Field[] arr = {};
        return set1.toArray(arr);
    }

}
