package cn.xcodev.ai.learn.alibaba.v2._0_hello_world.tools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.function.BiFunction;

/**
 *
 * @author xcodev
 */
public class UserLocationTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(
            @ToolParam(description = "User query") String query,
            ToolContext toolContext) {
        // 从上下文中获取用户信息
        String userId = (String) toolContext.getContext().get("user_id");
        return "1".equals(userId) ? "深圳" : "娄底";
    }
}
