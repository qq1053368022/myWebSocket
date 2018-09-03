package com.xull.webSocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 * @author: xull
 * @date: 2018-08-31 15:59
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
//@EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，
// 这时候控制器（controller）开始支持@MessageMapping,就像是使用@requestMapping一样。
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * 配置消息中心
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpoint-websocket")
                .addInterceptors(new HttpHandShakeInterceptor())
                .setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/chat");//服务端推消息给客户端的路径前缀
        registry.setApplicationDestinationPrefixes("/app");//客户端发送数据给服务端的前缀
//        registry.setUserDestinationPrefix("/user");//客户端发送消息给服务端然后转发客户端的前缀

    }

    /**
     * 客户端呼入时添加SocketChannelInterceptor拦截器
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelInterceptor());
    }
    /**
     * 客户端呼出时添加SocketChannelInterceptor拦截器
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new SocketChannelInterceptor());
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        log.info("客户端"+event.getSessionId()+"断开连接");
    }


}
