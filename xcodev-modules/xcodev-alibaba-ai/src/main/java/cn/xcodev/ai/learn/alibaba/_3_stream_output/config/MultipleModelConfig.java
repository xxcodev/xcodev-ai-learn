package cn.xcodev.ai.learn.alibaba._3_stream_output.config;


import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 多个模型配置
 *
 * @author xcodev
 */
@Configuration
public class MultipleModelConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apiKey;

    @Bean(name = "deepseekModel")
    public ChatModel deepseek() {
        String deepseek = "deepseek-v3.2";
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(deepseek).build())
                .build();
    }

    @Bean(name = "qwenModel")
    public ChatModel qwen() {
        String qwen = "qwen-plus-2025-07-28";
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(apiKey).build())
                .defaultOptions(DashScopeChatOptions.builder().withModel(qwen).build())
                .build();
    }

    @Bean(name = "deepseekClient")
    public ChatClient deepseekClient(@Qualifier("deepseekModel") ChatModel deepseekModel) {
        return ChatClient.builder(deepseekModel)
                .build();
    }

    @Bean(name = "qwenClient")
    public ChatClient qwenClient(@Qualifier("qwenModel") ChatModel qwenModel) {
        return ChatClient.builder(qwenModel)
                .build();
    }

}
