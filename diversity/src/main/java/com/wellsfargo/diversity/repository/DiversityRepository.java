package com.wellsfargo.diversity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wellsfargo.diversity.entity.Diversity;

@Repository
public interface DiversityRepository extends CrudRepository<Diversity, Long> {

}
