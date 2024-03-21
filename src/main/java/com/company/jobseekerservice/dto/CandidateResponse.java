package com.company.jobseekerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponse {

	private long candidateId;
	private String fullName;
	private String dob;
	private String gender;
}
