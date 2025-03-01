package com.javaweb.repository.custom.IMPL;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.javaweb.utils.ConectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	
	@PersistenceContext
	private EntityManager entityManage;
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffid = buildingSearchBuilder.getStaffId();
		if(staffid != null){
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0 ) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id ");
		}
//		Long rentareaTo =  buildingSearchBuilder.getAreaTo();
//		Long rentareaFrom = buildingSearchBuilder.getAreaFrom();
//		if(rentareaTo != null ||rentareaFrom != null ) {
//			sql.append(" INNER JOIN rentarea ON b.id = rentarea.buildingid");
//		}
	}
	
	public static void joinQueryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where){
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				
				if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") &&
						!fieldName.startsWith("area")  && !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					System.out.println(value);
					if(value != null) {
						if(item.getType().getName().equals("java.lang.Long")  || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + " = " + value);
						}
						else {
							 where.append(" AND b."+ fieldName  + " LIKE '%" +  value + "%'");
						}
					}
				}
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
}
	
	public static void joinQueryspecial(BuildingSearchBuilder buildingSearchBuilder,StringBuilder where){
		Long staffid = buildingSearchBuilder.getStaffId();
		if(staffid != null) {
			where.append( " AND  assignmentbuilding.staffid = " + staffid );
		}
		
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Long rentPriceTo =  buildingSearchBuilder.getRentPriceTo();
		    
		if (rentPriceFrom != null) {
		    where.append(" AND b.rentprice >= " + rentPriceFrom);
		}
		if (rentPriceTo != null) {
		    where.append(" AND b.rentprice <= " + rentPriceTo);
		}
		
		Long rentareaTo =  buildingSearchBuilder.getAreaTo();
		Long rentareaFrom = buildingSearchBuilder.getAreaFrom();
		    
		if (rentareaTo != null || rentareaFrom != null) {
		    where.append(" AND EXISTS (SELECT * FROM rentarea WHERE b.id = rentarea.buildingid");
		    
		    if (rentareaFrom != null) {
		        where.append(" AND rentarea.value >= " + rentareaFrom);
		    }
		    
		    if (rentareaTo != null) {
		        where.append(" AND rentarea.value <= " + rentareaTo);
		    }
		    
		    where.append(")");
		}

		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		
		if(typeCode != null && typeCode.size() != 0) {
			where.append("AND(");
			String code = typeCode.stream().map(it -> " renttype.code like '%" + it + "%'").collect(Collectors.joining(" OR "));
			where.append(code);
			where.append(")");
		}		
	}
	
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder){
		StringBuilder sql = new StringBuilder("SELECT * From building b  ");
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		joinTable(buildingSearchBuilder, sql);
		joinQueryNormal(buildingSearchBuilder,where);
		joinQueryspecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id ");
		sql.append(where);
		System.out.println(sql.toString());
		Query query = entityManage.createNativeQuery(sql.toString(),BuildingEntity.class);
		return query.getResultList();
		 
		
//		return result;
	}
}
