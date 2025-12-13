package cn.xcodev.ai.learn.alibaba.v1._11_rag.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
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

    @Bean(name = "deepseek")
    public ChatClient chatClient() {
        return ChatClient.builder(DashScopeChatModel.builder()
                        .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                        .build())
                .defaultOptions(DashScopeChatOptions.builder().model("deepseek-v3.2").build())
                .build();
    }

}
