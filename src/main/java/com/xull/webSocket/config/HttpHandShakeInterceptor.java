package com.xull.webSocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @description: WebSocket握手请求拦截器，检查握手请求和响应
 *                  对WebSocketHandler传递属性
 * @author: xull
 * @date: 2018-09-01 9:51
 */
@Slf4j
public class HttpHandShakeInterceptor implements HandshakeInterceptor {
    /**
     * 握手之前传递改方法，继续握手返回true，否则中断为false，
     * 通过attributes参数设置WebSocketSession的属性
     * 暂时在WebSocketSession中存入sessionId
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) throws Exception {
        log.info("beforeHandshake拦截器");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = request.getServletRequest().getSession();
            String sessionId = session.getId();
            log.info("beforeHandshake sessionId:"+sessionId);
            map.put("sessionId", sessionId);
        }
        return true;
    }

    /**
     * 握手之后的方法，无论是否成功都指明响应状态和响应头
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest,
                               ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler,
                               Exception e) {
        log.info("afterHandshake拦截器");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = request.getServletRequest().getSession();
            String sessionId = session.getId();
            log.info("afterHandshake sessionId:"+sessionId);
        }
    }

}
