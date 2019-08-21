package com.gaoice.system.common.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint("/websocket")
public class WebSocketServer {

    private Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    private static AtomicInteger atomicCount = new AtomicInteger(0);
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        atomicCount.incrementAndGet();
        LOGGER.info("WebSocket 连接数：" + atomicCount.get());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        atomicCount.decrementAndGet();
        LOGGER.info("WebSocket 连接数：" + atomicCount.get());
    }

    /**
     * 简单的返回消息
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        LOGGER.info(message);
        for (WebSocketServer webSocket : webSocketSet) {
            try {
                webSocket.sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        LOGGER.error(error.getMessage());
    }

    public void sendText(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static int getAtomicCount() {
        return atomicCount.get();
    }

}
