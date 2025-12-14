package cn.xcodev.ai.mcp.controller;

import org.springframework.ai.chat.client.ChatClient;
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
@RequestMapping("/mcp")
public class McpController {

    private final ChatClient qwen;

    public McpController(@Qualifier("qwenClient") ChatClient qwen) {
        this.qwen = qwen;
    }

    @GetMapping("/doChat")
    public Flux<String> doChat(@RequestParam(name = "msg", defaultValue = "深圳天气如何？") String msg) {
        return qwen.prompt()
                .user(msg)
                .stream()
                .content();

    }
}
