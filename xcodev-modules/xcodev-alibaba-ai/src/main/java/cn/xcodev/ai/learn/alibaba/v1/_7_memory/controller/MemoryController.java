package cn.xcodev.ai.learn.alibaba.v1._7_memory.controller;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xcodev
 */

@RestController
@AllArgsConstructor
@RequestMapping("/v1/memory")
public class MemoryController {

    private final ReactAgent anotherAgent;

    @GetMapping("/chat")
    public String chat() throws GraphRunnerException {
        return anotherAgent.call("你是谁?", RunnableConfig.builder().threadId("xcodev-1").build()).getText();
    }
}
