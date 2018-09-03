package com.xull.webSocket.entity;

/**
 * @description:
 * @author: xull
 * @date: 2018-09-01 8:18
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 接受客户端数据实体
 */
@Data
@AllArgsConstructor//生成一个包含所有变量的构造方法
@NoArgsConstructor//生成一个无参构造方法
public class InMessage {
    private String from;
    private String to;
    private String content;
    private Date time;

    public InMessage(String content) {
        this.content = content;
    }
}
