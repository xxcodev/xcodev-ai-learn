package cn.xcodev.ai.learn.alibaba.v1._7_memory.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * @author xcodev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String messageType;
    private String textContent;
    private LocalDateTime createTime;
}
