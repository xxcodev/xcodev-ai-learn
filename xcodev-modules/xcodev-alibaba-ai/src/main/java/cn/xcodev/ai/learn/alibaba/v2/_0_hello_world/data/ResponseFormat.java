package cn.xcodev.ai.learn.alibaba.v2._0_hello_world.data;

import lombok.Data;

/**
 * 响应格式
 *
 * @author xcodev
 */
@Data
public class ResponseFormat {
    // 一个双关语响应（始终必需）
    private String punnyResponse;

    // 如果可用的话，关于天气的任何有趣信息
    private String weatherConditions;

}
