package cn.xcodev.ai.alibaba.v2._2_hooks_interceptors.controller;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 * @author xcodev
 */
@RestController
@RequestMapping("/v2/hook")
public class HookController {

    private final ReactAgent anotherAgent;

    public HookController(@Qualifier("anotherAgent") ReactAgent anotherAgent) {
        this.anotherAgent = anotherAgent;
    }

    @GetMapping("/chat")
    public String chat(
            @RequestParam(name = "role", defaultValue = "魅魔") String role,
            @RequestParam(name = "msg", defaultValue = "你是谁") String msg) throws GraphRunnerException {
        RunnableConfig config = RunnableConfig.builder().threadId("1").build();
        return anotherAgent.call(msg, config).getText();
    }

    @GetMapping("/stream")
    public Flux<String> stream(
            @RequestParam(name = "role", defaultValue = "魅魔") String role,
            @RequestParam(name = "msg", defaultValue = "你是谁") String msg) throws GraphRunnerException {
        RunnableConfig config = RunnableConfig.builder().threadId("1").build();
       return anotherAgent.stream(msg).map(node->node.state().data().toString());
    }
}
