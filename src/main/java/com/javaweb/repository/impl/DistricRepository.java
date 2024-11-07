package com.javaweb.repository.impl;

import com.javaweb.repository.entity.DistrictEntity;

public interface DistricRepository {
	DistrictEntity findNameById(Long id);
}
