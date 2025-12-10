package cn.xcodev.ai.learn.openai._1_helloworld.controller;

import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
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
@AllArgsConstructor
@RequestMapping("/ai/openai")
public class OpenAiController {

    private final ChatClient chatClient;


    @GetMapping("/do_chat")
    public String call(@RequestParam(value = "msg", defaultValue = "你是谁") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .call()
                .content();
    }


    @GetMapping("/stream_chat")
    public Flux<String> stream(@RequestParam(value = "msg", defaultValue = "你是谁") String msg) {
        return chatClient
                .prompt()
                .user(msg)
                .stream()
                .content();
    }

}
