package com.base.service;
import java.util.List;
import com.base.binding.PlanData;
import com.base.binding.ResponseData;
import com.base.entity.PlanCategoryEntity;
import com.base.entity.PlanMasterEntity;


public interface PlanService {
	
	   public boolean savePlan(PlanData plan);
	   
	   public List<PlanCategoryEntity> getPlancategories();
	   
	   public PlanMasterEntity getPlanById(Integer planId);
	   
	   public List<ResponseData> getAllPlans();
	   
	   public boolean updatePlan(Integer planId,PlanData plan);
	   
	   public boolean deleteById(Integer planId);
	   
	   public boolean changePlanStatus(Integer planId,String activeSwitch);
	   
}
