package com.xull.webSocket.service;

import com.alibaba.fastjson.JSON;
import com.xull.webSocket.entity.InMessage;
import com.xull.webSocket.entity.OutMessage;
import com.xull.webSocket.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @description:
 * @author: xull
 * @date: 2018-09-01 8:16
 */
@Service
@Slf4j
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 简单点对点聊天
     * @param inMessage
     */
    public void sendChatMessage(InMessage inMessage) {
        //template的有点是我们可以灵活的获取前端的参数并指定订阅路径
        OutMessage message = new OutMessage();
        message.setFrom(inMessage.getFrom());
        message.setContent(inMessage.getContent());
        log.info("发送消息："+message.toString());
        template.convertAndSend("/chat/single/" + inMessage.getTo(),message);
    }

    /**
     * 发送在线用户
     * @param onlineUser
     */
    public void sendOnlineUser(Map<String, SysUser> onlineUser) {
        String jsonObject = JSON.toJSONString(onlineUser);
        OutMessage message = new OutMessage(jsonObject);
        log.info("发送消息："+message.toString());
        template.convertAndSend("/topic/onlineuser",message);
    }

    /**
     * 用于多人聊天
     * @param inMessage
     */
    public void sendTopicChat(InMessage inMessage) {
        OutMessage message = new OutMessage();
        message.setFrom(inMessage.getFrom());
        message.setContent(inMessage.getContent());
        log.info("发送消息："+message.toString());
        template.convertAndSend("/topic/chat",message );
    }
}
