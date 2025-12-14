package cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.config;

import cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.hooks.LoggingHook;
import cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.hooks.MessageTrimmingHook;
import cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.hooks.RepeatHook;
import cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.hooks.StopConditionHook;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.hook.modelcalllimit.ModelCallLimitHook;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 *
 * @author xcodev
 */
@Configuration
public class AgentsConfig {

    @Bean(name = "deepseek")
    public ChatModel seniorModel() {
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("AI_DASHSCOPE_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder()
                        .model("deepseek-v3.2")
                        .temperature(0.7)
                        .maxToken(1000)
                        .topP(0.9)
                        .build())
                .build();
    }


    @Bean(name = "anotherAgent")
    public ReactAgent reactAgent(@Qualifier("deepseek") ChatModel chatModel) {
        return ReactAgent.builder()
                .name("search_agent")
                .model(chatModel)
                .hooks(new LoggingHook(),
                        new RepeatHook(),
                        new StopConditionHook(),
                        new MessageTrimmingHook(),
                        new ModelCallLimitHook.Builder().runLimit(5).build())
                .saver(new MemorySaver())
                .build();
    }
}