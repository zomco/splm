package com.yawsme.splm.model;

import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 压板 Repository.
 */

@RepositoryRestResource(
    exported = false,
    path = "plates",
    collectionResourceRel = "plates",
    collectionResourceDescription = @Description("压板列表"),
    itemResourceRel = "plate",
    itemResourceDescription = @Description("压板")
)
public interface PtPlateRepository extends BaseRepository<PtPlate> {

}
