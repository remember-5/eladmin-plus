package me.zhengjie.modules.tool.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.tool.domain.MessageNotification;
import me.zhengjie.modules.tool.repository.MessageNotificationRepository;
import me.zhengjie.utils.RedisUtils;
import me.zhengjie.utils.SpringContextUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description
 * @Author fly
 * @Date 2021/4/15 15:13
 */
@ServerEndpoint("/ws/{sid}")
@Slf4j
@Component
public class WebSocketServers {

    private RedisUtils redisUtils;
    private MessageNotificationRepository messageNotificationRepository;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static CopyOnWriteArraySet<WebSocketServers> webSocketSets = new CopyOnWriteArraySet<WebSocketServers>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收sid
     */
    private String sid = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        //如果存在就先删除一个，防止重复推送消息
        webSocketSets.removeIf(webSocket -> webSocket.sid.equals(sid));
        webSocketSets.add(this);
        this.sid = sid;
        redisUtils = (RedisUtils) SpringContextUtil.getBean(RedisUtils.class);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSets.remove(this);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来" + sid + "的信息:" + message);
        if (StrUtil.isBlank(message)) {
            return;
        }
        // 发消息
        try {
            SocketMessage socketMessage = JSON.toJavaObject(JSON.parseObject(message), SocketMessage.class);
            if (socketMessage.getUsers().size() == 1 && "[ok]".equals(socketMessage.getUsers().toString())) {
                // 消息通知
                //System.out.println(socketMessage);
                MessageNotification msgn = JSON.toJavaObject(JSON.parseObject(socketMessage.getMsg()), MessageNotification.class);
                if (ObjectUtil.isNotNull(msgn)) {
                    messageNotificationRepository = (MessageNotificationRepository) SpringContextUtil.getBean(MessageNotificationRepository.class);
                    messageNotificationRepository.updateById(2, msgn.getId());
                }
            } else {
                // 消息发送
                sendInfo(socketMessage);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    private void sendMessage(String message) throws IOException {
        // TODO 做消息通知中心 哪些类型需要通知 通知级别
        // 生成消息参考 me.zhengjie.modules.tool.rest.TestMessageNotification
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 推送消息到前端
     */
    public static void sendMessage(String message, @PathParam("sid") String sid) throws IOException {
        log.info("推送消息到" + sid + "，推送内容:" + message);
        for (WebSocketServers item : webSocketSets) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if (sid == null) {
                    item.sendMessage(message);
                } else if (item.sid.equals(sid)) {
                    item.sendMessage(message);
                }
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 群发自定义消息
     */
    public void sendInfo(SocketMessage socketMsg) throws IOException {
        String message = socketMsg.getMsg();
        Set<String> users = socketMsg.getUsers();
        log.info("推送消息到" + users + "，推送内容:" + message);
        // TODO redis Key格式修改 存redis的内容修改(谁发的 发给谁 内容是什么)
        // TODO 发送目标不在线怎么处理
        // TODO 记录消息记录
        // 添加到Redis 发送给谁 然后取出一个发一个
        redisUtils.sSet(sid + ":SocketMessage", users.toArray());
        for (WebSocketServers item : webSocketSets) {
            try {
                // 这里可以设定只推送给这个sid的，为null则全部推送
                if (users.isEmpty() || ObjectUtil.isNull(users)) {
                    // 发送给全部的时候排除自己
                    if (sid.equals(item.sid)) {
                        continue;
                    }
                    item.sendMessage(message);
                } else {
                    for (String sid : users) {
                        if (sid.equals(item.sid)) {
                            item.sendMessage(message);
                            log.info("推送消息到" + sid + "，推送内容:" + message);
                            redisUtils.setRemove(this.sid + ":SocketMessage", sid);
                            long socketMessage = redisUtils.sGetSetSize(this.sid + ":SocketMessage");
                            // System.out.println("剩余需要消息发送目标个数为"+socketMessage);
                        }
                    }
                }
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebSocketServers that = (WebSocketServers) o;
        return Objects.equals(session, that.session) &&
                Objects.equals(sid, that.sid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, sid);
    }
}
