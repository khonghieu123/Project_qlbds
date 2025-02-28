package com.javaweb.repository;

import com.javaweb.repository.entity.DistrictEntity;

public interface DistricRepository {
	DistrictEntity findNameById(Long id);
}
