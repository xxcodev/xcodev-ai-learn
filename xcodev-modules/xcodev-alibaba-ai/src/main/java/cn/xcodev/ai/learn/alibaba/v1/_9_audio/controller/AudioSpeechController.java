package cn.xcodev.ai.learn.alibaba.v1._9_audio.controller;

import com.alibaba.dashscope.aigc.multimodalconversation.AudioParameters;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

/**
 *
 * @author xcodev
 */
@RestController
@RequestMapping("/v1/audio")
public class AudioSpeechController {
    private static final String MODEL = "qwen3-tts-flash";

    public static void call() throws ApiException, NoApiKeyException, UploadFileException {
        MultiModalConversation conv = new MultiModalConversation();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                // 新加坡和北京地域的API Key不同。获取API Key：https://help.aliyun.com/zh/model-studio/get-api-key
                // 若没有配置环境变量，请用百炼API Key将下行替换为：.apiKey("sk-xxx")
                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
                .model(MODEL)
                .text("你们的API文档写的乱七八糟。")
                .voice(AudioParameters.Voice.CHERRY)
                .languageType("Chinese")
                .build();
        MultiModalConversationResult result = conv.call(param);
        String audioUrl = result.getOutput().getAudio().getUrl();
        System.out.print(audioUrl);

        // 下载音频文件到本地
        try (InputStream in =  URI.create(audioUrl).toURL().openStream();
             FileOutputStream out = new FileOutputStream("downloaded_audio.wav")) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("\n音频文件已下载到本地: downloaded_audio.wav");
        } catch (Exception e) {
            System.out.println("\n下载音频文件时出错: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            // 以下为北京地域url，若使用新加坡地域的模型，需将url替换为：https://dashscope-intl.aliyuncs.com/api/v1
            Constants.baseHttpApiUrl = "https://dashscope.aliyuncs.com/api/v1";
            call();
        } catch (ApiException | NoApiKeyException | UploadFileException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }


}
