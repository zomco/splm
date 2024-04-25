package com.yawsme.splm.service;


import com.digitalpetri.modbus.codec.Modbus;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.yawsme.splm.model.PtBoard;
import com.yawsme.splm.model.PtBoardRepository;
import com.yawsme.splm.model.PtBoardSpec;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class ModbusService {

  PtBoardRepository ptBoardRepository;

  @Autowired
  public ModbusService(
      PtBoardRepository ptBoardRepository
  ) {
    this.ptBoardRepository = ptBoardRepository;
  }

  public void modbusRequest(String ip, Integer port) {
    ModbusTcpMasterConfig config = new ModbusTcpMasterConfig
        .Builder(ip)
        .setPort(port)
        .build();
    ModbusTcpMaster master = new ModbusTcpMaster(config);
    master.connect();

    CompletableFuture<ReadHoldingRegistersResponse> future =
        master.sendRequest(new ReadHoldingRegistersRequest(0, 10), 0);

    future.thenAccept(response -> {
      log.info("Response: {}", ByteBufUtil.hexDump(response.getRegisters()));

      ReferenceCountUtil.release(response);
      master.disconnect();
    });

  }

}
