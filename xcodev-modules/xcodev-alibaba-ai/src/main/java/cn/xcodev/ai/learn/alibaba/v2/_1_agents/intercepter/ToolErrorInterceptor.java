package cn.xcodev.ai.learn.alibaba.v2._1_agents.intercepter;

import com.alibaba.cloud.ai.graph.agent.interceptor.ToolCallHandler;
import com.alibaba.cloud.ai.graph.agent.interceptor.ToolCallRequest;
import com.alibaba.cloud.ai.graph.agent.interceptor.ToolCallResponse;
import com.alibaba.cloud.ai.graph.agent.interceptor.ToolInterceptor;

/**
 * 工具异常拦截器
 *
 * @author xcodev
 */
public class ToolErrorInterceptor extends ToolInterceptor {
    @Override
    public ToolCallResponse interceptToolCall(ToolCallRequest request, ToolCallHandler handler) {
        try {
            return handler.call(request);
        } catch (Exception e) {
            return ToolCallResponse.of(request.getToolCallId(), request.getToolName(), "工具异常：" + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return "ToolErrorInterceptor";
    }
}
