package com.yawsme.splm.service;


import com.digitalpetri.modbus.codec.Modbus;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.yawsme.splm.common.enums.PtPlateStatusValue;
import com.yawsme.splm.model.*;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class ModbusService {

  PtPlateStatusService ptPlateStatusService;

  @Autowired
  public ModbusService(
      PtPlateStatusService ptPlateStatusService
  ) {
    this.ptPlateStatusService = ptPlateStatusService;
  }

  public void modbusRequest(List<PtPlate> ptPlates, String ip) {
    ModbusTcpMasterConfig config = new ModbusTcpMasterConfig
        .Builder(ip)
        .setPort(502)
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

      for (int i = 0; i < row; i++) {
        int value = (Byte.toUnsignedInt(bytes[i]) << 8) + Byte.toUnsignedInt(bytes[i + 1]);
        boolean valid = (value & (1 << 15)) > 0 ;
        log.info("Response Row: {} {} {}", value, i, valid);
        for (int j = 0; j < column; j++) {
          PtPlate ptPlate = ptPlates.get(i * column + j);
          PtPlateStatusValue actualValue = (value & (1 << j)) > 0 ? PtPlateStatusValue.ON : PtPlateStatusValue.OFF;
          log.info("Response Column: {} {} {}", ptPlate.getId(), j, actualValue);
//          Optional<PtPlateStatus> ptPlateStatus_ = ptPlateStatusService.findPtPlateLatestStatus(ptPlate.getId());
//          if (ptPlateStatus_.isPresent() && ptPlateStatus_.get().getActualValue() != actualValue) {
//
//          }
          PtPlateStatus ptPlateStatus = new PtPlateStatus();
          ptPlateStatus.setPlateId(ptPlate.getId());
          ptPlateStatus.setActualValue(actualValue);
          ptPlateStatusService.savePtPlateStatus(ptPlateStatus);
        }
      }

      ReferenceCountUtil.release(response);
      master.disconnect();
    });

  }

}
