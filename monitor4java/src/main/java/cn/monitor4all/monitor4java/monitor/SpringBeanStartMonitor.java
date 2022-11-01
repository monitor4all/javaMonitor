package cn.monitor4all.monitor4java.monitor;


import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.monitor4all.monitor4java.checker.SpringBootHttpStatusChecker;
import cn.monitor4all.monitor4java.constant.MonitorConstants;
import cn.monitor4all.monitor4java.domain.SpringBean;
import cn.monitor4all.monitor4java.listener.SpringBeanInitListener;
import cn.monitor4all.monitor4java.utils.LogUtils;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.filter.Filter;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;

import java.util.Map;

public class SpringBeanStartMonitor extends Thread implements Monitor {

    private static final String LOG_PREFIX = "[SpringBeanStartMonitor]";

    private ModuleEventWatcher moduleEventWatcher;

    private SpringBeanInitListener springBeanInitListener;

    private SpringBootHttpStatusChecker springBootHttpStatusChecker;

    public SpringBeanStartMonitor(ModuleEventWatcher moduleEventWatcher) {
        this.moduleEventWatcher = moduleEventWatcher;
        this.springBeanInitListener = new SpringBeanInitListener();
        this.springBootHttpStatusChecker = new SpringBootHttpStatusChecker();
    }

    @Override
    public void run() {

        // 开启SpringBean初始化监听
        Filter springBeanFilter = new Filter() {
            @Override
            public boolean doClassFilter(int access, String javaClassName, String superClassTypeJavaClassName, String[] interfaceTypeJavaClassNameArray,
                                         String[] annotationTypeJavaClassNameArray) {
                return javaClassName.equals("org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory");
            }

            @Override
            public boolean doMethodFilter(int access, String javaMethodName, String[] parameterTypeJavaClassNameArray, String[] throwsTypeJavaClassNameArray,
                                          String[] annotationTypeJavaClassNameArray) {
                return javaMethodName.equalsIgnoreCase("initializeBean");
            }
        };
        moduleEventWatcher.watch(springBeanFilter, springBeanInitListener, Event.Type.BEFORE, Event.Type.RETURN);

        // 检查应用状态并结束监听
        while (true) {
            try {
                Thread.sleep(MonitorConstants.CHECK_INTERVAL_MILLS);
                if (springBootHttpStatusChecker.check()) {
                    break;
                }
            } catch (Exception e) {
                LogUtils.error(LOG_PREFIX + "run error" + e);
            }
        }

        // 导出CSV数据
        exportCsvData();

    }

    /**
     * 导出数据
     */
    public String exportCsvData() {
        Map<String, SpringBean> springBeanMap = springBeanInitListener.getSpringBeanMap();
        LogUtils.info(LOG_PREFIX + "exportData springBeanMap:" + springBeanMap);
        LogUtils.info(LOG_PREFIX + "exportData user dir:" + System.getProperty("user.dir"));
        CsvWriter writer = CsvUtil.getWriter(System.getProperty("user.dir") + "/logs/monitor4java/springBean.csv", CharsetUtil.CHARSET_UTF_8);
        writer.writeBeans(springBeanMap.values());
        writer.close();
        return null;
    }
}
