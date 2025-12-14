package cn.xcodev.ai.alibaba.v2._1_agents.intercepter;

import com.alibaba.cloud.ai.graph.agent.interceptor.ModelCallHandler;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelInterceptor;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelRequest;
import com.alibaba.cloud.ai.graph.agent.interceptor.ModelResponse;
import org.springframework.ai.chat.messages.SystemMessage;

/**
 * 异世界动态系统提示词拦截器
 *
 * @author xcodev
 */
public class AnotherPromptInterceptor extends ModelInterceptor {
    @Override
    public ModelResponse interceptModel(ModelRequest request, ModelCallHandler handler) {
        String userRole = (String) request.getContext().getOrDefault("user_role", "default");

        String dynamicPrompt = switch (userRole) {
            case "魅魔" -> "你是个一个魅魔。" +
                    "- 使用暧昧的语气回答。";
            case "天使" -> "你是个一个天使。" +
                    "使用可爱的语气回答。";
            default -> "你是一个普通的人类。" +
                    "- 使用普通的语气回答。";
        };

        SystemMessage enhancedSystemMessage;
        if (request.getSystemMessage() == null) {
            enhancedSystemMessage = new SystemMessage(dynamicPrompt);
        } else {
            enhancedSystemMessage = new SystemMessage(request.getSystemMessage().getText() + dynamicPrompt);
        }

        ModelRequest modified = ModelRequest.builder(request)
                .systemMessage(enhancedSystemMessage)
                .build();
        return handler.call(modified);
    }

    @Override
    public String getName() {
        return "AnotherPromptInterceptor";
    }
}
