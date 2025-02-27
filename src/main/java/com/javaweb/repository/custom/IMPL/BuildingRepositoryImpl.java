package com.javaweb.repository.custom.IMPL;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;


@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository {
	
	@PersistenceContext
	private EntityManager entityManage;
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// JPQL: JPA query language
//		String sql  = "From BuildingEntity b ";
//		Query query = entityManage.createQuery(sql,BuildingEntity.class);
//		return query.getResultList();
		
		// JPA native
		String sql = "SELECT * FROM building b WHERE b.name like '%building%'";
		Query query = entityManage.createNativeQuery(sql,BuildingEntity.class);
		return query.getResultList();
	}
	
}
