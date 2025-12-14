package cn.xcodev.ai.alibaba.v1._2_model_client.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 使用ChatModel调用模型
 *
 * @author xcodev
 */
@RestController
@RequestMapping("/ai/alibaba/model")
public class ModelController {

    @Resource(name = "qwenModel")
    private  ChatModel chatModel;

    @GetMapping("/do_chat")
    public String doChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatModel.call(msg);
    }

    @GetMapping("/stream_chat")
    public Flux<String> streamChat(@RequestParam(name = "msg", defaultValue = "你是谁？") String msg) {
        return chatModel.stream(msg);
    }

}
