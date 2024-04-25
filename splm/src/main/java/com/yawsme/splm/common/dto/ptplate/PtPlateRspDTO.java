package com.yawsme.splm.common.dto.ptplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "压板信息响应")
public class PtPlateRspDTO implements Serializable {

  Long id;
  Long boardId;
  String name;
  Boolean enabled;
  Integer row;
  Integer column;
}

