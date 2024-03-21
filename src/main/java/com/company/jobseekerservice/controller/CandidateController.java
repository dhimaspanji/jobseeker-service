package com.company.jobseekerservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.jobseekerservice.common.CommonLibrary;
import com.company.jobseekerservice.dto.CandidateRequest;
import com.company.jobseekerservice.dto.CandidateResponse;
import com.company.jobseekerservice.response.Response;
import com.company.jobseekerservice.service.CandidateService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
@Slf4j
public class CandidateController {

	private final CandidateService candidateService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response createCandidate(@RequestBody CandidateRequest candidateRequest) {
		Response resp = new Response();
		
		try {
			int isCreated = candidateService.createdCandidate(candidateRequest);
			
			if (isCreated == 1) {				
				resp.setCode(CommonLibrary.CREATED_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isCreated == 2) {
				resp.setCode(CommonLibrary.CONFLICT_CODE);
				resp.setMessage(CommonLibrary.CONFLICT_MESSAGE + ". " + CommonLibrary.DUPLICATE_IS_NOT_ALLOWED);
			} else {
				resp.setCode(CommonLibrary.BAD_REQUEST_CODE);
				resp.setMessage(CommonLibrary.BAD_REQUEST_MESSAGE);
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
	public Response getAllCandidate() {
		Response resp = new Response();
		
		try {
			List<CandidateResponse> data = candidateService.getAllCandidate();
			
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
	
	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public Response getCandidateByKeyword(@RequestParam String keyword) {
		Response resp = new Response();
		
		try {
			List<CandidateResponse> data = candidateService.getCandidateByKeyword(keyword);
			
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
	public Response updateCandidate(@PathVariable("id") long id, @RequestBody CandidateRequest candidateRequest) {
		Response resp = new Response();
		
		try {
			int isUpdated = candidateService.updateCandidate(id, candidateRequest);
			
			if (isUpdated == 1) {
				resp.setCode(CommonLibrary.CREATED_CODE);
				resp.setMessage(CommonLibrary.SUCCESS_MESSAGE);
			} else if (isUpdated == 2) {
				resp.setCode(CommonLibrary.NOT_FOUND_CODE);
				resp.setMessage(CommonLibrary.NOT_FOUND_MESSAGE);
			} else if (isUpdated == 3) {
				resp.setCode(CommonLibrary.CONFLICT_CODE);
				resp.setMessage(CommonLibrary.CONFLICT_MESSAGE + ". " + CommonLibrary.DUPLICATE_IS_NOT_ALLOWED);
			} else {
				resp.setCode(CommonLibrary.BAD_REQUEST_CODE);
				resp.setMessage(CommonLibrary.BAD_REQUEST_MESSAGE);
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
	public Response deleteCandidate(@PathVariable("id") long id) {
		Response resp = new Response();
		
		try {
			boolean isDeleted = candidateService.deleteCandidate(id);
			
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
