package cn.monitor4all.monitor4java.checker;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.monitor4all.monitor4java.utils.LogUtils;

public class SpringBootHttpStatusChecker implements Checker {

    private static final String LOG_PREFIX = "[SpringBootHttpStatusChecker]";

    private static final String SPRING_BOOT_URL = "http://127.0.0.1:8080/";
    private static final int SPRING_BOOT_URL_TIMEOUT = 500;

    @Override
    public boolean check() {
        try {
            HttpRequest httpRequest = HttpRequest.get(SPRING_BOOT_URL).timeout(SPRING_BOOT_URL_TIMEOUT);
            HttpResponse httpResponse = httpRequest.execute();
            LogUtils.info(LOG_PREFIX + httpResponse);
            return httpResponse.getStatus() < 500;
        } catch (Exception e) {
            LogUtils.debug(LOG_PREFIX + "check error" + e);
            return false;
        }
    }

}
