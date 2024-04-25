package com.yawsme.splm.model;


import com.yawsme.splm.common.enums.PtPlateStatusValue;
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
 * 压板状态.
 */
@Schema(name = "压板状态")
@Entity
@Table(name = "pt_plate_status")
@SQLDelete(sql = "UPDATE pt_plate_status SET is_deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PtPlateStatus extends BaseEntity {

  @NotNull
  @Column(nullable = false)
  @Schema(name = "压板ID")
  private Long plateId;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "实际状态")
  private PtPlateStatusValue actualValue = PtPlateStatusValue.OFF;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PtPlateStatus aPtPlate = (PtPlateStatus) o;
    return getId() != null && Objects.equals(getId(), aPtPlate.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
