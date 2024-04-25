package com.yawsme.splm.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

  static public Long now() {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
}
