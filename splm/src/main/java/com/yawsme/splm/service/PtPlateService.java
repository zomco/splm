package com.yawsme.splm.service;


import com.yawsme.splm.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
public class PtPlateService {

  PtPlateRepository ptPlateRepository;
  RedisTemplate<String, String> redisTemplate;

  @Autowired
  public PtPlateService(
      PtPlateRepository ptPlateRepository,
      RedisTemplate<String, String> redisTemplate
  ) {
    this.ptPlateRepository = ptPlateRepository;
    this.redisTemplate = redisTemplate;
  }

  public Page<PtPlate> findPtPlates(Long boardId, Pageable pageable) {
    return ptPlateRepository.findAll(PtPlateSpec.hasBoard(boardId), pageable);
  }

  public Optional<PtPlate> findPtPlate(Long id) {
    return ptPlateRepository.findOne(PtPlateSpec.is(id));
  }

  public PtPlate savePtPlate(PtPlate ptPlate) {
    return ptPlateRepository.save(ptPlate);
  }
}
