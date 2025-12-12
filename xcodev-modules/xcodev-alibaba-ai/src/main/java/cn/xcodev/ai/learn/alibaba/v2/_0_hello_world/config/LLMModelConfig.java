package cn.xcodev.ai.learn.alibaba.v2._0_hello_world.config;

import cn.xcodev.ai.learn.alibaba.v2._0_hello_world.data.ResponseFormat;
import cn.xcodev.ai.learn.alibaba.v2._0_hello_world.tools.UserLocationTool;
import cn.xcodev.ai.learn.alibaba.v2._0_hello_world.tools.WeatherForLocationTool;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author xcodev
 */

@Configuration
public class LLMModelConfig {

    private static final String SYSTEM_PROMPT = """
             你是个气象预报专家，说话会用双关语。
             你可以使用两个工具：
              - get_weather_for_location：用它获取特定地点的天气
              - get_user_location：利用此获取用户位置
            如果用户问你天气，务必知道具体位置。
            如果你能从问题中判断出他们指的是无论他们所在的地方，
            使用get_user_location工具查找它们的位置。
            """;

    @Bean
    public ReactAgent reactAgent() {
        // 创建工具回调
        ToolCallback getWeatherTool = FunctionToolCallback
                .builder("getWeatherForLocation", new WeatherForLocationTool())
                .description("获取某个城市的天气")
                .inputType(String.class)
                .build();

        ToolCallback getUserLocationTool = FunctionToolCallback
                .builder("getUserLocation", new UserLocationTool())
                .description("根据用户ID获取用户位置")
                .inputType(String.class)
                .build();

        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                .build();

        ChatModel chatModel = DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(DashScopeChatOptions.builder()
                        .model("qwen-plus-2025-07-28")
                        .temperature(0.7)
                        .maxToken(1000)
                        .build())
                .build();

        // 创建 agent
        return ReactAgent.builder()
                .name("weather_pun_agent")
                .model(chatModel)
                .systemPrompt(SYSTEM_PROMPT)
                .tools(getUserLocationTool, getWeatherTool)
                .outputType(ResponseFormat.class)
                .saver(new MemorySaver())
                .build();
    }

}
