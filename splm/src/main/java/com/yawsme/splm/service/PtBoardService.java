package com.yawsme.splm.service;

import com.yawsme.splm.model.PtBoard;
import com.yawsme.splm.model.PtBoardRepository;
import com.yawsme.splm.model.PtBoardSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


@Slf4j
@Service
public class PtBoardService {

  private static final int THREAD_POOL_SIZE = 5; // 设置线程池大小
  private static final long RECONNECT_DELAY_SECONDS = 5; // 重连延迟时间
  private final ModbusService modbusService;
  private final ScheduledExecutorService executorService;
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
    // 发起长连接
    this.executorService = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
//    List<PtBoard> ptBoards = ptBoardRepository.findAll(PtBoardSpec.hasEnabled(true));
//    ptBoards.forEach(ptBoard -> executorService.execute(() -> {
//      try {
//        modbusService.persistentConnect(ptBoard.getId(), ptBoard.getIp(), ptBoard.getPort());
//      } catch (IOException e) {
//        log.error("reconnect {}:{}, {}", ptBoard.getIp(), ptBoard.getPort(), e.getMessage());
//        reconnect(ptBoard.getId(), ptBoard.getIp(), ptBoard.getPort());
//      }
//    }));
  }

//  public void reconnect(Long boardId, String ip, Integer port) {
//    executorService.schedule(() -> {
//      executorService.execute(() -> {
//        try {
//          modbusService.persistentConnect(boardId, ip, port);
//        } catch (IOException e) {
//          log.error("reconnect {}:{}, {}", ip, port, e.getMessage());
//          reconnect(boardId, ip, port);
//        }
//      });
//    }, RECONNECT_DELAY_SECONDS, TimeUnit.SECONDS);
//  }
//
//  @PreDestroy
//  public void stop() {
//    executorService.shutdownNow();
//  }

  public Optional<PtBoard> findPtBoard(Long boardId) {
    return ptBoardRepository.findOne(PtBoardSpec.is(boardId));
  }

  public Page<PtBoard> findPtBoards(Pageable pageable) {
    return ptBoardRepository.findAll(pageable);
  }

  public void inspectPtBoard(PtBoard ptBoard) {
    modbusService.shortConnect(ptBoard.getId(), ptBoard.getIp(), ptBoard.getPort());
  }

  @Scheduled(fixedDelay = 1000, initialDelay = 1000)
  public void pollPtBoards() {
    List<PtBoard> ptBoards = ptBoardRepository.findAll(PtBoardSpec.hasEnabled(true));
    ptBoards.forEach(ptBoard -> modbusService.shortConnect(ptBoard.getId(), ptBoard.getIp(), ptBoard.getPort()));
  }

}
