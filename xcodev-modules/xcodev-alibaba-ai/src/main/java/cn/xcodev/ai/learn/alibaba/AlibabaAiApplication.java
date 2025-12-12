package cn.xcodev.ai.learn.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * ai alibaba框架启动类
 *
 * @author xcodev
 */

@EnableCaching
@ComponentScan(basePackages = {"cn.xcodev.ai.learn.alibaba.v1._7_memory"})
@SpringBootApplication
public class AlibabaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaAiApplication.class, args);
    }
}
