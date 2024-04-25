package com.yawsme.splm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yawsme.splm.common.dto.ptplate.PtBoardRspDTO;
import com.yawsme.splm.common.exception.WebsocketException;
import com.yawsme.splm.model.PtBoard;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class WebSocketService {

  private final TaskScheduler taskScheduler;
  Map<Long, WebSocketTask> tasks = new HashMap<>();

  @Autowired
  public WebSocketService(
      TaskScheduler taskScheduler
  ) {
    this.taskScheduler = taskScheduler;
  }

  public void addWebsocketTask(PtBoard ptBoard, Session session) {
    WebSocketTask task = tasks.get(ptBoard.getId());
    if (task == null) {
      task = new WebSocketTask();
//      Runnable run = () -> mqttSenderInterface.sendToMqtt(String.format("/care/sub/%s/cmd/full", sensor.getCode()), "654321");
//      task.schedule = taskScheduler.scheduleAtFixedRate(run, 20000);
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
      task.schedule.cancel(true);
//      mqttSenderInterface.sendToMqtt(String.format("/care/sub/%s/cmd/part", sensor.getCode()), "654321");
      tasks.remove(ptBoard.getId());
    }
  }

  public void broadcast(PtBoard ptBoard, String text) {
    WebSocketTask task = tasks.get(ptBoard.getId());
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

  public String parseClientId(Session session) throws WebsocketException {
    Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");
    Matcher matcher = pat.matcher(session.getQueryString());
    Map<String,String> params = new HashMap<>();
    while (matcher.find()) {
      params.put(matcher.group(1), matcher.group(2));
    }
    String clientId = params.get("clientId");
    if (Strings.isEmpty(clientId)) {
      try {
        session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "client is invalid"));
      } catch (IOException e) {
        log.error(e.getMessage());
      }
      throw new WebsocketException("client is invalid");
    }
    return clientId;
  }


  public String stringifyPtBoard(PtBoard ptBoard) throws JsonProcessingException {
    PtBoardRspDTO ptBoardRspDTO = new PtBoardRspDTO();
    ptBoardRspDTO.setId(ptBoardRspDTO.getId());
    ptBoardRspDTO.setName(ptBoardRspDTO.getName());
    ptBoardRspDTO.setEnabled(ptBoardRspDTO.getEnabled());
    ObjectMapper om = new ObjectMapper();
    return om.writeValueAsString(ptBoardRspDTO);
  }

  private static class WebSocketTask {
    Set<Session> sessions;
    ScheduledFuture<?> schedule;
  }
}
