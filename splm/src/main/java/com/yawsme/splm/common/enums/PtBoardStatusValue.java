package com.yawsme.splm.common.enums;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.yawsme.splm.common.utils.EnumUtil;

/**
 * 铁板状态类型.
 */
public enum PtBoardStatusValue {
  @JsonProperty("0")
  OFFLINE("0"),
  @JsonProperty("1")
  ONLINE("1"),
  @JsonProperty("2")
  UNKNOWN("2");

  private final String code;

  PtBoardStatusValue(String code) {
    this.code = code;
  }

  public static PtBoardStatusValue fromCode(String code) {
    return EnumUtil.getEnumFromString(PtBoardStatusValue.class, code);
  }

  public String getCode() {
    return code;
  }
}

