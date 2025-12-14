package cn.xcodev.ai.alibaba.v2._1_agents.config;

import cn.xcodev.ai.alibaba.v2._1_agents.intercepter.AnotherPromptInterceptor;
import cn.xcodev.ai.alibaba.v2._1_agents.intercepter.ExpertPromptInterceptor;
import cn.xcodev.ai.alibaba.v2._1_agents.intercepter.ToolErrorInterceptor;
import cn.xcodev.ai.alibaba.v2._1_agents.result.PoemOutput;
import cn.xcodev.ai.alibaba.v2._1_agents.tools.SearchTool;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
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

    @Bean(name = "expertAgent")
    public ReactAgent expertAgent(@Qualifier("deepseek") ChatModel chatModel) {
        return ReactAgent.builder()
                .name("search_agent")
                .model(chatModel)
                .tools(toolCallback())
                .saver(new MemorySaver())
                .interceptors(new ToolErrorInterceptor())
                .interceptors(new ExpertPromptInterceptor())
                .build();
    }

    @Bean(name = "anotherAgent")
    public ReactAgent reactAgent(@Qualifier("deepseek") ChatModel chatModel) {
        return ReactAgent.builder()
                .name("search_agent")
                .model(chatModel)
                .tools(toolCallback())
                .saver(new MemorySaver())
                .outputType(PoemOutput.class)
                .interceptors(new ToolErrorInterceptor())
                .interceptors(new AnotherPromptInterceptor())
                .build();
    }

    private ToolCallback toolCallback() {
        return FunctionToolCallback.builder("search", new SearchTool())
                .description("根据用户输入的关键词，返回相关的搜索结果")
                .inputType(String.class)
                .build();
    }
}