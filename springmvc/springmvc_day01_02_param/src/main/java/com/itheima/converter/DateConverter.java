package com.itheima.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期类型转换器,需要实现接口:Converter
 * Converter<S,T>:
 *      S:Source,转换之前的数据类型,这里是String类型的日期
 *      T:Target,转换之后的数据类型,这里是Date类型的日期
 */
public class DateConverter implements Converter<String, Date> {


    /**
     * 实现日期转换逻辑
     */
    public Date convert(String source) {

        // 定义日期格式化对象
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // 转换成功,直接返回
            return format.parse(source);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 转换异常,返回null
        return null;
    }
}
