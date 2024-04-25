package com.yawsme.splm.model;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * 通用 Repository.
 *
 * @param <T> 数据类型
 */
public interface BaseRepository<T>
    extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T>, CrudRepository<T, Long> {
}
