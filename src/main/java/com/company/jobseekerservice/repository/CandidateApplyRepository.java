package com.company.jobseekerservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.company.jobseekerservice.model.CandidateApply;

public interface CandidateApplyRepository extends MongoRepository<CandidateApply, Long> {

	@Query("{'candidate_id' : ?0, 'vacancy_id' : ?1}")
	Optional<CandidateApply> findByCandidateIdAndVacancyId(long candidateId, long vacancyId);
}
