package com.yawsme.splm.common.exception;


/**
 * Nmap 扫描操作异常.
 */
public class MqttException extends Exception {

  public MqttException(String message) {
    super(message);
  }

  public MqttException(String message, Throwable cause) {
    super(message, cause);
  }
}
