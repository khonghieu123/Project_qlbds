package com.javaweb.repository;

import java.util.*;

import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findALL(Map<String, Object> param, List<String> typeCode);
}
