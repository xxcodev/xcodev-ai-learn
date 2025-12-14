package cn.xcodev.ai.alibaba.v1._5_template.controller;

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
@RequestMapping("/ai/alibaba/template")
public class TemplateController {

    private final ChatClient deepseek;
    private final ChatClient qwen;

    public TemplateController(@Qualifier("deepseek") ChatClient deepseek,
                              @Qualifier("qwen") ChatClient qwen) {
        this.deepseek = deepseek;
        this.qwen = qwen;
    }

    private static final String TEMPLATE = "你是一个{role},"
            + "请讲一个关于{topic}的故事，"
            + "并使用{output_format}格式输出，"
            + "字数控制在{wordCount}左右。";


    @GetMapping("/deepseek")
    public Flux<String> deepseekTemplate(@RequestParam("role") String role,
                                         @RequestParam("topic") String topic,
                                         @RequestParam("outputFormat") String outputFormat,
                                         @RequestParam("wordCount") String wordCount) {
        return deepseek.prompt()
                .system(TEMPLATE.replace("{role}", role)
                        .replace("{topic}", topic)
                        .replace("{output_format}", outputFormat)
                        .replace("{wordCount}", String.valueOf(wordCount)))
                .stream()
                .content();
    }


    @GetMapping("/qwen")
    public Flux<String> qwenTemplate(@RequestParam("role") String role,
                                     @RequestParam("topic") String topic,
                                     @RequestParam("outputFormat") String outputFormat,
                                     @RequestParam("wordCount") String wordCount) {
        return qwen.prompt()
                .system(TEMPLATE.replace("{role}", role)
                        .replace("{topic}", topic)
                        .replace("{output_format}", outputFormat)
                        .replace("{wordCount}", String.valueOf(wordCount)))
                .stream()
                .content();
    }

    private static final String TEMPLATE_SYSTEM = "你是一个{role}，拒绝回答没有关系的问题，"
            + "并使用{output_format}格式输出，"
            + "字数控制在{wordCount}左右。";

    private static final String TEMPLATE_USER = "解释一下{userTopic}。";


    @GetMapping("/deepseek/combination")
    public Flux<String> deepseekTemplateCombination(@RequestParam("role") String role,
                                                    @RequestParam("outputFormat") String outputFormat,
                                                    @RequestParam("wordCount") String wordCount,
                                                    @RequestParam("userTopic") String topic) {

        return deepseek.prompt()
                .system(TEMPLATE_SYSTEM.replace("{role}", role)
                        .replace("{output_format}", outputFormat)
                        .replace("{wordCount}", String.valueOf(wordCount)))
                .user(TEMPLATE_USER.replace("{userTopic}", topic))
                .stream()
                .content();
    }


}
