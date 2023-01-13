package com.template.config.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.*;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket服务
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
//@ServerEndpoint("/testServer/{userId}")
//@Component
@Slf4j
public class WebSocketServer {

    /**
     * PING
     */
    private static final String PING = "ping";

    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
     */
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static final ConcurrentHashMap<String, WebSocketServer> WEB_SOCKET_MAP = new ConcurrentHashMap<>();

    @Getter
    private Session session;

    /**
     * 根据客户端ID获取socket
     *
     * @param clientId 客户端ID
     * @return WebSocketServer
     */
    public static WebSocketServer getWebSocketServer(String clientId) {
        return WEB_SOCKET_MAP.get(clientId);
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        synchronized (this) {
            log.info("sessionId===={}", session.getId());
            if (!WEB_SOCKET_MAP.containsKey(session.getId())) {
                this.session = session;
                WEB_SOCKET_MAP.put(session.getId(), this);
                addOnlineCount();
                log.info("客户端连接:" + session.getId() + ",当前在线人数为:" + getOnlineCount());
                sendMessage("Low Code服务连接成功！");
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        synchronized (this) {
            if (WEB_SOCKET_MAP.containsKey(this.getSession().getId())) {
                WEB_SOCKET_MAP.remove(this.getSession().getId());
                //从set中删除
                subOnlineCount();
            }
            log.info("客户端退出:" + this.getSession().getId() + ",当前在线客户端为:" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.debug("收到客户端消息:" + this.getSession().getId() + ",报文:" + message);
        WebSocketServer ws = WEB_SOCKET_MAP.get(this.getSession().getId());
        if (ws != null) {
            if (StringUtils.isNotBlank(message)) {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                String value = jsonObject.getString("eventType");
                if (PING.equals(value)) {
                    ws.sendMessage("pang");
                } else {
                    // 业务逻辑处理
                }
            } else {
                log.error("onMessage 空消息");
            }
        } else {
            this.onOpen(this.session);
            log.error("onMessage 客户端会话不存在 clientId:{}，重新链接", this.session.getId());
        }
    }

    /**
     * 打印错误信息
     */
    @OnError
    public void onError(Throwable error) {
        log.error("错误:" + this.session.getId() + ",原因:" + error.getMessage());
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            log.debug("主动推送: {}", message);
        } catch (IOException e) {
            log.error("服务器主动推送失败", e);
        }
    }

    public int getOnlineCount() {
        return ONLINE_COUNT.get();
    }

    public void addOnlineCount() {
        WebSocketServer.ONLINE_COUNT.incrementAndGet();
    }

    public void subOnlineCount() {
        ONLINE_COUNT.decrementAndGet();
    }

}