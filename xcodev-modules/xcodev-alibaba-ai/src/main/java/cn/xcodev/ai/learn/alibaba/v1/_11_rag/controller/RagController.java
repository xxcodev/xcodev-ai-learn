package cn.xcodev.ai.learn.alibaba.v1._11_rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 * @author xcodev
 */
@RestController
@RequestMapping("/v1/rag")
public class RagController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagController(@Qualifier("deepseek") ChatClient chatClient,
                         VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    private final static String SYSTEM_PROMPT = """
            你是一个异世界的角色咨询师，根据对应的角色给出角色的解释，否则回复：亲，找不到哦！
            """;

    @RequestMapping("/chat")
    public Flux<String> chat(@RequestParam("msg") String msg) {
        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(vectorStore)
                        .topK(1)
                        .build())
                .build();
        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(msg)
                .advisors(advisor)
                .stream()
                .content();
    }
}