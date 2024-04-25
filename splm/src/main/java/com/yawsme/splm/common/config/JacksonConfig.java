package com.yawsme.splm.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;

@Configuration
public class JacksonConfig implements InitializingBean {

  @Resource
  ObjectMapper objectMapper;

  private SimpleModule getSimpleModule() {
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    return simpleModule;
  }

  @Override
  public void afterPropertiesSet() {
    SimpleModule simpleModule = getSimpleModule();
    objectMapper.registerModule(simpleModule);
  }
}