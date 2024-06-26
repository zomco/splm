package com.yawsme.splm.controller;

import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.common.dto.ptplate.PtPlateStatusRspDTO;
import com.yawsme.splm.common.dto.ptplate.PtPlateUpdateReqDTO;
import com.yawsme.splm.common.enums.ResultCode;
import com.yawsme.splm.common.enums.ResultMessage;
import com.yawsme.splm.common.enums.ResultUtil;
import com.yawsme.splm.common.exception.ControllerException;
import com.yawsme.splm.model.PtPlate;
import com.yawsme.splm.model.PtPlateStatus;
import com.yawsme.splm.service.PtPlateService;
import com.yawsme.splm.service.PtPlateStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "压板相关接口 ")
@RequestMapping("/api/plate")
public class PtPlateController {

  RedisTemplate<String, String> redisTemplate;
  PtPlateService ptPlateService;
  PtPlateStatusService ptPlateStatusService;

  @Autowired
  public PtPlateController(
      RedisTemplate<String, String> redisTemplate,
      PtPlateService ptPlateService,
      PtPlateStatusService ptPlateStatusService
  ) {
    this.redisTemplate = redisTemplate;
    this.ptPlateService = ptPlateService;
    this.ptPlateStatusService = ptPlateStatusService;
  }

  static public PtPlateRspDTO ptPlateMapper(PtPlate ptPlate) {
    PtPlateRspDTO result = new PtPlateRspDTO();
    result.setId(ptPlate.getId());
    result.setBoardId(ptPlate.getBoardId());
    result.setName(ptPlate.getName());
    result.setName1(ptPlate.getName1());
    result.setName2(ptPlate.getName2());
    result.setEnabled(ptPlate.getEnabled());
    result.setCx(ptPlate.getCx());
    result.setCy(ptPlate.getCy());

    return result;
  }

  @Operation(summary = "压板列表")
  @GetMapping("")
  public ResultMessage<List<PtPlateRspDTO>> retrievePtPlates(
      Long boardId
  ) {
    List<PtPlate> pageRecords = ptPlateService.findPtPlates(boardId);
    List<PtPlateRspDTO> listResults = pageRecords
        .stream().map((result) -> {
          PtPlateRspDTO ptPlateRspDTO = PtPlateController.ptPlateMapper(result);
          ptPlateRspDTO.setStatus(ptPlateStatusService.findPtPlateLatestStatus(result.getId()));
          return ptPlateRspDTO;
        }).toList();

    return ResultUtil.data(listResults);
  }

  @Operation(summary = "压板详情")
  @GetMapping("/{id}")
  public ResultMessage<PtPlateRspDTO> retrievePtPlate(
      @PathVariable Long id,
      @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam("stop") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime stop,
      Pageable pageable
  ) {
    PtPlate ptPlate = ptPlateService.findPtPlate(id)
        .orElseThrow(() -> new ControllerException(ResultCode.PLATE_NOT_EXIST));
    PtPlateRspDTO ptPlateRspDTO = PtPlateController.ptPlateMapper(ptPlate);

    // 最新状态
    ptPlateRspDTO.setStatus(ptPlateStatusService.findPtPlateLatestStatus(ptPlate.getId()));
    // 状态列表
    Page<PtPlateStatus> pageRecords = ptPlateStatusService.findPtPlateStatuses(id, start, stop, pageable);
    List<PtPlateStatusRspDTO> listResults = pageRecords
        .stream().map(PtPlateStatusController::ptPlateStatusMapper).toList();

    Page<PtPlateStatusRspDTO> pageResults = new PageImpl<>(listResults, pageRecords.getPageable(), pageRecords.getTotalElements());
    ptPlateRspDTO.setStatuses(pageResults);
    return ResultUtil.data(ptPlateRspDTO);
  }

  @Operation(summary = "更新压板")
  @PostMapping("/{id}")
  public ResultMessage<PtPlateRspDTO> updatePtPlate(
      @PathVariable Long id,
      @Valid @RequestBody PtPlateUpdateReqDTO sensorUpdateReqDTO
  ) {
    PtPlate ptPlate = ptPlateService.findPtPlate(id)
        .orElseThrow(() -> new ControllerException(ResultCode.PLATE_NOT_EXIST));
    if (sensorUpdateReqDTO.getName() != null) {
      ptPlate.setName(sensorUpdateReqDTO.getName());
    }
    if (sensorUpdateReqDTO.getName1() != null) {
      ptPlate.setName1(sensorUpdateReqDTO.getName1());
    }
    if (sensorUpdateReqDTO.getName2() != null) {
      ptPlate.setName2(sensorUpdateReqDTO.getName2());
    }
    if (sensorUpdateReqDTO.getEnabled() != null) {
      ptPlate.setEnabled(sensorUpdateReqDTO.getEnabled());
    }
    ptPlateService.savePtPlate(ptPlate);

    return ResultUtil.data(PtPlateController.ptPlateMapper(ptPlate));
  }

}
