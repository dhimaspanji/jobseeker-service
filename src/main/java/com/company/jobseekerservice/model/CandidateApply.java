package com.company.jobseekerservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "candidate_apply")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CandidateApply {
	
	@Transient
	public static final String SEQUENCE_NAME = "candidate_apply_seq";

	@Id
	private long applyId;
	
	@Field(name = "candidate_id")
	private long candidateId;
	
	@Field(name = "vacancy_id")
	private long vacancyId;
	
	@Field(name = "apply_date")
	private String applyDate;
	
	@Field(name = "created_date")
	private String createdDate;
}
