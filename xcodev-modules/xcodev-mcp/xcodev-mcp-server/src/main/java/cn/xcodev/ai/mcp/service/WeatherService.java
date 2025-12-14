package cn.xcodev.ai.mcp.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * @author xcodev
 */
@Service
public class WeatherService {

    @Tool(description = "获取城市天气")
    public String getWeather(String city) {
        Map<String, String> map = Map.of(
                "深圳", "今天阴天，今天两天晴天",
                "上海", "今天晴，明天多云",
                "北京", "今天多云，明天阴天"
        );
        return map.getOrDefault(city, "没有这个城市的天气信息");
    }
}
