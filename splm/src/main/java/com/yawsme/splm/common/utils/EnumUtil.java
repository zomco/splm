package com.yawsme.splm.common.utils;

/**
 * 枚举工具类.
 */
public class EnumUtil {
  /**
   * 从枚举值别名获取枚举值的通用方法.
   *
   * @param <T>  枚举值
   * @param c    枚举值类型
   * @param code 枚举值别名
   * @return corresponding enum, or null
   */
  public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String code) {
    if (c != null && code != null) {
      try {
        return Enum.valueOf(c, code.trim().toUpperCase());
      } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
        return null;
      }
    }
    return null;
  }
}
