package com.demo.demo.repo;

import com.demo.demo.models.OnDemandProcessInstance;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OnDemandProcessInstanceRepository extends PagingAndSortingRepository<OnDemandProcessInstance, Long>, JpaSpecificationExecutor<OnDemandProcessInstance> {
}
