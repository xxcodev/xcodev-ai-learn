package cn.xcodev.ai.learn.alibaba.v1._10_embedding.controller;

import lombok.AllArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author xcodev
 */

@RestController
@AllArgsConstructor
@RequestMapping("/v1/embedding")
public class EmbeddingController {

    private final VectorStore vectorStore;
    private final EmbeddingModel embeddingModel;

    @GetMapping("/emb")
    public void emb() {


        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        vectorStore.add(documents);

        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
    }

    @GetMapping("/test2embed")
    public void test2embed() {
        float[] helloWorlds = embeddingModel.embed("hello world");
        System.out.println(Arrays.toString(helloWorlds));
    }

    @GetMapping("query")
    public String query() {
        return vectorStore.similaritySearch(SearchRequest.builder().query("AI").topK(1).build()).toString();
    }
}
