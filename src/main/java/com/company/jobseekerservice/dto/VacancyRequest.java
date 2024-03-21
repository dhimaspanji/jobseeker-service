package com.company.jobseekerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancyRequest {

	private String vacancyName;
	private String minAge;
	private String maxAge;
	private String requirementGender;
	private String expiredDate;
}
