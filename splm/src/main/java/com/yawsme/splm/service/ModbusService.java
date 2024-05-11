package com.yawsme.splm.service;


import com.digitalpetri.modbus.codec.Modbus;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.common.enums.PtPlateStatusValue;
import com.yawsme.splm.controller.PtPlateController;
import com.yawsme.splm.model.*;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class ModbusService {

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

  public void modbusRequest(Long boardId, String ip, Integer port) {
    ModbusTcpMasterConfig config = new ModbusTcpMasterConfig
        .Builder(ip)
        .setPort(port)
        .build();
    ModbusTcpMaster master = new ModbusTcpMaster(config);
    master.connect();

    int column = 9;
    int row = 7;
    CompletableFuture<ReadHoldingRegistersResponse> future =
        master.sendRequest(new ReadHoldingRegistersRequest(100, row), 1);

    future.thenAccept(response -> {
      log.info("Response: {}", ByteBufUtil.hexDump(response.getRegisters()));
      byte[] bytes = ByteBufUtil.getBytes(response.getRegisters());

      // 查出铁板上所有压板
      List<PtPlate> ptPlates = ptPlateService.findPtPlates(boardId);
      List<PtPlateRspDTO> ptPlateRspDTOS = new ArrayList<>();
      // 解析行数据
      for (int i = 0; i < row; i++) {
        int value = (Byte.toUnsignedInt(bytes[2*i]) << 8) + Byte.toUnsignedInt(bytes[2*i + 1]);
        boolean invalid = (value & (1 << 15)) > 0 ;
        log.info("Response Row {} Value {}, invalid {}", i, value, invalid);
        // 跳过无效行数据
        if (invalid) continue;
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
          log.info("Response Row {} Column {} Plate {}, ActualValue {}, CurrentValue {}", i, j, ptPlate.getId(), actualValue, ptPlateStatusValue);
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
        log.error(e.getMessage());
      }

      ReferenceCountUtil.release(response);
      master.disconnect();
    });

  }

}
