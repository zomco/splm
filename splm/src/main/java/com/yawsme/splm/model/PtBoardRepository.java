package com.yawsme.splm.model;

import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * 铁板 Repository.
 */

@RepositoryRestResource(
    exported = false,
    path = "boards",
    collectionResourceRel = "boards",
    collectionResourceDescription = @Description("铁板列表"),
    itemResourceRel = "board",
    itemResourceDescription = @Description("铁板")
)
public interface PtBoardRepository extends BaseRepository<PtBoard> {

}
