package com.company.jobseekerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.company.jobseekerservice.model.Vacancy;

public interface VacancyRepository extends MongoRepository<Vacancy, Long> {

	@Query("{'vacancy_name' : ?0}")
	Optional<Vacancy> findByVacancyName(String vacancyName);
	
	@Query("{'$or' : [{'vacancy_name' : {$regex : ?0, $options: 'i'}}, "
			+ "{'min_age' : /^.*?0.*$/i}, "
			+ "{'max_age' : /^.*?0.*$/i}, "
			+ "{'requirement_gender' : {$regex : ?0, $options: 'i'}}]}")
	List<Vacancy> findByKeyword(String keyword);
}
