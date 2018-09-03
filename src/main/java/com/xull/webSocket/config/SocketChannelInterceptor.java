package com.xull.webSocket.config;

import com.xull.webSocket.controller.UserChatController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * @description:
 * @author: xull
 * @date: 2018-08-31 18:35
 */
@Slf4j
public class SocketChannelInterceptor extends ChannelInterceptorAdapter {

    /**
     * 在消息被实际发送到频道之前调用
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        log.info("SocketChannelInterceptor-->preSend");
        return super.preSend(message, channel);
    }

    /**
     * 发送消息调用后立即调用
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        log.info("SocketChannelInterceptor-->postSend");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器
        if (headerAccessor.getCommand() == null) {
            return;//避免非stomp消息类型，例如心跳检测
        }
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        log.info("SocketChannelInterceptor-->SessionId:" + sessionId);
        switch (headerAccessor.getCommand()) {
            case CONNECT:
                connect(sessionId);
            case DISCONNECT:
                disconnect(sessionId);
            case SUBSCRIBE:
                break;
            case UNSUBSCRIBE:
                break;
            default:
                    break;
        }
        super.postSend(message, channel, sent);
    }

    /**
     * 完成发送后调用，不管是否异常发生，一般用于资源清理
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        super.afterSendCompletion(message, channel, sent, ex);
    }

    /**
     * 接受消息之前的方法
     * @param channel
     * @return
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        return super.preReceive(channel);
    }

    /**
     * 接受消息之后的方法
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return super.postReceive(message, channel);
    }

    /**
     * 完成接受消息后调用
     * @param message
     * @param channel
     * @param ex
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        super.afterReceiveCompletion(message, channel, ex);
    }

    /**
     * 连接成功
     * @param sessionId
     */
    private void connect(String sessionId) {
        log.info("connect sessionId="+sessionId);
    }

    /**
     * 断开连接后
     * @param sessionId
     */
    private void disconnect(String sessionId) {
        log.info("disconnect sessionId:" + sessionId);
    }

}
