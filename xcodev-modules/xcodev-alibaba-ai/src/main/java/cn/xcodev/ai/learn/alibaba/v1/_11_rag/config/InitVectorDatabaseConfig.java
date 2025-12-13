package cn.xcodev.ai.learn.alibaba.v1._11_rag.config;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 初始化向量数据库
 *
 * @author xcodev
 */
@Slf4j
@Configuration
public class InitVectorDatabaseConfig {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisVectorStore redisVectorStore;
    private final Resource anotherFile;

    public InitVectorDatabaseConfig(
            @Value("classpath:another.txt") Resource anotherFile,
            RedisTemplate<String, Object> redisTemplate,
            RedisVectorStore redisVectorStore) {
        this.redisVectorStore = redisVectorStore;
        this.anotherFile = anotherFile;
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        //1.读取文件
        TextReader textReader = new TextReader(anotherFile);
        textReader.setCharset(Charset.defaultCharset());
        //3.文件转换为向量（分词）
        List<Document> list = new TokenTextSplitter().transform(textReader.read());
        String sourceMetadata = (String) textReader.getCustomMetadata().get("source");
        //4.md5获取是否重复
        String textMd5 = DigestUtils.md5Hex(sourceMetadata);
        if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(textMd5, "1"))) {

            redisVectorStore.add(list);
        } else {
            log.info("rag数据以经重复。");
        }
    }
}