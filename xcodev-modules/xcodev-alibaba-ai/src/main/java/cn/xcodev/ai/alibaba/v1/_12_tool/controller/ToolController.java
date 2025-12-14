package cn.xcodev.ai.alibaba.v1._12_tool.controller;

import cn.xcodev.ai.alibaba.v1._12_tool.tools.DateTimeTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.support.ToolCallbacks;
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
@RequestMapping("/v1/tool")
public class ToolController {

    private final ChatClient chatClient;

    public ToolController(@Qualifier("qwen") ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping("/chat")
    public Flux<String> chat(@RequestParam(name = "msg", defaultValue = "你是谁") String msg) {

        ToolCallingChatOptions tools = ToolCallingChatOptions.builder()
                .toolCallbacks(ToolCallbacks.from(new DateTimeTool()))
                .build();

        return chatClient.prompt()
                .options(tools)
                .user(msg)
                .stream()
                .content();
    }
}
