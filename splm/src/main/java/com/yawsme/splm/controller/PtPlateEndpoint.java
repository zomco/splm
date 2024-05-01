package com.yawsme.splm.controller;

import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.common.exception.WebsocketException;
import com.yawsme.splm.model.PtBoard;
import com.yawsme.splm.model.PtPlate;
import com.yawsme.splm.service.PtBoardService;
import com.yawsme.splm.service.WebSocketService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

@Component
@ServerEndpoint("/ws/board/{boardId}")
@Slf4j
public class PtPlateEndpoint {

  private static WebSocketService websocketService;
  private static PtBoardService ptBoardService;
  Map<String, PtBoard> ptBoards = new HashMap<>();

  @Autowired
  public void setSensorTaskService(WebSocketService websocketService) { PtPlateEndpoint.websocketService = websocketService; }

  @Autowired
  public void setSensorService(PtBoardService ptBoardService) {
    PtPlateEndpoint.ptBoardService = ptBoardService;
  }

  @OnOpen
  public void onOpen(Session session, EndpointConfig config, @PathParam(value = "boardId") Long boardId) throws IOException, WebsocketException {
    String bid = websocketService.parseBid(session);
    log.info(String.format("websocket open %s %s", boardId, bid));
    Optional<PtBoard> ptBoard = ptBoardService.findPtBoard(boardId);
    if (ptBoard.isEmpty()) {
      try {
        session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "board not existed"));
      } catch (IOException e) {
        log.error(e.getMessage());
      }
      return;
    }
    this.ptBoards.put(bid, ptBoard.get());
    websocketService.addWebsocketTask(ptBoard.get(), session);
    // 返回铁板状态
    List<PtPlateRspDTO> ptPlateRspDTOS = new ArrayList<>();
    String text = websocketService.stringifyPtPlates(ptPlateRspDTOS);
    session.getBasicRemote().sendText(text);
  }

  @OnClose
  public void onClose(Session session, CloseReason reason) throws WebsocketException {
    String bid = websocketService.parseBid(session);
    //连接关闭
    log.info(String.format("websocket close %s %s", bid, reason.getCloseCode()));
    PtBoard ptBoard = this.ptBoards.get(bid);
    if (ptBoard == null) {
      return;
    }

    websocketService.removeWebsocketTask(ptBoard, session);
  }

  @OnMessage
  public void onMessage(Session session, String message) {
    //接收文本信息
    log.info(String.format("websocket receive string message %s", message));
  }

  @OnMessage
  public void onMessage(Session session, PongMessage message) {
    //接收pong信息
    log.info(String.format("websocket receive pong message %s", new String(message.getApplicationData().array())));
  }

  @OnMessage
  public void onMessage(Session session, ByteBuffer message) {
    //接收二进制信息，也可以用byte[]接收
    log.info(String.format("websocket receive buffer message %s", new String(message.array())));
  }

  @OnError
  public void onError(Session session, Throwable e) throws WebsocketException {
    String bid = websocketService.parseBid(session);
    //异常处理
    log.info(String.format("websocket error %s %s", bid, e.getMessage()));
  }

}
