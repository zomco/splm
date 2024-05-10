package com.yawsme.splm.service;


import com.yawsme.splm.common.enums.PtPlateStatusValue;
import com.yawsme.splm.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

  public PtPlateStatusValue findPtPlateLatestStatus(Long plateId) {
    List<PtPlateStatus> statuses = ptPlateStatusRepository.findAll(PtPlateStatusSpec.orderByTime(PtPlateStatusSpec.hasPlate(plateId)));
    if (statuses.isEmpty()) return PtPlateStatusValue.UNKNOWN;
    return statuses.get(0).getActualValue();
  }

  public Page<PtPlateStatus> findPtPlateStatuses(Long plateId, LocalDateTime start, LocalDateTime stop, Pageable pageable) {
    return ptPlateStatusRepository.findAll(PtPlateStatusSpec.orderByTime(PtPlateStatusSpec.hasPlate(plateId)
        .and(PtPlateStatusSpec.beforeTheTime(start))
        .and(PtPlateStatusSpec.afterTheTime(stop))), pageable);
  }

  public void savePtPlateStatus(PtPlateStatus ptPlateStatus) {
    ptPlateStatusRepository.save(ptPlateStatus);
  }
}
