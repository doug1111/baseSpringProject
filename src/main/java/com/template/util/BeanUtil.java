package com.template.util;


import com.google.common.collect.Maps;
import com.template.common.BizException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * 通用Bean工具类
 * 1. Java bean和map之间转换
 * 2. Java bean列表和map列表之间的转换
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
public class BeanUtil {

    /**
     * 单个实体映射，实体属性复制
     *
     * @param source 源实体
     * @param clazz  目标类型
     * @param <T>    源实体泛型
     */
    public static <T, K> K copy(T source, Class<K> clazz) {
        if (source == null) {
            return null;
        }
        K target;
        try {
            target = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
        return target;
    }

    /**
     * 复制列表对象
     *
     * @param sourceList 源实体列表
     * @param clazz      目标实体类型
     * @return List<K>
     * @param <T>        源实体泛型
     * @param <K>        目标实体泛型
     */
    public static <T, K> List<K> copyList(List<T> sourceList, Class<K> clazz) {
        if (sourceList == null) {
            return null;
        }
        List<K> targetList = new ArrayList<>();
        if (sourceList.isEmpty()) {
            return targetList;
        }
        for (T source : sourceList) {
            targetList.add(BeanUtil.copy(source, clazz));
        }
        return targetList;
    }

    /**
     * 将对象装换为map
     *
     * @param bean 源实体
     * @return Map<String, Object>
     * @param <T>  实体泛型
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map       map对象
     * @param beanClass 目标实体类型
     * @return T 实体泛型
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }
        try {
            T obj = beanClass.getDeclaredConstructor().newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
            return obj;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        }
    }

    /**
     * 将对象列表装换为map列表
     *
     * @param beans bean对象列表
     * @return List<Map < String, Object>>
     */
    public static <T> List<Map<String, Object>> beansToMaps(List<T> beans) {
        if (CollectionUtils.isEmpty(beans)) {
            return Collections.emptyList();
        }
        return beans.stream().map(BeanUtil::beanToMap).collect(toList());
    }

    /**
     * 将map装换为javabean对象
     *
     * @param mapList   map数据列表
     * @param beanClass bean类
     * @return List<T> 泛型列表
     */
    public static <T> List<T> mapsToBeans(List<Map<String, Object>> mapList, Class<T> beanClass) {
        if (CollectionUtils.isEmpty(mapList)) {
            return Collections.emptyList();
        }
        return mapList.stream().map(e -> mapToBean(e, beanClass)).collect(toList());
    }

}