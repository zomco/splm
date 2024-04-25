package com.yawsme.splm.common.exception;


/**
 * Nmap 扫描操作异常.
 */
public class ServiceException extends Exception {

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
