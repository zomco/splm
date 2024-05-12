package com.yawsme.splm.model;


import org.springframework.data.jpa.domain.Specification;


public class PtPlateSpec {

  public static Specification<PtPlate> is(Long plateId) {
    return (root, query, builder) -> builder.equal(root.get("id"), plateId);
  }

  public static Specification<PtPlate> orderByCoordinate(Specification<PtPlate> spec) {
    return (root, query, builder) -> {
      query.orderBy(builder.asc(root.get("cy")), builder.asc(root.get("cx")));
      return spec.toPredicate(root, query, builder);
    };
  }

  public static Specification<PtPlate> hasBoard(Long boardId) {
    return (root, query, builder) -> builder.equal(root.get("boardId"), boardId);
  }

}
