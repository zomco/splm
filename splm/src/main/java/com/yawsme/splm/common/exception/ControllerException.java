package com.yawsme.splm.common.exception;

import com.yawsme.splm.common.enums.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 全局业务异常类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ControllerException extends RuntimeException {

  public static final String DEFAULT_MESSAGE = "网络错误，请稍后重试！";
  @Serial
  private static final long serialVersionUID = 3447728300174142127L;
  /**
   * 异常消息
   */
  private String msg = DEFAULT_MESSAGE;

  /**
   * 错误码
   */
  private ResultCode resultCode;

  public ControllerException() {
    super();
  }

  public ControllerException(ResultCode resultCode) {
    this.resultCode = resultCode;
  }

  public ControllerException(String message) {
    this.resultCode = ResultCode.ERROR;
    this.msg = message;
  }

  public ControllerException(ResultCode resultCode, String message) {
    this.resultCode = resultCode;
    this.msg = message;
  }

}
