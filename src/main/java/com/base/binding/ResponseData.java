package com.base.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ResponseData {
	
	private Integer planId;
	
	private String planName;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String activeSwitch;
	
	private String categoryName;


}
