package com.wellsfargo.diversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wellsfargo.diversity.entity.Diversity;
import com.wellsfargo.diversity.repository.DiversityRepository;

@Service
public class DiversityDataServiceImpl implements DiversityDataService {
	@Autowired
	DiversityRepository diversityRepository;

	@Override
	public List<Diversity> saveDiversityData(List<Diversity> diversityDataList) {
		return (List<Diversity>) diversityRepository.saveAll(diversityDataList);
	}

}
