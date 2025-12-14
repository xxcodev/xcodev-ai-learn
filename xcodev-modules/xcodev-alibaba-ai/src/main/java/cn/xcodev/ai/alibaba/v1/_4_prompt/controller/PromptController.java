package cn.xcodev.ai.alibaba.v1._4_prompt.controller;

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
@RequestMapping("/ai/alibaba/prompt")
public class PromptController {

    private final ChatClient deepseek;
    private final ChatClient qwen;

    public PromptController(@Qualifier("deepseek") ChatClient deepseek,
                            @Qualifier("qwen") ChatClient qwen) {
        this.deepseek = deepseek;
        this.qwen = qwen;
    }


    @GetMapping("/deepseek")
    public Flux<String> deepseek(@RequestParam(value = "msg", defaultValue = "你是谁？") String msg) {
        return deepseek.prompt()
                .system("你是一个喵娘，只能使用可爱的语气回答问题。")
                .user(msg)
                .stream()
                .content();
    }

    @GetMapping("/qwen")
    public Flux<String> qwen(@RequestParam(value = "msg", defaultValue = "你是谁？") String msg) {
        return qwen.prompt()
                .user(msg)
                .stream()
                .content();
    }


}
