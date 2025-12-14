package cn.xcodev.ai.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ai alibaba框架启动类
 *
 * @author xcodev
 */

@ComponentScan(basePackages = {"cn.xcodev.ai.alibaba.v1._13_mcp"})
@SpringBootApplication
public class AlibabaAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaAiApplication.class, args);
    }
}
