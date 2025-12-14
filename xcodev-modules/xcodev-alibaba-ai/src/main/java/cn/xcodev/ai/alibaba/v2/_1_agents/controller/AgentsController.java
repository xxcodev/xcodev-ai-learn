package cn.xcodev.ai.alibaba.v2._1_agents.controller;

import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xcodev
 */

@RestController
@RequestMapping("/ai/alibaba")
public class AgentsController {

    private final ReactAgent expertAgent;
    private final ReactAgent anotherAgent;

    public AgentsController(@Qualifier("expertAgent") ReactAgent expertAgent,
                            @Qualifier("anotherAgent") ReactAgent anotherAgent) {
        this.expertAgent = expertAgent;
        this.anotherAgent = anotherAgent;
    }


    @GetMapping("/expertAgent")
    public String chat(@RequestParam(name = "msg", defaultValue = "Java是什么？") String msg) throws GraphRunnerException {
        RunnableConfig config = RunnableConfig.builder()
                .threadId("1")
                .addMetadata("user_role", "expert")
                .build();
        return expertAgent.call(msg, config).getText();
    }

    @GetMapping("/anotherAgent")
    public String anotherChat(
            @RequestParam(name = "role", defaultValue = "魅魔") String role,
            @RequestParam(name = "msg", defaultValue = "你需要做什么？") String msg) throws GraphRunnerException {
        RunnableConfig config = RunnableConfig.builder()
                .threadId("1")
                .addMetadata("user_role", role)
                .build();
        return anotherAgent.call(msg, config).getText();
    }
}
