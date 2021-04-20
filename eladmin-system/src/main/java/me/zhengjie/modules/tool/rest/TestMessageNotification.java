package me.zhengjie.modules.tool.rest;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.modules.tool.service.MessageNotificationService;
import me.zhengjie.modules.tool.websocket.WebSocketServers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.alibaba.fastjson.JSON.toJSONString;
import static me.zhengjie.utils.SecurityUtils.getCurrentUserId;
import static me.zhengjie.utils.SecurityUtils.getCurrentUsername;

/**
 * @author fly
 * @description
 * @date 2021/4/19 17:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messageNotification/addMessage")
public class TestMessageNotification {

    private final MessageNotificationService mnService;

    @GetMapping
    public String aaa() {
        String mnString = toJSONString(
                mnService.create(
                        MessageNotification.builder()
                                .briefly("就是简要说明")
                                .details("详细说明")
                                // 消息级别 0紧急 1普通
                                .messageLevel(1)
                                // 消息标签 类型 0错误 1普通通知 2待办
                                .messageLabel(1)
                                // 消息状态 0未开始 1进行中 2已处理
                                .messageState(0)
                                // 取当前登录用户id
                                //.userId(getCurrentUserId())
                                .build()));
        // 取当前登录用户名+Message 作为消息通道sid
        // String sid = getCurrentUsername()+"Message";
        String sid = "adminMessage";
        try {
            WebSocketServers.sendMessage(mnString, sid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "消息发送成功";
    }
}
