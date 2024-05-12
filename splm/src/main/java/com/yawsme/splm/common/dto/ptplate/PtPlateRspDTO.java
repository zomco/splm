package com.yawsme.splm.common.dto.ptplate;

import com.yawsme.splm.common.enums.PtPlateStatusValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Data
@Schema(name = "压板响应")
public class PtPlateRspDTO implements Serializable {

  Long id;
  Long boardId;
  String name;
  String name1;
  String name2;
  Boolean enabled;
  Integer cx;
  Integer cy;
  PtPlateStatusValue status;
  Page<PtPlateStatusRspDTO> statuses;
}

