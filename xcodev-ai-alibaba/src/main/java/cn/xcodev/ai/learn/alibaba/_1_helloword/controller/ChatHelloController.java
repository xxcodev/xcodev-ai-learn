package cn.xcodev.ai.learn.alibaba._1_helloword.controller;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 基础ai调用学习控制层
 *
 * @author xcodev
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ai/alibaba")
public class ChatHelloController {

    private ChatModel chatModel;

    @GetMapping("/hello/do_chat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping("/hello/stream_chat")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatModel.stream(msg);
    }

}
