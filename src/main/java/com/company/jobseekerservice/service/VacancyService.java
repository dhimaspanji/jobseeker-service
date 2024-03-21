package com.company.jobseekerservice.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.company.jobseekerservice.common.GenderEnum;
import com.company.jobseekerservice.dto.VacancyRequest;
import com.company.jobseekerservice.dto.VacancyResponse;
import com.company.jobseekerservice.model.Vacancy;
import com.company.jobseekerservice.repository.VacancyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyService {

	private final SequenceGeneratorService sequenceGeneratorService;
	private final VacancyRepository VacancyRepository;
	
	public int createVacancy(VacancyRequest vacancyRequest) {
		boolean isGender = false;
		
		for (GenderEnum gender : GenderEnum.values()) {
			if (vacancyRequest.getRequirementGender().toUpperCase().equals(gender.toString().toUpperCase())) {
				isGender = true;
			}
		}
		
		if (isGender) {
			Optional<Vacancy> vacancyData = VacancyRepository.findByVacancyName(vacancyRequest.getVacancyName().trim());
			
			if (vacancyData.isPresent()) {
				return 2;
			} else {
				long id = sequenceGeneratorService.generateSequence(Vacancy.SEQUENCE_NAME);
				
				Date today = new Date();
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createdDate = formatter.format(today);
				
				Vacancy vacancy = Vacancy.builder()
						.vacancyId(id)
						.vacancyName(vacancyRequest.getVacancyName())
						.minAge(vacancyRequest.getMinAge())
						.maxAge(vacancyRequest.getMaxAge())
						.requirementGender(StringUtils.capitalize(vacancyRequest.getRequirementGender().toLowerCase()))
						.createdDate(createdDate)
						.expiredDate(vacancyRequest.getExpiredDate())
						.build();
				
				VacancyRepository.save(vacancy);
				log.info("Vacancy {} is saved", vacancy.getVacancyId());
				
				return 1;
			}
		}
		
		return 0;
	}
	
	public List<VacancyResponse> getAllVacancy() {
		List<Vacancy> vacancys = VacancyRepository.findAll();
		
		return vacancys.stream().map(this::mapToVacancyResponse).toList();
	}
	
	public List<VacancyResponse> getVacancyByKeyword(String keyword) {
		List<Vacancy> vacancys = VacancyRepository.findByKeyword(keyword);
		
		return vacancys.stream().map(this::mapToVacancyResponse).toList();
	}
	
	public int updateVacancy(long id, VacancyRequest vacancyRequest) {
		boolean isGender = false;
		
		for (GenderEnum gender : GenderEnum.values()) {
			if (vacancyRequest.getRequirementGender().toUpperCase().equals(gender.toString().toUpperCase())) {
				isGender = true;
			}
		}
		
		if (isGender) {
			Optional<Vacancy> vacancyData = VacancyRepository.findById(id);
			
			if (vacancyData.isPresent()) {
				Optional<Vacancy> vacancyDataGetVacancyName = VacancyRepository.findByVacancyName(vacancyRequest.getVacancyName().trim());
				
				if (vacancyDataGetVacancyName.isPresent() && vacancyDataGetVacancyName.get().getVacancyId() != id) {
					return 3;
				} else {
					Vacancy vacancy = Vacancy.builder()
							.vacancyId(id)
							.vacancyName(vacancyRequest.getVacancyName())
							.minAge(vacancyRequest.getMinAge())
							.maxAge(vacancyRequest.getMaxAge())
							.requirementGender(StringUtils.capitalize(vacancyRequest.getRequirementGender().toLowerCase()))
							.createdDate(vacancyData.get().getCreatedDate())
							.expiredDate(vacancyRequest.getExpiredDate())
							.build();
					
					VacancyRepository.save(vacancy);
					log.info("Vacancy {} is updated", vacancy.getVacancyId());
					
					return 1;
				}
			} else {
				return 2;
			}
		}
		
		return 0;
	}
	
	public boolean deleteVacancy(long id) {
		Optional<Vacancy> vacancy = VacancyRepository.findById(id);
		
		if (vacancy.isPresent()) {
			VacancyRepository.deleteById(id);
			log.info("Vacancy {} is deleted", id);
			
			return true;
		}
		
		return false;
	}
	
	private VacancyResponse mapToVacancyResponse(Vacancy vacancy) {
		return VacancyResponse.builder()
				.vacancyId(vacancy.getVacancyId())
				.vacancyName(vacancy.getVacancyName())
				.minAge(vacancy.getMinAge())
				.maxAge(vacancy.getMaxAge())
				.requirementGender(vacancy.getRequirementGender())
				.createdDate(vacancy.getCreatedDate())
				.expiredDate(vacancy.getExpiredDate())
				.build();
	}
}
