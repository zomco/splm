package com.yawsme.splm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yawsme.splm.common.dto.ptplate.PtBoardRspDTO;
import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.common.exception.WebsocketException;
import com.yawsme.splm.model.PtBoard;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WebSocketService {

  Map<Long, WebSocketTask> tasks = new HashMap<>();

  public void addWebsocketTask(PtBoard ptBoard, Session session) {
    WebSocketTask task = tasks.get(ptBoard.getId());
    if (task == null) {
      task = new WebSocketTask();
      task.sessions = new CopyOnWriteArraySet<>();
    }
    task.sessions.add(session);
    tasks.put(ptBoard.getId(), task);
  }

  public void removeWebsocketTask(PtBoard ptBoard, Session session) {
    WebSocketTask task = tasks.get(ptBoard.getId());
    if (task == null) {
      return;
    }
    task.sessions.remove(session);
    if (task.sessions.isEmpty()) {
      tasks.remove(ptBoard.getId());
    }
  }

  public void broadcast(Long boardId, String text) {
    WebSocketTask task = tasks.get(boardId);
    if (task == null) {
      return;
    }
    task.sessions.forEach(session -> {
      synchronized (session) {
        try {
          session.getBasicRemote().sendText(text);
        } catch (IOException e) {
          log.error(e.getLocalizedMessage());
        }
      }
    });
  }

  public String parseBid(Session session) throws WebsocketException {
    Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
    Matcher matcher = pat.matcher(session.getQueryString());
    Map<String, String> params = new HashMap<>();
    while (matcher.find()) {
      params.put(matcher.group(1), matcher.group(2));
    }
    String bid = params.get("bid");
    if (Strings.isEmpty(bid)) {
      try {
        session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "client is invalid"));
      } catch (IOException e) {
        log.error(e.getMessage());
      }
      throw new WebsocketException("client is invalid");
    }
    return bid;
  }

  public String stringifyMessage(PtBoardRspDTO ptBoardRspDTO) throws JsonProcessingException {
    ObjectMapper om = new ObjectMapper();
    return om.writeValueAsString(ptBoardRspDTO);
  }

  private static class WebSocketTask {
    Set<Session> sessions;
  }
}
