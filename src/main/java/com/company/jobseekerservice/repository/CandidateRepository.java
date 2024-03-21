package com.company.jobseekerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.company.jobseekerservice.model.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, Long> {

	@Query("{'full_name' : ?0}")
	Optional<Candidate> findByFullName(String fullName);
	
	@Query("{'$or' : [{'full_name' : {$regex : ?0, $options: 'i'}}, "
			+ "{'dob' : /^.*?0.*$/i}, "
			+ "{'gender' : {$regex : ?0, $options: 'i'}}]}")
	List<Candidate> findByKeyword(String keyword);
}
