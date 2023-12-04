package com.base.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.entity.PlanCategoryEntity;

public interface PlanCategoryRepo extends JpaRepository<PlanCategoryEntity, Serializable> {

}
