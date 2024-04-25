package com.yawsme.splm.model;


import org.springframework.data.jpa.domain.Specification;


public class PtBoardSpec {

  public static Specification<PtBoard> is(Long boardId) {
    return (root, query, builder) -> builder.equal(root.get("id"), boardId);
  }

  public static Specification<PtBoard> hasName(String name) {
    return (root, query, builder) -> builder.equal(root.get("name"), name);
  }

}
