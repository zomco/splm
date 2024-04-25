package com.yawsme.splm.common.enums;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 前后端交互VO
 */
@Data
@Schema(name = "响应消息")
public class ResultMessage<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 成功标志
   */
  private boolean success;

  /**
   * 消息
   */
  private String message;

  /**
   * 返回代码
   */
  private Integer code;

  /**
   * 时间戳
   */
  private long timestamp = System.currentTimeMillis();

  /**
   * 结果对象
   */
  private T result;
}
