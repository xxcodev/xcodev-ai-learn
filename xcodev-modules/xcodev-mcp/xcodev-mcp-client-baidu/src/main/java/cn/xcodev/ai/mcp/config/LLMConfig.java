package cn.xcodev.ai.mcp.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author xcodev
 */
@Configuration
public class LLMConfig {

    @Bean(name = "qwenModel")
    public ChatModel qwenModel() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("AI_DASHSCOPE_API_KEY")).build())
                .build();
    }


    @Bean(name = "qwenClient")
    public ChatClient qwen(@Qualifier("qwenModel") ChatModel chatModel,
                           ToolCallbackProvider toolCallbackProvider) {

        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder().model("qwen-max").build())
                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
                .build();
    }

}
