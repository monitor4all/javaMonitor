package cn.monitor4all.monitor4java.listener;

import cn.monitor4all.monitor4java.domain.SpringBean;
import cn.monitor4all.monitor4java.utils.LogUtils;
import com.alibaba.jvm.sandbox.api.event.BeforeEvent;
import com.alibaba.jvm.sandbox.api.event.Event;
import com.alibaba.jvm.sandbox.api.event.ReturnEvent;
import com.alibaba.jvm.sandbox.api.listener.EventListener;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SpringBeanInitListener implements EventListener {

    private static final String LOG_PREFIX = "[SpringBeanInitListener]";

    private static final Map<String, SpringBean> SPRING_BEAN_MAP = new ConcurrentHashMap<>();

    private final static String SPLITTER = "-";

    @Override
    public void onEvent(Event event) throws Throwable {
        if (event instanceof BeforeEvent) {
            BeforeEvent beforeEvent = (BeforeEvent) event;
            String uniqueKey = beforeEvent.invokeId + SPLITTER + beforeEvent.processId;
            if (SPRING_BEAN_MAP.containsKey(uniqueKey)) {
                LogUtils.error(LOG_PREFIX + "springBeanMap contains key error: " + uniqueKey);
                return;
            }
            SpringBean springBean = new SpringBean();
            springBean.setBeanName((beforeEvent).argumentArray[0].toString());
            springBean.setClassName(Optional.of(beforeEvent.argumentArray[1])
                    .map(Object::getClass)
                    .map(Class::toString)
                    .orElse(null));
            springBean.setClassLoaderName(Optional.of(beforeEvent.argumentArray[1]).map(Object::getClass)
                    .map(Class::getClassLoader)
                    .map(ClassLoader::getClass)
                    .map(Class::toString)
                    .orElse(null));
            springBean.startTimer();
            SPRING_BEAN_MAP.put(uniqueKey, springBean);

        } else if (event instanceof ReturnEvent) {
            ReturnEvent returnEvent = (ReturnEvent) event;
            String uniqueKey = returnEvent.invokeId + SPLITTER + returnEvent.processId;
            if (!SPRING_BEAN_MAP.containsKey(uniqueKey)) {
                LogUtils.error(LOG_PREFIX + "springBeanMap do not contains key error: " + uniqueKey);
                return;
            }
            SpringBean springBean = SPRING_BEAN_MAP.get(uniqueKey);
            springBean.finishTimer();
        }
    }

    public Map<String, SpringBean> getSpringBeanMap() {
        return SPRING_BEAN_MAP;
    }
}
