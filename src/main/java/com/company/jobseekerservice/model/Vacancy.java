package com.company.jobseekerservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "vacancy")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Vacancy {

	@Transient
	public static final String SEQUENCE_NAME = "vacancy_seq";
	
	@Id
	private long vacancyId;
	
	@Field(name = "vacancy_name")
	private String vacancyName;
	
	@Field(name = "min_age")
	private String minAge;
	
	@Field(name = "max_age")
	private String maxAge;
	
	@Field(name = "requirement_gender")
	private String requirementGender;
	
	@Field(name = "created_date")
	private String createdDate;
	
	@Field(name = "expired_date")
	private String expiredDate;
}
