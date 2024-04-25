package com.yawsme.splm.common.exception;


/**
 * Nmap 扫描操作异常.
 */
public class WebsocketException extends Exception {

  public WebsocketException(String message) {
    super(message);
  }

  public WebsocketException(String message, Throwable cause) {
    super(message, cause);
  }
}
