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
@Tag(name = "压板状态相关接口 ")
@RequestMapping("/api/status")
public class PtPlateStatusController {

  RedisTemplate<String, String> redisTemplate;
  PtPlateStatusService ptPlateStatusService;
  PtPlateService ptPlateService;

  @Autowired
  public PtPlateStatusController(
      RedisTemplate<String, String> redisTemplate,
      PtPlateStatusService ptPlateStatusService
  ) {
    this.redisTemplate = redisTemplate;
    this.ptPlateStatusService = ptPlateStatusService;
  }

  static public PtPlateStatusRspDTO ptPlateStatusMapper(PtPlateStatus ptPlateStatus) {
    PtPlateStatusRspDTO result = new PtPlateStatusRspDTO();
    result.setId(ptPlateStatus.getId());
    result.setPlateId(ptPlateStatus.getPlateId());
    result.setCreateTime(ptPlateStatus.getCreateTime());
    result.setActualValue(ptPlateStatus.getActualValue());

    return result;
  }

  @Operation(summary = "压板状态列表")
  @GetMapping("")
  public ResultMessage<Page<PtPlateStatusRspDTO>> retrievePtPlateStatuses(
      Long plateId,
      @RequestParam() @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @RequestParam() @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime stop,
      Pageable pageable
  ) {
    PtPlate ptPlate = ptPlateService.findPtPlate(plateId)
        .orElseThrow(() -> new ControllerException(ResultCode.PLATE_NOT_EXIST));

    Page<PtPlateStatus> pageRecords = ptPlateStatusService.findPtPlateStatuses(ptPlate.getId(), start, stop, pageable);
    List<PtPlateStatusRspDTO> listResults = pageRecords
        .stream().map(PtPlateStatusController::ptPlateStatusMapper).toList();

    Page<PtPlateStatusRspDTO> pageResults = new PageImpl<>(listResults, pageRecords.getPageable(), pageRecords.getTotalElements());
    return ResultUtil.data(pageResults);
  }

}
