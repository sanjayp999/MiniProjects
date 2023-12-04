package com.base.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.binding.PlanData;
import com.base.binding.ResponseData;
import com.base.entity.PlanCategoryEntity;
import com.base.entity.PlanMasterEntity;
import com.base.repo.PlanCategoryRepo;
import com.base.repo.PlanMasterRepo;

@Service
public class ServiceImplementation implements PlanService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplementation.class);

    @Autowired
    private PlanMasterRepo planMasterRepo;
 
    @Autowired
    private PlanCategoryRepo planCategoryRepo;

    @Override
    public boolean savePlan(PlanData plan) {
    	
        logger.info("Saving plan: {}", plan);

        PlanMasterEntity master = new PlanMasterEntity();
        master.setActiveSwitch("deactive");
        master.setCreatedBy("Admin");
        master.setUpdatedBy("Admin");
        BeanUtils.copyProperties(plan, master);
        PlanMasterEntity save = planMasterRepo.save(master);

        boolean result = save.getPlanId() != null;
        if (result) {
            logger.info("Plan saved successfully.");
        } else {
            logger.error("Failed to save the plan.");
        }

        return result;
    }

    @Override
    public List<PlanCategoryEntity> getPlancategories() {
        logger.info("Fetching all plan categories.");
        return planCategoryRepo.findAll();
    }

    @Override
    public PlanMasterEntity getPlanById(Integer planId) {
        logger.info("Fetching plan by ID: {}", planId);

        Optional<PlanMasterEntity> findById = planMasterRepo.findById(planId);
        if (findById.isPresent()) {
            PlanMasterEntity planMasterEntity = findById.get();
            logger.info("Fetched plan: {}", planMasterEntity);
            return planMasterEntity;
        } else {
            logger.warn("Plan not found with ID: {}", planId);
            return null;
        }
    }

    @Override
    public List<ResponseData> getAllPlans() {
        logger.info("Fetching all plans.");
        List<PlanMasterEntity> findAll = planMasterRepo.findAll();
        List<ResponseData> arrayList = new ArrayList<>();

        for (PlanMasterEntity entity : findAll) {
            ResponseData responseData = new ResponseData();
            BeanUtils.copyProperties(entity, responseData);
            arrayList.add(responseData);
        }

        logger.info("Fetched {} plans.", arrayList.size());
        return arrayList;
    }

    @Override
    public boolean updatePlan(Integer planId,PlanData plan) {
        logger.info("Updating plan: {}", plan);
         
        Optional<PlanMasterEntity> findById = planMasterRepo.findById(planId);
        PlanMasterEntity planMasterEntity = findById.get();      
        BeanUtils.copyProperties(plan, planMasterEntity);
        PlanMasterEntity save = planMasterRepo.save(planMasterEntity);

        boolean result = save.getPlanId() != null;
        if (result) {
            logger.info("Plan updated successfully.");
        } else {
            logger.error("Failed to update the plan.");
        }

        return result;
    }

    @Override
    public boolean deleteById(Integer planId) {
        logger.info("Deleting plan with ID: {}", planId);

        try {
            planMasterRepo.deleteById(planId);
            logger.info("Plan deleted successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete the plan with ID: {}. Error: {}", planId, e.getMessage());
        }

        return false;
    }

    @Override
    public boolean changePlanStatus(Integer planId, String activeSwitch) {
        logger.info("Changing status for plan with ID: {} to: {}", planId, activeSwitch);

        Optional<PlanMasterEntity> findById = planMasterRepo.findById(planId);
        if (findById.isPresent()) {
            PlanMasterEntity plan = findById.get();
            plan.setActiveSwitch(activeSwitch);
            planMasterRepo.save(plan);
            logger.info("Status updated successfully.");
            return true;
        } else {
            logger.warn("Plan not found with ID: {}. Status update failed.", planId);
        }

        return false;
    }
}