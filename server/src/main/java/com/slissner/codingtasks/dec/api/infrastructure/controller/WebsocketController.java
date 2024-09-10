package com.slissner.codingtasks.dec.api.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slissner.codingtasks.dec.api.domain.Product;
import com.slissner.codingtasks.dec.api.infrastructure.websocket.WebSocketHandler;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
@Slf4j
public class WebsocketController {

  private final WebSocketHandler webSocketHandler;
  private final ObjectMapper objectMapper;

  @SneakyThrows(IOException.class)
  @EventListener
  public void handleProductsHaveChanged(final Product.ProductsHaveChangedEvent event) {
    log.trace("Received ProductsHaveChangedEvent and will now publish to websocket subscribers.");

    final WebsocketMessageDto messageDto = new WebsocketMessageDto(UUID.randomUUID(), event.getLastChangedAt());

    final String messageJson = objectMapper.writeValueAsString(messageDto);

    webSocketHandler.sendMessageToClients(messageJson);

    log.debug("Sent a message to websocket subscribers. [message={}]", messageDto);
  }

  private record WebsocketMessageDto(UUID userId, Instant lastChangedAt) {}
}
