package cn.xcodev.ai.learn.alibaba._6_structured_output.controller;

import cn.xcodev.ai.learn.alibaba._6_structured_output.records.Student;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author xcodev
 */
@RestController
@RequestMapping("/ai/alibaba/structured")
public class StructuredController {

    private final ChatClient deepseek;
    private final ChatClient qwen;

    public StructuredController(@Qualifier("deepseek") ChatClient deepseek,
                                @Qualifier("qwen") ChatClient qwen) {
        this.deepseek = deepseek;
        this.qwen = qwen;
    }

    private static final String STRUCTURED_OUTPUT_PROMPT = """
            学号1002，大学专业是计算机科学，用户名是{sname}，邮箱是{email}
            """;

    @RequestMapping("/deepseek")
    public Student deepseek(String sname, String email) {
        return deepseek.prompt()
                .user(promptUserSpec ->
                        promptUserSpec.text(STRUCTURED_OUTPUT_PROMPT)
                                .param("sname", sname)
                                .param("email", email))
                .call()
                .entity(Student.class);
    }

    @RequestMapping("/qwen")
    public Student qwen(String sname, String email) {
        return qwen.prompt()
                .user(promptUserSpec ->
                        promptUserSpec.text(STRUCTURED_OUTPUT_PROMPT)
                                .param("sname", sname)
                                .param("email", email))
                .call()
                .entity(Student.class);
    }
}
