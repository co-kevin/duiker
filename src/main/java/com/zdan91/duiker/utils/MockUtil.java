package com.zdan91.duiker.utils;

import com.xiaoleilu.hutool.util.RandomUtil;
import com.xiaoleilu.hutool.util.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 根据字段类型模拟出数据来填充对象,用在接口未完成时,向前端提供假数据
 * FileName: MockUtil.java
 * Author: shijikun
 * Email: shijikun@wzdai.com
 * Date: 21/09/2017 13:17
 * Description:
 * History:
 */
public class MockUtil {
    /**
     * 自动填充数据到对象中
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mockIt(Class<T> clazz) {
        try {
            T obj = (T) Class.forName(clazz.getName()).newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Method method = clazz.getDeclaredMethod("set" + StrUtil.upperFirst(field.getName()), field.getType());
                method.invoke(obj, getValue(field.getType()));
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getValue(Class<?> type) {
        String typeStr = type.toString();
        if ("class java.lang.Integer".equalsIgnoreCase(typeStr)) {
            return Integer.valueOf(RandomUtil.randomNumbers(4));
        } else if ("class java.lang.String".equalsIgnoreCase(typeStr)) {
            return RandomUtil.randomString(8);
        } else if ("class java.lang.Float".equalsIgnoreCase(typeStr)) {
            return Float.valueOf(RandomUtil.randomNumbers(5));
        } else if ("class java.math.BigDecimal".equalsIgnoreCase(typeStr)) {
            return new BigDecimal(RandomUtil.randomInt(0, 10000));
        } else if ("class java.util.Date".equalsIgnoreCase(typeStr)) {
            return new Date();
        }
        return null;
    }


}
