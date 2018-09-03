package com.xull.webSocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description:
 * @author: xull
 * @date: 2018-09-01 8:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutMessage {
    private String from;
    private String content;
    private Date time = new Date();

    public OutMessage(String content) {
        this.content = content;
    }
}
