package com.yawsme.splm.common.dto.ptplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "压板信息响应")
public class PtPlateUpdateReqDTO implements Serializable {

  String name;
  Boolean enabled;
}

