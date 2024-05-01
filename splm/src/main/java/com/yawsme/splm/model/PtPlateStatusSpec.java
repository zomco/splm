package com.yawsme.splm.model;


import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;


public class PtPlateStatusSpec {

  public static Specification<PtPlateStatus> is(Long plateStatusId) {
    return (root, query, builder) -> builder.equal(root.get("id"), plateStatusId);
  }

  public static Specification<PtPlateStatus> hasPlate(Long plateId) {
    return (root, query, builder) -> builder.equal(root.get("plateId"), plateId);
  }

  public static Specification<PtPlateStatus> orderByTime(Specification<PtPlateStatus> spec) {
    return (root, query, builder) -> {
      query.orderBy(builder.desc(root.get("createTime")));
      return spec.toPredicate(root, query, builder);
    };
  }

  public static Specification<PtPlateStatus> hasTimeRange(LocalDateTime start, LocalDateTime stop) {
    return (root, query, builder) -> {
      Predicate predicate = builder.conjunction();
      predicate.getExpressions().add(builder.greaterThanOrEqualTo(root.get("createTime"), start));
      predicate.getExpressions().add(builder.lessThanOrEqualTo(root.get("createTime"), stop));
      return predicate;
    };
  }
}
