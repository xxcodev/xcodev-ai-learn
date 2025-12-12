package cn.xcodev.ai.learn.alibaba.v2._2_hooks_interceptors.hooks;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.hook.HookPosition;
import com.alibaba.cloud.ai.graph.agent.hook.HookPositions;
import com.alibaba.cloud.ai.graph.agent.hook.ModelHook;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author xcodev
 */

@HookPositions({HookPosition.BEFORE_MODEL})
public class StopConditionHook extends ModelHook {
    @Override
    public String getName() {
        return "";
    }

    @Override
    public CompletableFuture<Map<String, Object>> beforeModel(OverAllState state, RunnableConfig config) {
        // 检查是否找到答案，展示使用 OverAllState
        boolean answerFound = (Boolean) state.value("answer_found").orElse(false);
        // 检查错误次数，展示使用 RunnableConfig
        int errorCount = (Integer) Optional.ofNullable(config.context().get("error_count")).orElse(0);

        // 找到答案或错误过多时停止
        if (answerFound || errorCount > 3) {
            List<Message> messages = new ArrayList<>(
                    (List<Message>) state.value("messages").orElse(new ArrayList<>())
            );
            messages.add(new AssistantMessage(
                    answerFound ? "已找到答案，Agent 执行完成。"
                            : "错误次数过多 (" + errorCount + ")，Agent 执行终止。"
            ));
            return CompletableFuture.completedFuture(Map.of("messages", messages));
        }

        return CompletableFuture.completedFuture(Map.of());
    }
}
