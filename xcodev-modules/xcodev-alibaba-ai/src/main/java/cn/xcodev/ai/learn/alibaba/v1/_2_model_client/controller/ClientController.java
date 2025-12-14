package cn.xcodev.ai.learn.alibaba.v1._2_model_client.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 使用ChatClient调用模型
 *
 * @author xcodev
 */
@RestController
@RequestMapping("/ai/alibaba/client")
public class ClientController {

    @Resource(name = "qwenClient")
    private ChatClient chatClient;

    @GetMapping("/do_chat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .call()
                .content();
    }

    @GetMapping("/stream_chat")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .stream()
                .content();
    }
}
