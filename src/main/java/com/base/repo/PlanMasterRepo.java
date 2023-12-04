package com.base.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.base.entity.PlanMasterEntity;

public interface PlanMasterRepo extends JpaRepository<PlanMasterEntity, Serializable> {

}
