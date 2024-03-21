package com.company.jobseekerservice.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.company.jobseekerservice.dto.CandidateApplyRequest;
import com.company.jobseekerservice.dto.CandidateApplyResponse;
import com.company.jobseekerservice.model.Candidate;
import com.company.jobseekerservice.model.CandidateApply;
import com.company.jobseekerservice.model.Vacancy;
import com.company.jobseekerservice.repository.CandidateApplyRepository;
import com.company.jobseekerservice.repository.CandidateRepository;
import com.company.jobseekerservice.repository.VacancyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateApplyService {

	private final SequenceGeneratorService sequenceGeneratorService;
	private final CandidateApplyRepository candidateApplyRepository;
	private final CandidateRepository candidateRepository;
	private final VacancyRepository vacancyRepository;
	
	public int createCandidateApply(CandidateApplyRequest candidateApplyRequest) {
		long candidateId = candidateApplyRequest.getCandidateId();
		long vacancyId = candidateApplyRequest.getVacancyId();
		
		Optional<Candidate> candidate = candidateRepository.findById(candidateId);
		
		if (candidate.isPresent()) {
			Optional<Vacancy> vacancy = vacancyRepository.findById(vacancyId);
			
			if (vacancy.isPresent()) {
				Optional<CandidateApply> candidateApplyData = candidateApplyRepository.findByCandidateIdAndVacancyId(candidateId, vacancyId);
				
				if (candidateApplyData.isPresent()) {
					return 4;
				} else {
					long id =  sequenceGeneratorService.generateSequence(CandidateApply.SEQUENCE_NAME);
					
					Date today = new Date();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String createdDate = formatter.format(today);
					
					CandidateApply candidateApply = CandidateApply.builder()
							.applyId(id)
							.candidateId(candidateId)
							.vacancyId(vacancyId)
							.applyDate(candidateApplyRequest.getApplyDate())
							.createdDate(createdDate)
							.build();
					
					candidateApplyRepository.save(candidateApply);
					log.info("Candidate apply {} is saved", candidateApply.getApplyId());
					
					return 1;
				}
			} else {
				return 3;
			}
		} else {
			return 2;
		}
	}
	
	public List<CandidateApplyResponse> getAllCandidateApply() {
		List<CandidateApply> candidateApply = candidateApplyRepository.findAll();
		
		return candidateApply.stream().map(this::mapToCandidateApplyResponse).toList();
	}
	
	public int updateCandidateApply(long id, CandidateApplyRequest candidateApplyRequest) {
		long candidateId = candidateApplyRequest.getCandidateId();
		long vacancyId = candidateApplyRequest.getVacancyId();
		
		Optional<Candidate> candidate = candidateRepository.findById(candidateId);
		
		if (candidate.isPresent()) {
			Optional<Vacancy> vacancy = vacancyRepository.findById(vacancyId);
			
			if (vacancy.isEmpty()) {
				Optional<CandidateApply> candidateApplyData = candidateApplyRepository.findByCandidateIdAndVacancyId(candidateId, vacancyId);
				
				if (candidateApplyData.isPresent() && candidateApplyData.get().getApplyId() != id) {
					return 4;
				} else {					
					CandidateApply candidateApply = CandidateApply.builder()
							.applyId(id)
							.candidateId(candidateId)
							.vacancyId(vacancyId)
							.applyDate(candidateApplyRequest.getApplyDate())
							.createdDate(candidateApplyData.get().getCreatedDate())
							.build();
					
					candidateApplyRepository.save(candidateApply);
					log.info("Candidate apply {} is updated", candidateApply.getApplyId());
					
					return 1;
				}
			} else {
				return 3;
			}
		} else {
			return 2;
		}
	}
	
	public boolean deleteCandidateApply(long id) {
		Optional<CandidateApply> candidateApply = candidateApplyRepository.findById(id);
		
		if (candidateApply.isPresent()) {
			candidateApplyRepository.deleteById(id);
			log.info("Candidate apply {} is deleted", id);
			
			return true;
		}
		
		return false;
	}
	
	private CandidateApplyResponse mapToCandidateApplyResponse(CandidateApply candidateApply) {
		return CandidateApplyResponse.builder()
				.applyId(candidateApply.getApplyId())
				.candidateId(candidateApply.getCandidateId())
				.vacancyId(candidateApply.getVacancyId())
				.applyDate(candidateApply.getApplyDate())
				.createdDate(candidateApply.getCreatedDate())
				.build();
	}
}
