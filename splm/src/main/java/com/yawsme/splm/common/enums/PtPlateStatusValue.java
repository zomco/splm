package com.yawsme.splm.common.enums;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.yawsme.splm.common.utils.EnumUtil;

/**
 * 压板状态类型.
 */
public enum PtPlateStatusValue {
  @JsonProperty("0")
  OFF("0"),
  @JsonProperty("1")
  ON("1"),
  @JsonProperty("2")
  UNKNOWN("2");

  private final String code;

  PtPlateStatusValue(String code) {
    this.code = code;
  }

  public static PtPlateStatusValue fromCode(String code) {
    return EnumUtil.getEnumFromString(PtPlateStatusValue.class, code);
  }

  public String getCode() {
    return code;
  }
}

