package cn.xcodev.ai.learn.alibaba.v2._2_hooks_interceptors.hooks;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.hook.HookPosition;
import com.alibaba.cloud.ai.graph.agent.hook.ModelHook;
import org.springframework.ai.chat.messages.Message;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author xcodev
 */
public class MessageTrimmingHook extends ModelHook {

    private static final Integer MAX_MESSAGE = 10;

    @Override
    public String getName() {
        return "message_trimming";
    }

    @Override
    public HookPosition[] getHookPositions() {
        return new HookPosition[]{HookPosition.BEFORE_MODEL};
    }

    @Override
    public CompletableFuture<Map<String, Object>> beforeModel(OverAllState state, RunnableConfig config) {
        Optional<Object> messagesOpt = state.value("messages");
        if (messagesOpt.isPresent()) {
            List<Message> messages = (List<Message>) messagesOpt.get();
            if (messages.size() > MAX_MESSAGE) {
                return CompletableFuture.completedFuture(Map.of("messages",
                        messages.subList(messages.size() - MAX_MESSAGE, messages.size())));
            }
        }
        return CompletableFuture.completedFuture(Map.of());
    }
}
