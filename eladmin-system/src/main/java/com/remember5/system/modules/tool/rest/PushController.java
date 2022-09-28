package com.remember5.system.modules.tool.rest;

import lombok.RequiredArgsConstructor;
import com.remember5.system.modules.tool.service.PushService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fly
 * @date 2021-12-24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/push")
public class PushController {

    private final PushService pushService;

    /**
     * 推送给所有用户
     *
     * @param msg 消息
     */
    @PostMapping(value = "/pushAll")
    public void pushToAll(@RequestParam("msg") String msg) {
        pushService.pushMsgToAll(msg);
    }

    /**
     * 推送给指定用户
     *
     * @param userId 用户id
     * @param msg    消息
     */
    @PostMapping(value = "pushOne")
    public void pushMsgToOne(@RequestParam("userId") String userId, @RequestParam("msg") String msg) {
        pushService.pushMsgToOne(userId, msg);
    }

}
