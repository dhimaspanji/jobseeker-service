package com.company.jobseekerservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.jobseekerservice.common.CommonLibrary;
import com.company.jobseekerservice.dto.CandidateApplyRequest;
import com.company.jobseekerservice.dto.CandidateApplyResponse;
import com.company.jobseekerservice.response.Response;
import com.company.jobseekerservice.service.CandidateApplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/candidate-apply")
@RequiredArgsConstructor
@Slf4j
public class CandidateApplyController {

	private final CandidateApplyService candidateApplyService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createCandidateApply(@RequestBody CandidateApplyRequest candidateApplyRequest) {
		Response resp = new Response();
		
		try {
			int isCreated = candidateApplyService.createCandidateApply(candidateApplyRequest);
			
			if (isCreated == 1) {
				resp.setCode(CommonLibrary.CREATED_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isCreated == 2) {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Candidate id not found");
			} else if (isCreated == 3) {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Vacancy id not found");
			} else if (isCreated == 4) {
				resp.setCode(CommonLibrary.CONFLICT_CODE);
				resp.setMessage(CommonLibrary.CONFLICT_MESSAGE + ". " + CommonLibrary.DUPLICATE_IS_NOT_ALLOWED);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Response getAllCandidateApply() {
		Response resp = new Response();
		
		try {
			List<CandidateApplyResponse> data = candidateApplyService.getAllCandidateApply();
			
			if (data.size() > 0) {
				resp.setCode(CommonLibrary.OK_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
				resp.setData(data);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@PostMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Response updateCandidateApply(@PathVariable("id") long id, @RequestBody CandidateApplyRequest candidateApplyRequest) {
		Response resp = new Response();
		
		try {
			int isUpdated = candidateApplyService.updateCandidateApply(id, candidateApplyRequest);
			
			if (isUpdated == 1) {
				resp.setCode(CommonLibrary.CREATED_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isUpdated == 2) {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Candidate id not found");
			} else if (isUpdated == 3) {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.FAILED_MESSAGE + ". Vacancy id not found");
			} else if (isUpdated == 4) {
				resp.setCode(CommonLibrary.CONFLICT_CODE);
				resp.setMessage(CommonLibrary.CONFLICT_MESSAGE + ". " + CommonLibrary.DUPLICATE_IS_NOT_ALLOWED);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Response deleteCandidateApply(@PathVariable("id") long id) {
		Response resp = new Response();
		
		try {
			boolean isDeleted = candidateApplyService.deleteCandidateApply(id);
			
			if (isDeleted) {
				resp.setCode(CommonLibrary.OK_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			}
		} catch (Exception e) {
			log.error("Error : ", e);
			
			resp.setCode(CommonLibrary.INTERNAL_SERVER_ERROR_CODE);
			resp.setMessage(CommonLibrary.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		
		return resp;
	}
}
