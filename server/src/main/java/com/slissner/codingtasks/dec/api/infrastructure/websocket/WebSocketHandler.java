package com.slissner.codingtasks.dec.api.infrastructure.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

  private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

  @Override
  public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
    sessions.add(session); // Add the session to the set
    log.debug("New WebSocket connection established. [session.id={}]", session.getId());
  }

  @Override
  public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status)
      throws Exception {
    super.afterConnectionClosed(session, status);
    sessions.remove(session);
    log.debug("WebSocket connection closed. [session.id={}]", session.getId());
  }

  public void sendMessageToClients(final String message) throws IOException {
    for (final WebSocketSession session : sessions) {
      if (session.isOpen()) {
        session.sendMessage(new TextMessage(message));
      }
    }
  }
}
