package com.template.util;

import com.template.common.BizException;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Mongodb Bean工具类
 *
 * @author Doug Liu
 * @since 2023-01-13
 */
public class MongoBeanUtil {

    /**
     * Mongodb中的单个文档实体映射，实体属性复制
     *
     * @param source 源实体
     * @param clazz  目标实体类
     * @param <T>    源实体泛型
     * @param <K>    目标实体泛型
     */
    public static <T, K> K copy(T source, Class<K> clazz) {
        if (source == null) {
            return null;
        }
        K target;
        try {
            target = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            Field[] fieldOne = source.getClass().getDeclaredFields();
            Class<? super T> superclassOne = (Class<? super T>) source.getClass().getSuperclass();
            if (superclassOne != null) {
                Field[] fields = superclassOne.getDeclaredFields();
                fieldOne = SystemArrayUtil.getUnion(fieldOne, fields);
            }
            Field[] fieldTwo = clazz.getDeclaredFields();
            Class<? super K> superclassTwo = clazz.getSuperclass();
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
            throw new BizException(e.getMessage());
        }
        return target;
    }

    /**
     * 复制Mongodb文档列表对象
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
        for (T source : sourceList) {
            try {
                K targetObject = clazz.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                Field[] fieldOne = source.getClass().getDeclaredFields();
                Class<? super T> superclassOne = (Class<? super T>) source.getClass().getSuperclass();
                if (superclassOne != null) {
                    Field[] fields = superclassOne.getDeclaredFields();
                    fieldOne = SystemArrayUtil.getUnion(fieldOne, fields);
                }
                Field[] fieldTwo = clazz.getDeclaredFields();
                Class<? super K> superclassTwo = clazz.getSuperclass();
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
                throw new BizException(e.getMessage());
            }
        }
        return targetList;
    }

}
