package cn.monitor4all.monitor4java.utils;

import cn.monitor4all.monitor4java.constant.MonitorConstants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUtils {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void debug(String msg, Object...args) {
        System.out.printf("[%s][DEBUG][%s][%s]", DATE_FORMAT.format(new Date()), Thread.currentThread().getName(), MonitorConstants.MONITOR4JAVA);
        System.out.printf(msg, args);
        System.out.println();
    }

    public static void info(String msg, Object...args) {
        System.out.printf("[%s][INFO][%s][%s]", DATE_FORMAT.format(new Date()), Thread.currentThread().getName(), MonitorConstants.MONITOR4JAVA);
        System.out.printf(msg, args);
        System.out.println();
    }

    public static void warn(String msg, Object...args) {
        System.out.printf("[%s][WARN][%s][%s]", DATE_FORMAT.format(new Date()), Thread.currentThread().getName(), MonitorConstants.MONITOR4JAVA);
        System.out.printf(msg, args);
        System.out.println();
    }

    public static void error(String msg, Object...args) {
        System.out.printf("[%s][ERROR][%s][%s]", DATE_FORMAT.format(new Date()), Thread.currentThread().getName(), MonitorConstants.MONITOR4JAVA);
        System.out.printf(msg, args);
        System.out.println();
    }

}
