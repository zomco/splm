package com.yawsme.splm.common.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * 重定义控制台打印log不同级别的区分颜色.
 */
public class LogConfig extends ForegroundCompositeConverterBase<ILoggingEvent> {
  @Override
  protected String getForegroundColorCode(ILoggingEvent event) {
    Level level = event.getLevel();
    return switch (level.toInt()) {
      case Level.ERROR_INT -> ANSIConstants.MAGENTA_FG;
      case Level.WARN_INT -> ANSIConstants.YELLOW_FG;
      case Level.DEBUG_INT -> ANSIConstants.GREEN_FG;
      default -> ANSIConstants.DEFAULT_FG;
    };
  }
}
