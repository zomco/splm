package com.yawsme.splm.service;


import com.yawsme.splm.model.PtPlate;
import com.yawsme.splm.model.PtPlateRepository;
import com.yawsme.splm.model.PtPlateSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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

  public List<PtPlate> findPtPlates(Long boardId) {
    return ptPlateRepository.findAll(PtPlateSpec.orderByCoordinate(PtPlateSpec.hasBoard(boardId)));
  }

  public Optional<PtPlate> findPtPlate(Long id) {
    return ptPlateRepository.findOne(PtPlateSpec.is(id));
  }

  public void savePtPlate(PtPlate ptPlate) {
    ptPlateRepository.save(ptPlate);
  }
}
