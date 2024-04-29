package com.yawsme.splm.controller;

import com.yawsme.splm.common.dto.ptplate.PtBoardRspDTO;
import com.yawsme.splm.common.dto.ptplate.PtPlateRspDTO;
import com.yawsme.splm.common.enums.ResultCode;
import com.yawsme.splm.common.enums.ResultMessage;
import com.yawsme.splm.common.enums.ResultUtil;
import com.yawsme.splm.common.exception.ControllerException;
import com.yawsme.splm.model.PtBoard;
import com.yawsme.splm.model.PtPlate;
import com.yawsme.splm.model.PtPlateStatus;
import com.yawsme.splm.service.PtBoardService;
import com.yawsme.splm.service.PtPlateService;
import com.yawsme.splm.service.PtPlateStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "铁板相关接口 ")
@RequestMapping("/api/board")
public class PtBoardController {

  RedisTemplate<String, String> redisTemplate;
  PtPlateService ptPlateService;
  PtBoardService ptBoardService;
  PtPlateStatusService ptPlateStatusService;

  @Autowired
  public PtBoardController(
      RedisTemplate<String, String> redisTemplate,
      PtBoardService ptBoardService,
      PtPlateService ptPlateService,
      PtPlateStatusService ptPlateStatusService
  ) {
    this.redisTemplate = redisTemplate;
    this.ptBoardService = ptBoardService;
    this.ptPlateService = ptPlateService;
    this.ptPlateStatusService = ptPlateStatusService;
  }

  static public PtBoardRspDTO ptBoardMapper(PtBoard ptBoard) {
    PtBoardRspDTO result = new PtBoardRspDTO();
    result.setId(ptBoard.getId());
    result.setName(ptBoard.getName());
    result.setEnabled(ptBoard.getEnabled());

    return result;
  }

  @Operation(summary = "铁板列表")
  @GetMapping("")
  public ResultMessage<Page<PtBoardRspDTO>> retrievePtBoards(
      Pageable pageable
  ) {
    Page<PtBoard> pageRecords = ptBoardService.findPtBoards(pageable);
    List<PtBoardRspDTO> listResults = pageRecords
        .stream().map(PtBoardController::ptBoardMapper).toList();

    Page<PtBoardRspDTO> pageResults = new PageImpl<>(listResults, pageRecords.getPageable(), pageRecords.getTotalElements());
    return ResultUtil.data(pageResults);
  }

  @Operation(summary = "铁板详情")
  @GetMapping("/{id}")
  public ResultMessage<PtBoardRspDTO> retrievePtBoard(
      @PathVariable Long id
  ) {
    PtBoard ptBoard = ptBoardService.findPtBoard(id)
        .orElseThrow(() -> new ControllerException(ResultCode.BOARD_NOT_EXIST));
    PtBoardRspDTO ptBoardRspDTO = ptBoardMapper(ptBoard);

    // 压板列表
    List<PtPlate> ptPlates = ptPlateService.findPtPlates(id);
    List<PtPlateRspDTO> listResults = ptPlates.stream().map((result) -> {
      PtPlateRspDTO ptPlateRspDTO = PtPlateController.ptPlateMapper(result);

      // 最新状态
      Optional<PtPlateStatus> status = ptPlateStatusService.findPtPlateLatestStatus(result.getId());
      status.ifPresent(ptPlateStatus -> ptPlateRspDTO.setStatus(ptPlateStatus.getActualValue()));

      return ptPlateRspDTO;
    }).toList();

    ptBoardRspDTO.setPlates(listResults);
    return ResultUtil.data(ptBoardRspDTO);
  }

  @Operation(summary = "铁板巡检")
  @GetMapping("/{id}/inspect")
  public ResultMessage<PtBoardRspDTO> inspectPtBoard(
      @PathVariable Long id
  ) {
    PtBoard ptBoard = ptBoardService.findPtBoard(id)
        .orElseThrow(() -> new ControllerException(ResultCode.BOARD_NOT_EXIST));


    return ResultUtil.data(ptBoardMapper(ptBoard));
  }


}
