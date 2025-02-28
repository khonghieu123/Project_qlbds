package com.javaweb.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;


import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
	void deleteByIdIn(Long[] ids);
	List<BuildingEntity> findByNameContaining(String s);
	List<BuildingEntity> findByNameContainingAndStreet(String s, String street);
}
