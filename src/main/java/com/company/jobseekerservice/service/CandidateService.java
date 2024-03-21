package com.company.jobseekerservice.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.company.jobseekerservice.common.GenderEnum;
import com.company.jobseekerservice.dto.CandidateRequest;
import com.company.jobseekerservice.dto.CandidateResponse;
import com.company.jobseekerservice.model.Candidate;
import com.company.jobseekerservice.repository.CandidateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateService {
	
	private final SequenceGeneratorService sequenceGeneratorService;
	private final CandidateRepository candidateRepository;

	public int createdCandidate(CandidateRequest candidateRequest) {
		boolean isGender = false;
		
		for (GenderEnum gender : GenderEnum.values()) {
			if (candidateRequest.getGender().toUpperCase().equals(gender.toString().toUpperCase())) {
				isGender = true;
			}
		}
		
		if (isGender) {
			Optional<Candidate> candidateData = candidateRepository.findByFullName(candidateRequest.getFullName().trim());
			
			if (candidateData.isPresent()) {
				return 2;
			} else {
				long id = sequenceGeneratorService.generateSequence(Candidate.SEQUENCE_NAME);
				
				Candidate candidate = Candidate.builder()
						.candidateId(id)
						.fullName(candidateRequest.getFullName())
						.dob(candidateRequest.getDob())
						.gender(StringUtils.capitalize(candidateRequest.getGender().toLowerCase()))
						.build();
				
				candidateRepository.save(candidate);
				log.info("Candidate {} is saved", candidate.getCandidateId());
				
				return 1;
			}
		}
		
		return 0;
	}
	
	public List<CandidateResponse> getAllCandidate(){
		List<Candidate> candidates = candidateRepository.findAll();
		
		return candidates.stream().map(this::mapToCandidateResponse).toList();
	}
	
	public List<CandidateResponse> getCandidateByKeyword(String keyword){
		List<Candidate> candidates = candidateRepository.findByKeyword(keyword);
		
		return candidates.stream().map(this::mapToCandidateResponse).toList();
	}
	
	public int updateCandidate(long id, CandidateRequest candidateRequest) {
		boolean isGender = false;
		
		for (GenderEnum gender : GenderEnum.values()) {
			if (candidateRequest.getGender().toUpperCase().equals(gender.toString().toUpperCase())) {
				isGender = true;
			}
		}
		
		if (isGender) {
			Optional<Candidate> candidateData = candidateRepository.findById(id);
			
			if (candidateData.isPresent()) {
				Optional<Candidate> candidateDataGetFullName = candidateRepository.findByFullName(candidateRequest.getFullName().trim());
				
				if (candidateDataGetFullName.isPresent() && candidateDataGetFullName.get().getCandidateId() != id) {
					return 3;
				} else {
					Candidate candidate = Candidate.builder()
							.candidateId(id)
							.fullName(candidateRequest.getFullName())
							.dob(candidateRequest.getDob())
							.gender(StringUtils.capitalize(candidateRequest.getGender().toLowerCase()))
							.build();
					
					candidateRepository.save(candidate);
					log.info("Candidate {} is updated", candidate.getCandidateId());
					
					return 1;
				}
			} else {
				return 2;
			}
		}
		
		return 0;
	}
	
	public boolean deleteCandidate(long id) {
		Optional<Candidate> candidateData = candidateRepository.findById(id);
		
		if (candidateData.isPresent()) {
			candidateRepository.deleteById(id);
			log.info("Candidate {} is deleted", id);
			
			return true;
		}
		
		return false;
	}
	
	private CandidateResponse mapToCandidateResponse(Candidate candidate) {
		return CandidateResponse.builder()
				.candidateId(candidate.getCandidateId())
				.fullName(candidate.getFullName())
				.dob(candidate.getDob())
				.gender(candidate.getGender())
				.build();
	}
}
