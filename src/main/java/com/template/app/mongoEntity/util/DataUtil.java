package com.template.app.mongoEntity.util;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;
import com.template.util.BeanUtil;
import org.bson.types.ObjectId;

/**
 * 数据转化工具类
 *
 * @author Doug Liu
 * @since 2022-06-14
 *
 */
public class DataUtil {

    /**
     * 复制列表
     * @param sourceList 源数据对象列表
     * @param clazzOne   源对象类
     * @param clazzTwo   目标对象类
     * @param <T>        源对象
     * @param <K>        目标对象
     * @return List<K>
     */
    public static <T, K> List<K> copyList(List<T> sourceList, Class<T> clazzOne, Class<K> clazzTwo) {
        List<K> targetList = Lists.newArrayList();
        if (sourceList == null || sourceList.isEmpty()) {
            return targetList;
        }
        for (T source : sourceList) {
            try {
                K targetObject = BeanUtil.copy(source, clazzTwo);
                Field[] fieldOne = clazzOne.getDeclaredFields();
                Class<? super T> superclassOne = clazzOne.getSuperclass();
                if (superclassOne != null) {
                    Field[] fields = superclassOne.getDeclaredFields();
                    fieldOne = SystemArrayUtil.getUnion(fieldOne, fields);
                }
                Field[] fieldTwo = clazzTwo.getDeclaredFields();
                Class<? super K> superclassTwo = clazzTwo.getSuperclass();
                if (superclassTwo != null) {
                    Field[] fields = superclassTwo.getDeclaredFields();
                    fieldTwo = SystemArrayUtil.getUnion(fieldTwo, fields);
                }
                for (Field field1 : fieldOne) {
                    for (Field field2 : fieldTwo) {
                        field1.setAccessible(true);
                        field2.setAccessible(true);
                        if (field1.getName().equals(field2.getName()) && !"serialVersionUID".equals(field1.getName()) && field1.get(source) != null) {
                            if (field1.getType().equals(ObjectId.class) && field1.get(source) != null) {
                                field2.set(targetObject, ((ObjectId) field1.get(source)).toString());
                            } else if (field2.getType().equals(ObjectId.class) && field1.get(source) != null) {
                                field2.set(targetObject, new ObjectId((String) field1.get(source)));
                            }
                        }
                    }
                }
                targetList.add(targetObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return targetList;
    }

    /**
     * 复制文档
     * @param source   源数据对象
     * @param clazzOne 对象类
     * @param clazzTwo 目标对象类
     * @param <T>      源对象
     * @param <K>      目标对象
     * @return K
     */
    public static <T, K> K copyDocument(T source, Class<T> clazzOne, Class<K> clazzTwo) {
        if (source == null) {
            return null;
        }
        K target = BeanUtil.copy(source, clazzTwo);
        try {
            Field[] fieldOne = clazzOne.getDeclaredFields();
            Class<? super T> superclassOne = clazzOne.getSuperclass();
            if (superclassOne != null) {
                Field[] fields = superclassOne.getDeclaredFields();
                fieldOne = SystemArrayUtil.getUnion(fieldOne, fields);
            }
            Field[] fieldTwo = clazzTwo.getDeclaredFields();
            Class<? super K> superclassTwo = clazzTwo.getSuperclass();
            if (superclassTwo != null) {
                Field[] fields = superclassTwo.getDeclaredFields();
                fieldTwo = SystemArrayUtil.getUnion(fieldTwo, fields);
            }
            for (Field field1 : fieldOne) {
                for (Field field2 : fieldTwo) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (field1.getName().equals(field2.getName()) && !"serialVersionUID".equals(field1.getName()) && field1.get(source) != null) {
                        if (field1.getType().equals(ObjectId.class)) {
                            field2.set(target, ((ObjectId) field1.get(source)).toString());
                        } else if (field2.getType().equals(ObjectId.class)) {
                            field2.set(target, new ObjectId((String) field1.get(source)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

}
