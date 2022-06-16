package com.template.app.mongoEntity.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数组处理工具类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
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

    /**
     * 求交集
     *
     * @param m
     * @param n
     * @return
     */
    public static String[] getJ(String[] m, String[] n) {
        List<String> rs = new ArrayList<>();
        
        // 将较长的数组转换为set
        Set<String> set = new HashSet<>(Arrays.asList(m.length > n.length ? m : n));
        
        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m) {
            if (set.contains(i)) {
                rs.add(i);
            }
        }
        String[] arr = {};
        return rs.toArray(arr);
    }
    
    /**
     * 求差集
     * @param m
     * @param n
     * @return
     */
    public static String[] getDifference(String[] m, String[] n) {
        // 将较长的数组转换为set
        Set<String> set = new HashSet<>(Arrays.asList(m.length > n.length ? m : n));
        // 遍历较短的数组，实现最少循环
        for (String i : m.length > n.length ? n : m) {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }
        
        String[] arr = {};
        return set.toArray(arr);
    }
    
    
    /**
     * 求删除的集合
     * @param m
     * @param n
     * @return
     */
    public static String[] getDelete(String[] m, String[] n) {
        Set<String> set = new HashSet<>(Arrays.asList(m));
        Set<String> result = new HashSet<>();
        for (String i : n) {
            if (!set.contains(i)) {
                result.add(i);
            }
        }
        String[] arr = {};
        return result.toArray(arr);
    }
    
    public static String[] getAdd(String[] m, String[] n) {
        Set<String> set = new HashSet<>(Arrays.asList(n));
        Set<String> result = new HashSet<>();
        for (String i : m) {
            if (!set.contains(i)) {
                result.add(i);
            }
        }
        String[] arr = {};
        return result.toArray(arr);
    }

}