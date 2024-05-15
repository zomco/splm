package com.yawsme.splm.common.dto.ptplate;

import com.yawsme.splm.common.enums.PtBoardStatusValue;
import com.yawsme.splm.common.enums.PtPlateStatusValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(name = "铁板响应")
public class PtBoardRspDTO implements Serializable {

  Long id;
  String name;
  Boolean enabled;
  PtBoardStatusValue status;
  List<PtPlateRspDTO> plates;
}

