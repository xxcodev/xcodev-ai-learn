package cn.xcodev.ai.alibaba.v1._1_helloword.controller;

import jakarta.annotation.Resource;
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
@RequestMapping("/ai/alibaba")
public class ChatHelloController {

    @Resource(name = "qwenModel")
    private ChatModel chatModel;

    @GetMapping("/do_chat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping("/stream_chat")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatModel.stream(msg);
    }

}
