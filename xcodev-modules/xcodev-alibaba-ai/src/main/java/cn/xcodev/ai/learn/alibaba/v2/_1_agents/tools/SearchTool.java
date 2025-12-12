package cn.xcodev.ai.learn.alibaba.v2._1_agents.tools;

import org.springframework.ai.chat.model.ToolContext;

import java.util.function.BiFunction;

/**
 *
 * @author xcodev
 */
public class SearchTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(String query, ToolContext toolContext) {
        return "搜索结果：" + query;
    }
}
