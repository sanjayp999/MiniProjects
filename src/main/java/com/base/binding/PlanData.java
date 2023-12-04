package com.base.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanData {

  
    private String planName;

    
    private LocalDate startDate;

   
    private LocalDate endDate;

   
    private String categoryName;
}
