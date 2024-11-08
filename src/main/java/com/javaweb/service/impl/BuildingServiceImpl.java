package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingDTOConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;


@Service
public  class BuildingServiceImpl implements BuildingService{
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired 
	private BuildingDTOConverter buildingDTOConverter;
	
	@Autowired 
	private BuildingSearchBuilderConverter  buildingSearchBuilderConverter;
	
	@Override
	public List<BuildingDTO> findALL(Map<String, Object> param, List<String> typeCode) {  
		
		 BuildingSearchBuilder  buildingSearchBuilder =  buildingSearchBuilderConverter.toBuildingSearchBuilder(param, typeCode);
		
		List<BuildingEntity> buildingEntityes = buildingRepository.findALL(buildingSearchBuilder);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for(BuildingEntity item : buildingEntityes) {
			BuildingDTO buiding = buildingDTOConverter.toBuildingDTO(item);
			result.add(buiding);
		}
		return result;
	}
	
}
