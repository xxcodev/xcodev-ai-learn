package cn.xcodev.ai.learn.alibaba.v1._7_memory.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.redis.RedisSaver;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author xcodev
 */

@Configuration
public class LLMConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

//    @Bean(name = "deepseek")
//    public ChatClient deepseek() {
//        DashScopeChatModel model = DashScopeChatModel.builder()
//                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
//                .build();
//        return ChatClient.builder(model)
//                .defaultOptions(DashScopeChatOptions.builder().model("deepseek-v3.2").build())
//                .build();
//    }

    @Bean
    public RedisSaver redisSaver(RedissonClient redissonClient) {
        return RedisSaver.builder()
                .redisson(redissonClient)
                .build();
    }

    @Bean(name = "anotherAgent")
    public ReactAgent expertAgent(RedisSaver redisSaver) {
        return ReactAgent.builder()
                .name("anotherAgent")
                .model(DashScopeChatModel.builder().dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build()).build())
                .saver(redisSaver)
                .build();
    }


}
