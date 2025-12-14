package cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.hooks;

import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.hook.ModelHook;
import org.springframework.ai.chat.messages.Message;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 校验当前问题是否已经回复过
 *
 * @author xcodev
 */
public class RepeatHook extends ModelHook {
    @Override
    public String getName() {
        return "repeatHook";
    }

    @Override
    public CompletableFuture<Map<String, Object>> beforeModel(OverAllState state, RunnableConfig config) {
        Optional<Object> messagesOpt = state.value("messages");
        if (messagesOpt.isPresent()) {
            List<Message> messages = (List<Message>) messagesOpt.get();
            String lastMsg = messages.getFirst().getText();

            if (messages.size() > 1 && messages.stream().anyMatch(message -> message.getText().equals(lastMsg))) {
              return   CompletableFuture.completedFuture(Map.of("answer_found", true));
            }
        }
        return CompletableFuture.completedFuture(Map.of());
    }

}
