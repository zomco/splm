package com.yawsme.splm.controller;

import com.yawsme.splm.common.dto.ptplate.PtPlateStatusRspDTO;
import com.yawsme.splm.common.dto.ptplate.PtPlateUpdateReqDTO;
import com.yawsme.splm.common.enums.ResultCode;
import com.yawsme.splm.common.enums.ResultMessage;
import com.yawsme.splm.common.enums.ResultUtil;
import com.yawsme.splm.common.exception.ControllerException;
import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.model.PtPlate;
import com.yawsme.splm.model.PtPlateStatus;
import com.yawsme.splm.service.PtPlateService;
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

  @Autowired
  public PtPlateController(
      RedisTemplate<String, String> redisTemplate,
      PtPlateService ptPlateService
  ) {
    this.redisTemplate = redisTemplate;
    this.ptPlateService = ptPlateService;
  }

  public PtPlateRspDTO ptPlateMapper(PtPlate ptPlate) {
    PtPlateRspDTO result = new PtPlateRspDTO();
    result.setId(ptPlate.getId());
    result.setName(ptPlate.getName());
    result.setEnabled(ptPlate.getEnabled());
    result.setRow(ptPlate.getRow());
    result.setColumn(ptPlate.getColumn());

    return result;
  }

  @Operation(summary = "压板列表")
  @GetMapping("")
  public ResultMessage<Page<PtPlateRspDTO>> retrievePtPlates(
      Long boardId,
      Pageable pageable
  ) {
    Page<PtPlate> pageRecords = ptPlateService.findPtPlates(boardId, pageable);
    List<PtPlateRspDTO> listResults = pageRecords
        .stream().map(this::ptPlateMapper).toList();

    Page<PtPlateRspDTO> pageResults = new PageImpl<>(listResults, pageRecords.getPageable(), pageRecords.getTotalElements());
    return ResultUtil.data(pageResults);
  }

  @Operation(summary = "压板详情")
  @GetMapping("/{id}")
  public ResultMessage<PtPlateRspDTO> retrievePtPlate(
      @PathVariable Long id
  ) {
    PtPlate ptPlate = ptPlateService.findPtPlate(id)
        .orElseThrow(() -> new ControllerException(ResultCode.PLATE_NOT_EXIST));

    return ResultUtil.data(ptPlateMapper(ptPlate));
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
    if (sensorUpdateReqDTO.getEnabled() != null) {
      ptPlate.setEnabled(sensorUpdateReqDTO.getEnabled());
    }
    ptPlateService.savePtPlate(ptPlate);

    return ResultUtil.data(ptPlateMapper(ptPlate));
  }

}
