package com.yawsme.splm.model;


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
 * 压板.
 */
@Schema(name = "压板")
@Entity
@Table(name = "pt_plate")
@SQLDelete(sql = "UPDATE pt_plate SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PtPlate extends BaseEntity {

  @NotNull
  @Column(nullable = false)
  @Schema(name = "压板名称")
  private String name;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "铁板ID")
  private Long boardId;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "启用状态")
  private Boolean enabled = true;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "压板所在行")
  private Integer cx;

  @NotNull
  @Column(nullable = false)
  @Schema(name = "压板所在列")
  private Integer cy;


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PtPlate aPtPlate = (PtPlate) o;
    return getId() != null && Objects.equals(getId(), aPtPlate.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
