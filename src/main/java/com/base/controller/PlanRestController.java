package com.base.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.base.binding.PlanData;
import com.base.binding.ResponseData;
import com.base.constants.AppConstants;
import com.base.entity.PlanCategoryEntity;
import com.base.entity.PlanMasterEntity;
import com.base.props.AppProperties;
import com.base.service.PlanService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class PlanRestController {

	private static final Logger logger = LoggerFactory.getLogger(PlanRestController.class);

	@Autowired
	private PlanService service;

	@Autowired
	public AppProperties appProps;

	@GetMapping("/categories")
	public ResponseEntity<List<PlanCategoryEntity>> getAllCategories() {
		logger.info("Fetching all categories.");
		List<PlanCategoryEntity> plancategories = service.getPlancategories();
		return new ResponseEntity<>(plancategories, HttpStatus.OK);
	}

	
	@PostMapping("/saveplan")
	public ResponseEntity<String> savePlan(@RequestBody PlanData plan) {
		logger.info("Attempting to save plan: {}", plan);

		boolean savePlan = service.savePlan(plan);
		Map<String,String> messages=appProps.getMessages();
		
		if (savePlan) {
			logger.info("Plan saved successfully.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_SAVE_SUCCESS), HttpStatus.CREATED);
		} else {
			logger.error("Failed to save the plan.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_SAVE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/plans")
	public ResponseEntity<List<ResponseData>> getAllPlans() {
		logger.info("Fetching all plans.");
		List<ResponseData> allPlans = service.getAllPlans();
		return new ResponseEntity<>(allPlans, HttpStatus.OK);
	}

	@GetMapping("/plan/{planId}")
	public ResponseEntity<PlanMasterEntity> getPlanById(@PathVariable Integer planId) {
		logger.info("Fetching plan by ID: {}", planId);
		PlanMasterEntity planById = service.getPlanById(planId);
		return new ResponseEntity<>(planById, HttpStatus.OK);
	}

	@PutMapping("/updateplan/{planId}")
	public ResponseEntity<String> updatePlan(@PathVariable Integer planId, @RequestBody PlanData plan) {
		
		logger.info("Attempting to update plan: {}", plan);

		boolean updatePlan = service.updatePlan(planId, plan);
		Map<String,String> messages=appProps.getMessages();
		
		if (updatePlan) {
			logger.info("Plan updated successfully.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_UPDATE_SUCCESS), HttpStatus.CREATED);
		} else {
			logger.error("Failed to update the plan.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_UPDATE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/plan/{planId}")
	public ResponseEntity<String> deleteById(@PathVariable Integer planId) {
		logger.info("Attempting to delete plan with ID: {}", planId);

		boolean deleteById = service.deleteById(planId);
		Map<String,String> messages=appProps.getMessages();

		if (deleteById) {
			logger.info("Plan deleted successfully.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_DELETE_SUCCESS), HttpStatus.OK);
		} else {
			logger.error("Failed to delete the plan.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_DELETE_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/statuschange/{planId}/{activeSwitch}")
	public ResponseEntity<String> changeStatus(@PathVariable Integer planId, @PathVariable String activeSwitch) {
		logger.info("Changing status for plan with ID: {} to: {}", planId, activeSwitch);

		boolean changePlanStatus = service.changePlanStatus(planId, activeSwitch);
		Map<String,String> messages=appProps.getMessages();

		if (changePlanStatus) {
			logger.info("Status updated successfully.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_STATUS_SUCCESS), HttpStatus.OK);
		} else {
			logger.error("Failed to update the status.");
			return new ResponseEntity<>(messages.get(AppConstants.PLAN_STATUS_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}