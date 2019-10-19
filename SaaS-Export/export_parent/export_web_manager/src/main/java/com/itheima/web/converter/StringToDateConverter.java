package com.itheima.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换类,实现Converter接口
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
