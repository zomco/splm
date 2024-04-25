package com.yawsme.splm.model;

import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 压板状态 Repository.
 */

@RepositoryRestResource(
    exported = false,
    path = "plate_statuses",
    collectionResourceRel = "plate_statuses",
    collectionResourceDescription = @Description("压板状态列表"),
    itemResourceRel = "plate_status",
    itemResourceDescription = @Description("压板状态")
)
public interface PtPlateStatusRepository extends BaseRepository<PtPlateStatus> {

}
