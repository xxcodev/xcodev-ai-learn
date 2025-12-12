package cn.xcodev.ai.learn.alibaba.v2._0_hello_world.controller;


import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xcodev
 */

@RestController
@AllArgsConstructor
@RequestMapping("/ai/alibaba/memory")
public class ChatController {

    private final ReactAgent reactAgent;


    @RequestMapping("/react")
    public String react(@RequestParam(name = "msg", defaultValue = "外面的天气怎么样？") String msg,
                        @RequestParam(name = "threadId", defaultValue = "1") String threadId)
            throws GraphRunnerException {
        // threadId 是给定对话的唯一标识符
        RunnableConfig runnableConfig = RunnableConfig.builder()
                .threadId(threadId)
                .addMetadata("user_id", "1")
                .build();
        return reactAgent.call(msg, runnableConfig).getText();
    }


}
