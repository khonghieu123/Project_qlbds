package com.javaweb.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.nio.file.FileAlreadyExistsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.ErrorResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

import customexception.FieldRequireException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@PropertySource("classpath:application.properties")
@Transactional
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;
	
	@Value("${dev}")
	private String data;
	
	@PersistenceContext
	private EntityManager entitymanager;
	
	
	@Autowired
	private BuildingRepository buildingRepository;
	
	@GetMapping(value = "/api/building")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> param,
			                           @RequestParam(name ="typeCode", required = false) List<String> typeCode){
		List<BuildingDTO> result = buildingService.findAll(param, typeCode);
		return result;
	}
	
//	@GetMapping(value = "/api/building/{name}/{street}")
//	public BuildingDTO getBuildingById(@PathVariable String name, @PathVariable String street){
//		BuildingDTO result = new BuildingDTO();
//		List<BuildingEntity> building = buildingRepository.findByNameContainingAndStreet(name, street);
//		return result;
//	}
	
	
//	@DeleteMapping(value ="/api/building/{id}")
//	public void deleteBuiling(@PathVariable Integer id) {
//		System.out.print(data);
//	}
//	
	@PostMapping(value ="/api/building/")
	public void creatBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity builEntity = new BuildingEntity();
		builEntity.setName(buildingRequestDTO.getName());
		builEntity.setStreet(buildingRequestDTO.getStreet());
		builEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districEntity = new DistrictEntity();
		districEntity.setId(buildingRequestDTO.getDistrictId());
		builEntity.setDistrict(districEntity);
		entitymanager.persist(builEntity); // thêm 1 entity mới vào database
		System.out.print("ok");
	}
	
//	@PutMapping(value ="/api/building/")
//	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
//		BuildingEntity builEntity = buildingRepository.findById(buildingRequestDTO.getId()).get();
//		builEntity.setName(buildingRequestDTO.getName());
//		builEntity.setStreet(buildingRequestDTO.getStreet());
//		builEntity.setWard(buildingRequestDTO.getWard());
//		DistrictEntity districEntity = new DistrictEntity();
//		districEntity.setId(buildingRequestDTO.getDistrictId());
//		builEntity.setDistrict(districEntity);
////		entitymanager.merge(builEntity); // update trong database
//		buildingRepository.save(builEntity);
////		System.out.print("ok");
//	}
//	@DeleteMapping("/api/building/{ids}")
//	public void deleteBuilding(@PathVariable Long[] ids) {
//	   buildingRepository.deleteByIdIn(ids);// xóa theo 1 list danh sách
//	}


}
