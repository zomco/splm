package com.yawsme.splm.model;


import com.yawsme.splm.common.enums.PtBoardStatusValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Objects;

/**
 * 铁板.
 */
@Schema(name = "铁板")
@Entity
@Table(name = "pt_board")
@SQLDelete(sql = "UPDATE pt_board SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PtBoard extends BaseEntity {

  @NotNull
  @Column(nullable = false)
  @Schema(name = "铁板名称")
  private String name;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "启用状态")
  private Boolean enabled = true;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "铁板地址")
  private String ip;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "铁板端口")
  private Integer port;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "压板状态")
  private PtBoardStatusValue status = PtBoardStatusValue.UNKNOWN;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PtBoard aPtPlate = (PtBoard) o;
    return getId() != null && Objects.equals(getId(), aPtPlate.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
