package cn.xcodev.ai.auto.core;

import cn.xcode.ai.common.pojo.MessageDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.*;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * redis存储会话
 *
 * @author xcodev
 */
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    private final String KEY = "chat";
    private final HashOperations<String, Object, Object> hashOps;

    public RedisChatMemoryRepository(RedisTemplate<String, Object> redisTemplate) {
        this.hashOps = redisTemplate.opsForHash();
    }

    @Override
    public @NonNull List<String> findConversationIds() {
        return hashOps.keys(KEY).stream().map(Object::toString).toList();
    }

    @Override
    public @NonNull List<Message> findByConversationId(@NonNull String conversationId) {
        List<MessageDTO> chatCache = (List<MessageDTO>) hashOps.get(KEY, conversationId);
        if (CollectionUtils.isEmpty(chatCache)) {
            return List.of();
        }
        return chatCache
                .stream()
                .map(msg -> {
                    Message result;
                    String messageType = msg.getMessageType();
                    String textContent = msg.getTextContent();

                    switch (MessageType.valueOf(messageType)) {
                        case MessageType.USER -> result = new UserMessage(textContent);
                        case MessageType.ASSISTANT -> result = new AssistantMessage(textContent);
                        case MessageType.TOOL -> result = ToolResponseMessage.builder().responses(List.of()).build();
                        default -> throw new IncompatibleClassChangeError();
                    }
                    return result;
                })
                .toList();
    }

    @Override
    public void saveAll(@NonNull String conversationId, @NonNull List<Message> messages) {
        List<MessageDTO> save =
                messages.stream()
                        .map(msg -> new MessageDTO(msg.getMessageType().name(), msg.getText(), LocalDateTime.of(2025, 12, 13, 12, 12, 12, 1212)))
                        .collect(Collectors.toList());
        hashOps.put(KEY, conversationId, save);

    }

    @Override
    public void deleteByConversationId(@NonNull String conversationId) {
        hashOps.delete(KEY, conversationId);
    }

}