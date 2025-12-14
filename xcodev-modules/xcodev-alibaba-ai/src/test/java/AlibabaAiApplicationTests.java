import cn.xcodev.ai.learn.alibaba.AlibabaAiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author xcodev
 */

@SpringBootTest(classes = AlibabaAiApplication.class)
public class AlibabaAiApplicationTests {

    @Qualifier("qwenClient")
    @Autowired
    private ChatClient chatClient;

    @Test
    public void contextLoads() {

        System.out.println(chatClient);
    }

}
