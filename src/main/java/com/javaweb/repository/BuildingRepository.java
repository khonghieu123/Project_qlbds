package com.javaweb.repository;

import java.util.*;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findALL(BuildingSearchBuilder buildingSearchBuilder);
}
