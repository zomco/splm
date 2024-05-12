package com.yawsme.splm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.common.enums.PtPlateStatusValue;
import com.yawsme.splm.controller.PtPlateController;
import com.yawsme.splm.model.PtPlate;
import com.yawsme.splm.model.PtPlateStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;


@Slf4j
@Service
public class ModbusService {

  private static final int[] CRC_TABLE = {
      0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
      0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
      0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
      0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
      0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
      0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
      0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
      0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
      0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
      0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
      0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
      0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
      0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
      0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
      0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
      0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
      0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
      0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
      0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
      0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
      0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
      0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
      0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
      0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
      0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
      0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
      0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
      0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
      0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
      0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
      0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
      0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040
  };
  PtPlateStatusService ptPlateStatusService;
  WebSocketService webSocketService;
  PtPlateService ptPlateService;

  @Autowired
  public ModbusService(
      PtPlateStatusService ptPlateStatusService,
      WebSocketService webSocketService,
      PtPlateService ptPlateService
  ) {
    this.ptPlateStatusService = ptPlateStatusService;
    this.webSocketService = webSocketService;
    this.ptPlateService = ptPlateService;
  }

  public static int calculateCRC(byte[] data) {
    int crc = 0xFFFF;

    for (byte b : data) {
      crc = (crc >>> 8) ^ CRC_TABLE[(crc ^ b) & 0xFF];
    }

    return crc;
  }

  public static byte[] calculateCRCBytes(byte[] data) {
    int crc = calculateCRC(data);
    return new byte[]{(byte) (crc & 0xFF), (byte) ((crc >> 8) & 0xFF)};
  }

  public Boolean validateMessage(byte[] bytes) {
    // 入参无效
    if (bytes == null) return false;
    // 长度无效
    if (bytes.length <= 4) return false;
    // 站号无效
    if (bytes[0] != 0x01) return false;
    // 功能无效
    if (bytes[1] != 0x03) return false;
    int len = bytes[2];
    byte[] data = Arrays.copyOfRange(bytes, 0, 3 + len);
    byte[] crc = calculateCRCBytes(data);
    // 校验无效
    return bytes[3 + len] == crc[0] && bytes[4 + len] == crc[1];
  }

  public String encodeUsingHexFormat(byte[] bytes) {
    HexFormat hexFormat = HexFormat.of();
    return hexFormat.formatHex(bytes);
  }

  public byte[] decodeUsingHexFormat(String hexString) {
    HexFormat hexFormat = HexFormat.of();
    return hexFormat.parseHex(hexString);
  }

  public void parseMessage(Long boardId, byte[] bytes) {
    // 获取铁板数据
    byte[] dataBytes = Arrays.copyOfRange(bytes, 3, 3 + bytes[2]);
    int column = 9;
    int row = 7;
    // 查出铁板上所有压板
    List<PtPlate> ptPlates = ptPlateService.findPtPlates(boardId);
    List<PtPlateRspDTO> ptPlateRspDTOS = new ArrayList<>();
    // 解析行数据
    for (int i = 0; i < row; i++) {
      int value = (Byte.toUnsignedInt(dataBytes[2 * i]) << 8) + Byte.toUnsignedInt(dataBytes[2 * i + 1]);
      boolean invalid = (value & (1 << 15)) > 0;
      // 跳过无效行数据
      if (invalid) continue;
      log.info("update row {} value {}", i, Integer.toBinaryString(value));
      // 解析列数据
      for (int j = 0; j < column; j++) {
        PtPlate ptPlate = ptPlates.get(i * column + j);
        // 只更新使能的压板
        if (!ptPlate.getEnabled()) continue;
        // 获取实际数值
        PtPlateStatusValue actualValue = (value & (1 << j)) > 0 ? PtPlateStatusValue.ON : PtPlateStatusValue.OFF;
        PtPlateStatusValue ptPlateStatusValue = ptPlateStatusService.findPtPlateLatestStatus(ptPlate.getId());
        // 只更新状态变化的压板
        if (ptPlateStatusValue == actualValue) continue;
        log.info("update item {} for plate {} from {} to {}", j, ptPlate.getId(), ptPlateStatusValue, actualValue);
        // 保存新状态到数据库
        PtPlateStatus ptPlateStatus = new PtPlateStatus();
        ptPlateStatus.setPlateId(ptPlate.getId());
        ptPlateStatus.setActualValue(actualValue);
        ptPlateStatusService.savePtPlateStatus(ptPlateStatus);
        // 保存新状态到返回到前端的列表
        PtPlateRspDTO ptPlateRspDTO = PtPlateController.ptPlateMapper(ptPlate);
        ptPlateRspDTO.setStatus(actualValue);
        ptPlateRspDTOS.add(ptPlateRspDTO);
      }
    }
    // 将有更新的压板状态推送到前端
    try {
      String text = webSocketService.stringifyPtPlates(ptPlateRspDTOS);
      webSocketService.broadcast(boardId, text);
    } catch (JsonProcessingException e) {
      log.error("failed to serialize broadcast message");
    }
  }

  public void shortConnect(Long boardId, String ip, Integer port) {
    try (Socket client = new Socket(ip, port)) {
      client.setSoTimeout(3000);
      // 建立连接
      OutputStream out = client.getOutputStream();
      InputStream in = client.getInputStream();
      // 发送指令
      byte[] command = {0x01, 0x03, 0x01, 0x00, 0x00, 0x10, 0x45, (byte) 0xfa};
      out.write(command);
      out.flush();
      // 接收响应
      byte[] buffer = new byte[1024];
      // 解析响应
      int bytesRead = in.read(buffer);
      byte[] response = null;
      if (bytesRead > 0) {
        response = new byte[bytesRead];
        System.arraycopy(buffer, 0, response, 0, bytesRead);
      }
      if (response == null || !validateMessage(response)) {
        log.info("receive invalid response: {}", encodeUsingHexFormat(response));
        return;
      }
      parseMessage(boardId, response);
    } catch (IOException e) {
      log.error(e.getMessage());
      // 发生错误，退出
    }
  }

  public void persistentConnect(Long boardId, String ip, Integer port) throws IOException {
    Socket client = new Socket(ip, port);
    // 建立连接
    InputStream in = client.getInputStream();
    // 接收响应
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
      byte[] response = new byte[bytesRead];
      System.arraycopy(buffer, 0, response, 0, bytesRead);
      if (!validateMessage(response)) {
        log.warn("receive invalid response: {}", encodeUsingHexFormat(response));
        continue;
      }
      parseMessage(boardId, response);
      buffer = new byte[1024]; // 清空缓冲区
    }
    client.close();
    log.info("close client: {}:{}", ip, port);
  }
}
