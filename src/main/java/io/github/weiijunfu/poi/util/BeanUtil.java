package io.github.weiijunfu.poi.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @title  : Bean 常用操作工具类
 * @author : ijunfu <ijunfu@163.com>
 * @date   : 2024/6/28 22:01
 * @version: 1.0
 * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
public class BeanUtil {

    /**
     * @title  : Bean 转 Map
     * @param	: t 对象实例
     * @return : java.util.Map<java.lang.String,java.lang.Object>
     * @author : ijunfu <ijunfu@163.com>
     * @date   : 2024/6/28 22:19
     * @version: 1.0
     * @motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
     */
    public static <T> Map<String, Object> toMap(T t) {
        Map<String, Object> map = new HashMap<>();

        map.putAll(
                Arrays.asList(t.getClass().getDeclaredFields())
                        .stream()
                        .filter(field -> !field.getName().equals("serialVersionUID"))   // 过滤掉序列化ID
                        .collect(Collectors.toMap(
                                Field::getName,
                                field -> {
                                    field.setAccessible(Boolean.TRUE);
                                    try {
                                        return field.get(t);
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        ))
        );

        return map;
    }

    private BeanUtil(){}
}
