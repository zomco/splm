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
public class PtPlateStatusService {

  PtPlateStatusRepository ptPlateStatusRepository;
  RedisTemplate<String, String> redisTemplate;

  @Autowired
  public PtPlateStatusService(
      PtPlateStatusRepository ptPlateStatusRepository,
      RedisTemplate<String, String> redisTemplate
  ) {
    this.ptPlateStatusRepository = ptPlateStatusRepository;
    this.redisTemplate = redisTemplate;
  }

  public Optional<PtPlateStatus> findPtPlateLatestStatus(Long plateId) {
    return ptPlateStatusRepository.findOne(PtPlateStatusSpec.orderByTime(PtPlateStatusSpec.hasPlate(plateId)));
  }

  public Page<PtPlateStatus> findPtPlateStatuses(Long plateId, LocalDateTime start, LocalDateTime stop, Pageable pageable) {
    return ptPlateStatusRepository.findAll(PtPlateStatusSpec.hasPlate(plateId).and(PtPlateStatusSpec.hasTimeRange(start, stop)), pageable);
  }
}
