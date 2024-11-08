package com.javaweb.repository.impl;

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

import org.springframework.stereotype.Repository;

import com.javaweb.utils.ConectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffid = buildingSearchBuilder.getStaffId();
		if(staffid != null){
			sql.append(" INNER JOIN assignmentbuilding ON b.id =assignmentbuilding.buildingid ");
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0 ) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id ");
		}
		Long rentareaTo =  buildingSearchBuilder.getAreaTo();
		Long rentareaFrom = buildingSearchBuilder.getAreaFrom();
		if(rentareaTo != null ||rentareaFrom != null ) {
			sql.append(" INNER JOIN rentarea ON b.id = rentarea.buildingid");
		}
	}
	
	public static void joinQueryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where){
//		for (Map.Entry<String, Object> it : param.entrySet()) {
//			if(!it.getKey().equals("staffid") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area")  && !it.getKey().startsWith("rentPice")) {
//				String value = it.getValue().toString();
//				if(NumberUtil.checkNumber(value) == true){ 
//						where.append(" AND b." + it.getKey() + " = " + value);
//				}
//				else { 
//					  where.append(" AND b.name LIKE '%" +  value + "%'");
//				}
//			}
//		}
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffid") && !fieldName.equals("typeCode") &&
						!fieldName.startsWith("area")  && !fieldName.startsWith("rentPice")) {
					Object value = item.get(buildingSearchBuilder);
					if(value != null) {
						if(item.getType().getName().equals("java.lang.Long")  || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + " = " + value);
						}
						else if(item.getType().getName().equals("java.lang.String")) {
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
		Long rentPriceTo =  buildingSearchBuilder.getRentPriceTo();
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		    
		if(rentPriceTo != null ||rentPriceFrom != null) {
			where.append(" AND b.rentprice >= " + rentPriceFrom);
			where.append(" AND b.rentprice <= " + rentPriceTo);
		}
		
		Long rentareaTo =  buildingSearchBuilder.getAreaTo();
		Long rentareaFrom = buildingSearchBuilder.getAreaFrom();
		    
		if(rentareaTo != null ||rentareaFrom != null) {
			where.append(" AND rentarea.value >=" + rentareaTo);
			where.append(" AND rentarea.value <= " + rentareaFrom);
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		
		if(typeCode != null && typeCode.size() != 0) {
			where.append("AND(");
			String code = typeCode.stream().map(it -> " renttype.code like '%" + it + "%'").collect(Collectors.joining(" OR "));
			where.append(code);
			where.append(")");
		}		
	}
	
	
	
	public List<BuildingEntity> findALL(BuildingSearchBuilder buildingSearchBuilder){
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward,"
				     + "b.numberofbasement, b.managername, b.managerphonenumber, b.floorarea, b.rentprice, b.servicefee,b.brokeragefee FROM building b ");
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		joinTable(buildingSearchBuilder, sql);
		joinQueryNormal(buildingSearchBuilder,where);
		joinQueryspecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id ");
		sql.append(where);
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection conn = ConectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement(); 
				ResultSet rs = stmt.executeQuery(sql.toString());
				){
			while(rs.next()) {
				 BuildingEntity buildingEntity = new BuildingEntity();
				    buildingEntity.setId(rs.getLong("b.id"));
				    buildingEntity.setName(rs.getString("b.name"));
				    buildingEntity.setWard(rs.getString("b.ward"));
				    buildingEntity.setDistrictid(rs.getLong("b.districtid"));
				    buildingEntity.setStreet(rs.getString("b.street"));
				    buildingEntity.setFloorArea(rs.getLong("b.floorarea"));
				    buildingEntity.setRentPrice(rs.getLong("b.rentprice"));
				    buildingEntity.setServiceFee(rs.getString("b.servicefee"));
				    buildingEntity.setBrokerageFee(rs.getLong("b.brokeragefee"));
				    buildingEntity.setManagerName(rs.getString("b.managername"));
				    buildingEntity.setManagerPhoneNumber(rs.getString("b.managerphonenumber"));
				    result.add(buildingEntity);
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
