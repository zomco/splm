package com.yawsme.splm.service;


import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.codec.Modbus;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.controller.PtPlateController;
import com.yawsme.splm.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Slf4j
@Service
public class PtBoardService {

  private final ModbusService modbusService;
  PtBoardRepository ptBoardRepository;
  PtPlateService ptPlateService;

  @Autowired
  public PtBoardService(
      PtBoardRepository ptBoardRepository,
      ModbusService modbusService,
      PtPlateService ptPlateService
  ) {
    this.ptBoardRepository = ptBoardRepository;
    this.modbusService = modbusService;
    this.ptPlateService = ptPlateService;
  }

  public Optional<PtBoard> findPtBoard(Long boardId) {
    return ptBoardRepository.findOne(PtBoardSpec.is(boardId));
  }

  public Page<PtBoard> findPtBoards(Pageable pageable) {
    return ptBoardRepository.findAll(pageable);
  }

  public void inspectPtBoard(PtBoard ptBoard) {
    modbusService.modbusRequest(ptBoard.getId(), ptBoard.getIp(), ptBoard.getPort());
  }

  @Scheduled(fixedDelay = 1000, initialDelay = 1000)
  public void pollPtBoards() {
    List<PtBoard> ptBoards = ptBoardRepository.findAll(PtBoardSpec.hasEnabled(true));
    ptBoards.forEach(ptBoard -> modbusService.modbusRequest(ptBoard.getId(), ptBoard.getIp(), ptBoard.getPort()));
  }
}
