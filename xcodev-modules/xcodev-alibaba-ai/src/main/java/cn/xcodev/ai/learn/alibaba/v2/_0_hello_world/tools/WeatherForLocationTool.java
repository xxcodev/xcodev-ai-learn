package cn.xcodev.ai.learn.alibaba.v2._0_hello_world.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.function.BiFunction;

/**
 * 天气工具类
 *
 * @author xcodev
 */
public class WeatherForLocationTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(
            @ToolParam(description = "城市名称") String city,
            ToolContext toolContext) {
        return "这里总是阳光明媚 " + city + "!";
    }
}
