package cn.xcodev.ai.alibaba.v1._3_stream_output.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
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
@RequestMapping("/ai/alibaba/multiple")
public class ModelController {

    private final ChatModel deepseek;
    private final ChatModel qwen;
    private final ChatClient deepseekClient;
    private final ChatClient qwenClient;

    public ModelController(@Qualifier("deepseekModel") ChatModel deepseek,
                           @Qualifier("qwenModel") ChatModel qwen,
                           @Qualifier("deepseekClient") ChatClient deepseekClient,
                           @Qualifier("qwenClient") ChatClient qwenClient) {
        this.deepseek = deepseek;
        this.qwen = qwen;
        this.deepseekClient = deepseekClient;
        this.qwenClient = qwenClient;
    }


    @GetMapping("/deepseek_model")
    public Flux<String> doChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return deepseek.stream(msg);
    }

    @GetMapping("/qwen_model")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return qwen.stream(msg);
    }

    @GetMapping("/deepseek_client")
    public Flux<String> deepseekClient(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return deepseekClient.prompt()
                .user(msg)
                .stream()
                .content();
    }

    @GetMapping("/qwen_client")
    public Flux<String> qwenClient(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return qwenClient.prompt()
                .user(msg)
                .stream()
                .content();
    }

}
