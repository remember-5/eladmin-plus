package me.zhengjie.modules.tool.websocket;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.Socket;
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
    private String sid="";
    /**
     * 连接建立成功调用的方法
     * */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        //如果存在就先删除一个，防止重复推送消息
        webSocketSets.removeIf(webSocket -> webSocket.sid.equals(sid));
        webSocketSets.add(this);
        this.sid=sid;
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
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来"+sid+"的信息:"+message);
        if (StrUtil.isBlank(message)) {
            return;
        }
        // 发消息
            try {
                JSONObject messageObject = JSONObject.parseObject(message);
                SocketMessage socketMessage = JSON.toJavaObject(messageObject,SocketMessage.class);
                sendInfo(socketMessage);
            } catch (IOException e) {
                log.error(e.getMessage(),e);
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
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(SocketMessage socketMsg) throws IOException {
        String message = socketMsg.getMsg();
        Set<String> users = socketMsg.getUsers();
        log.info("推送消息到"+socketMsg.getUsers()+"，推送内容:"+message);
        for (WebSocketServers item : webSocketSets) {
            try {
                // 这里可以设定只推送给这个sid的，为null则全部推送
                if(users.isEmpty() || ObjectUtil.isNull(users)) {
                    item.sendMessage(message);
                }else {
                    for (String sid: users) {
                        if (sid.equals(item.sid)){
                            item.sendMessage(message);
                            log.info("推送消息到"+sid+"，推送内容:"+message);
                        }
                    }
                }
            } catch (IOException ignored) { }
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
