package cn.xcodev.ai.learn.alibaba.v1._7_memory.controller;

import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 *
 * @author xcodev
 */

@RestController
@AllArgsConstructor
@RequestMapping("/v1/memory")
public class MemoryController {

/*    private final ReactAgent anotherAgent;

    @GetMapping("/chat")
    public String chat() throws GraphRunnerException {
        return anotherAgent.call("你是谁?", RunnableConfig.builder().threadId("xcodev-1").build()).getText();
    }*/


    private final ChatClient chatClient;


    @GetMapping("/chat")
    public Flux<String> chat(
            @RequestParam(name = "id", defaultValue = "1") String id,
            @RequestParam(name = "msg", defaultValue = "你是谁?") String msg) throws GraphRunnerException {
        return chatClient.prompt()
                .user(msg)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, id))
                .stream()
                .content();
    }
}
