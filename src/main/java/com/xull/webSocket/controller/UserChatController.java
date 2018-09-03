package com.xull.webSocket.controller;

import com.xull.webSocket.entity.InMessage;
import com.xull.webSocket.entity.SysUser;
import com.xull.webSocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: xull
 * @date: 2018-09-01 8:14
 */
@Controller
@EnableScheduling
@Slf4j
public class UserChatController {
    @Autowired
    private WebSocketService webSocketService;

    public static Map<String, SysUser> userMap = new ConcurrentHashMap<>();

    @RequestMapping(value = "index", method = RequestMethod.POST)
    public String userLogin(@RequestParam("username") String username, HttpSession session, Model model) {
        userMap.put(session.getId(), new SysUser(username));
        webSocketService.sendOnlineUser(userMap);
        model.addAttribute("username", username);
        return "index";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    /**
     * 定时推送在线用户
     */
//    @Scheduled(fixedRate = 2000)
//    public void onlineUser() {
//        webSocketService.sendOnlineUser(userMap);
//    }(
    @MessageMapping("/public/onlineuser")
    public void getOnlineuser() {
        webSocketService.sendOnlineUser(userMap);

    }
//    /**
//     * 群聊接口
//     * @param message
//     * @param headerAccessor
//     */
//    public  void topicChat(InMessage message, SimpMessageHeaderAccessor headerAccessor) {
//        //这个sessionId是拦截器HttpHandShackInterceptor放入的
//        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
//        //通过sessionId获取在线用户信息
//        SysUser user = userMap.get(sessionId);
//        message.setFrom(user.getUsername());
//        webSocketService.sendTopicChat(message);
//    }

    @MessageMapping("/private/chat")
    public void chat(InMessage message) {
        webSocketService.sendChatMessage(message);
    }

    @MessageMapping("/group/chat")
    public void groupChat(InMessage message) {
        webSocketService.sendTopicChat(message);
    }
}
