package com.yawsme.splm.common.enums;

/**
 * 返回状态码
 */
public enum ResultCode {

  /**
   * 成功状态码
   */
  SUCCESS(200, "成功"),

  /**
   * 失败返回码
   */
  ERROR(400, "服务器繁忙，请稍后重试"),

  /**
   * 参数异常
   */
  PARAMS_ERROR(4002, "参数异常"),
  METHOD_NOT_ALLOWED(4003, "请求方法不正确"),

  /**
   * 系统异常
   */
  LIMIT_ERROR(1003, "访问过于频繁，请稍后再试"),

  /**
   * 用户
   */
  USER_NOT_EXIST(20001, "用户不存在"),
  USER_NOT_LOGIN(20002, "用户未登录"),
  USER_GET_WX_SESSION_ERROR(20003, "无法获取Session"),
  USER_ALREADY_EXIST(20004, "用户已存在"),
  USER_NOT_MATCH_PASSWORD(20005, "用户密码不匹配"),
  USER_NAME_INVALID(20006, "用户名长度无效"),
  USER_PASSWORD_INVALID(20006, "密码长度无效"),
  /**
   * 铁板
   */
  BOARD_NOT_EXIST(30001, "铁板不存在"),
  /**
   * 压板
   */
  PLATE_NOT_EXIST(40001, "压板不存在"),
  PLATE_STATUS_NOT_EXIST(40002, "压板状态不存在");


  private final Integer code;
  private final String message;


  ResultCode(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer code() {
    return this.code;
  }

  public String message() {
    return this.message;
  }

}
