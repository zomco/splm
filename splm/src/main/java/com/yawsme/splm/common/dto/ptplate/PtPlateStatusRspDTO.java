package com.yawsme.splm.common.dto.ptplate;

import com.yawsme.splm.common.enums.PtPlateStatusValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(name = "压板信息响应")
public class PtPlateStatusRspDTO implements Serializable {

  Long id;
  Long plateId;
  PtPlateStatusValue actualValue;
  LocalDateTime createTime;
}

