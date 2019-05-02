package com.karlfans.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * UDP Server服务器监听启动
 */
@Component
public class UDPServerStartupEvent implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(UDPServerStartupEvent.class);
    private static ApplicationContext context;

    public static Object getBean(Class beanName) {
        return context != null ? context.getBean(beanName) : null;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            context = contextRefreshedEvent.getApplicationContext();
            SysConfig sysConfig = (SysConfig) context.getBean(SysConfig.class);
            log.info("启动服务器配置信息：{}", sysConfig);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 启动UDP服务器，监听客户端UDP客户端
                    UdpServer udpServer = (UdpServer) UDPServerStartupEvent.getBean(UdpServer.class);
                    udpServer.run(sysConfig.getUdpReceivePort());
                }
            });
            t.start();

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
}
