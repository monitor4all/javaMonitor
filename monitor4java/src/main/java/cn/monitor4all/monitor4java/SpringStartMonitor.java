package cn.monitor4all.monitor4java;

import cn.monitor4all.monitor4java.constant.MonitorConstants;
import cn.monitor4all.monitor4java.monitor.SpringBeanStartMonitor;
import cn.monitor4all.monitor4java.utils.LogUtils;
import com.alibaba.jvm.sandbox.api.Information;
import com.alibaba.jvm.sandbox.api.Module;
import com.alibaba.jvm.sandbox.api.ModuleLifecycle;
import com.alibaba.jvm.sandbox.api.resource.ModuleEventWatcher;
import org.kohsuke.MetaInfServices;

import javax.annotation.Resource;

@MetaInfServices(Module.class)
@Information(id = MonitorConstants.SPRING_START_MONITOR, mode = {Information.Mode.AGENT})
public class SpringStartMonitor implements Module, ModuleLifecycle {

    private static final String LOG_PREFIX = "[SpringStartMonitor]";

    @Resource
    private ModuleEventWatcher moduleEventWatcher;

    @Override
    public void onLoad() throws Throwable {
        LogUtils.info(LOG_PREFIX + "onLoad");
    }

    @Override
    public void onUnload() throws Throwable {
        LogUtils.info(LOG_PREFIX + "onUnload");
    }

    @Override
    public void onActive() throws Throwable {
        LogUtils.info(LOG_PREFIX + "onActive");
    }

    @Override
    public void onFrozen() throws Throwable {
        LogUtils.info(LOG_PREFIX + "onFrozen");
    }

    @Override
    public void loadCompleted() {
        LogUtils.info(LOG_PREFIX + "loadCompleted");
        new SpringBeanStartMonitor(moduleEventWatcher).start();
    }
}
