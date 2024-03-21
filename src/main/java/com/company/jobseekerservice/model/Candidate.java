package com.company.jobseekerservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "candidate")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Candidate {

	@Transient
	public static final String SEQUENCE_NAME = "candidate_seq";
	
	@Id
	private long candidateId;
	
	@Field(name = "full_name")
	private String fullName;
	
	private String dob;
	private String gender;
}
