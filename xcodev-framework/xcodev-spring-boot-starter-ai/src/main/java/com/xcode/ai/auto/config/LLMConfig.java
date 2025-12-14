package com.xcode.ai.auto.config;

import com.alibaba.cloud.ai.agent.studio.loader.AgentLoader;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.agent.Agent;
import com.xcode.ai.auto.core.AgentStaticLoader;
import com.xcode.ai.auto.core.RedisChatMemoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * 大模型配置类
 *
 * @author xcodev
 */
@AutoConfiguration
public class LLMConfig {

    @Bean
    public MessageWindowChatMemory messageWindowChatMemory(RedisTemplate<String, Object> redisTemplate) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new RedisChatMemoryRepository(redisTemplate))
                .maxMessages(10)
                .build();
    }

    @Bean(name = "deepseekModel")
    public ChatModel deepseekModel() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("AI_DASHSCOPE_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().model("deepseek-v3.2").build())
                .build();
    }

    @Bean(name = "qwenModel")
    public ChatModel qwenModel() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("AI_DASHSCOPE_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().model("qwen-max").build())
                .build();
    }


    @Bean(name = "qwenClient")
    public ChatClient qwen(@Qualifier("qwenModel") ChatModel chatModel,
                           MessageWindowChatMemory messageWindowChatMemory) {

        return ChatClient.builder(chatModel)
                .defaultOptions(ChatOptions.builder().model("qwen-max").build())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build())
                .build();
    }

    @Bean(name = "deepseekClient")
    public ChatClient chatClient(@Qualifier("deepseekModel") ChatModel chatModel,
                                 MessageWindowChatMemory messageWindowChatMemory) {
        return ChatClient.builder(chatModel)
                .defaultOptions(DashScopeChatOptions.builder().model("deepseek-v3.2").build())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(messageWindowChatMemory).build())
                .build();
    }

    @Bean
    public AgentLoader agentStaticLoader(ApplicationContext applicationContext) {
        Map<String, Agent> agentMap = applicationContext != null ? applicationContext.getBeansOfType(Agent.class) : Collections.emptyMap();
        if (agentMap.isEmpty()) {
            return new AgentStaticLoader(new Agent[0]);
        }
        return new AgentStaticLoader(agentMap.values().toArray(new Agent[0]));

    }
}
