package com.itheima.web.task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTask {

    public void excete() {
        System.out.println("当前时间:" +
                new SimpleDateFormat("yyyy:MM:dd HH:mm:ss").format(new Date()));
    }
}
