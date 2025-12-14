package cn.xcodev.ai.alibaba.v1._12_tool.tools;

import org.springframework.ai.tool.annotation.Tool;

/**
 *
 * @author xcodev
 */
public class DateTimeTool {

    @Tool(description = "获取当前时间")
    public String getCurrentTime() {
        return "当前时间是：" + java.time.LocalDateTime.now();
    }
}
