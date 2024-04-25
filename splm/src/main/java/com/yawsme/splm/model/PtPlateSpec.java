package com.yawsme.splm.model;


import org.springframework.data.jpa.domain.Specification;


public class PtPlateSpec {

  public static Specification<PtPlate> is(Long plateId) {
    return (root, query, builder) -> builder.equal(root.get("id"), plateId);
  }

  public static Specification<PtPlate> hasName(String name) {
    return (root, query, builder) -> builder.equal(root.get("name"), name);
  }

  public static Specification<PtPlate> hasBoard(Long boardId) {
    return (root, query, builder) -> builder.equal(root.get("boardId"), boardId);
  }

}
