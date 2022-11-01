# javaMonitor

借助JVM Sandbox能力, 实现Java无侵入的应用启动性能监控，实时性能监控，切面处理等功能。

## 功能


### SpringBean启动耗时性能分析

通过JavaAgent方式，在SpringBean初始化时进行全周期数据监控，在SpringBean初始化完成后输出详细数据报表。

|beanName|className|classLoaderName|startUnixTime|finishUnixTime|timeCosts|
|----|----|----|----|----|----|
|org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration|class org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration|class sun.misc.Launcher$AppClassLoader|1667293220526|1667293220526|0|
|error|class org.springframework.web.servlet.view.InternalResourceView|class sun.misc.Launcher$AppClassLoader|1667293221184|1667293221186|2|
|requestContextFilter|class org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter|class sun.misc.Launcher$AppClassLoader|1667293219883|1667293219884|1
|org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration$EmbeddedTomcat|class org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryConfiguration$EmbeddedTomcat|class sun.misc.Launcher$AppClassLoader|1667293219450|1667293219545|95|
rabbitTemplate|class org.springframework.amqp.rabbit.core.RabbitTemplate|class sun.misc.Launcher$AppClassLoader|1667293220478|1667293220486|8|



## 使用方式

首先准备好您的JVM Sandbox环境：

[JVM Sandbox 安装](https://github.com/alibaba/jvm-sandbox#%E5%BF%AB%E9%80%9F%E5%AE%89%E8%A3%85)

执行deploy2sandbox.sh

`deploy2sandbox.sh {你的sandbox根路径}`

例如：

`./deploy2sandbox.sh  ~/sandbox/`

在应用中添加参数

`-javaagent:{你的sandbox根路径}/lib/sandbox-agent.jar`

启动应用，见到类似如下输出说明Agent植入成功！

`[INFO][main][Monitor4Java][SpringStartMonitor]loadCompleted`