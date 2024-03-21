package com.company.jobseekerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateApplyResponse {

	private long applyId;
	private long candidateId;
	private long vacancyId;
	private String applyDate;
	private String createdDate;
}
