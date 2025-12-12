package cn.xcodev.ai.learn.alibaba.v1._5_template.config;

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
    public ChatClient deepseek() {
        DashScopeChatModel model = DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                .build();
        return ChatClient.builder(model)
                .defaultOptions(DashScopeChatOptions.builder().model("deepseek-v3.2").build())
                .build();
    }

    @Bean(name = "qwen")
    public ChatClient qwen() {
        DashScopeChatModel model = DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                .build();
        return ChatClient.builder(model)
                .defaultOptions(DashScopeChatOptions.builder().model("qwen-plus-2025-07-28").build())
                .build();
    }


}
