package com.unifs.sdbst.app.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @version V1.0
 * @title: TypeTransformUtil
 * @projectName sdbst
 * @description: 类型转换工具类
 * @author： 张恭雨
 * @date 2019/8/15 11:54
 */
public class TypeTransformUtil {
    /**
     * 　　* @description: 将对象转换成map类型
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/15 11:55
     *
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }

        return map;
    }
    /**
     　　* @description: 将map转换成对象
     　　* @param ${tags}
     　　* @return ${return_type}
     　　* @throws
     　　* @author 张恭雨
     　　* @date 2019/8/15 14:34
     　　*/
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }
}
