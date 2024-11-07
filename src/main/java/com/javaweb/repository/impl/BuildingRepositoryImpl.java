package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.utils.ConectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
	public static void joinTable(Map<String, Object> param, List<String> typeCode, StringBuilder sql) {
		String staffid = (String)param.get("staffid");
		if(staffid != null && staffid != ""){
			sql.append(" INNER JOIN assignmentbuilding ON b.id =assignmentbuilding.buildingid ");
		}
		
		if(typeCode != null && typeCode.size() != 0 ) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id ");
		}
		String rentareaTo = (String)param.get("areaTo");
		String rentareaFrom = (String)param.get("areaFrom");
		if(rentareaTo != null && rentareaTo != ""  ||rentareaFrom != null && rentareaFrom != "") {
			sql.append(" INNER JOIN rentarea ON b.id = rentarea.buildingid");
		}
	}
	
	public static void joinQueryNormal(Map<String, Object> param, StringBuilder where){
		for (Map.Entry<String, Object> it : param.entrySet()) {
			if(!it.getKey().equals("staffid") && !it.getKey().equals("typeCode") && !it.getKey().startsWith("area")  && !it.getKey().startsWith("rentPice")) {
				String value = it.getValue().toString();
				if(NumberUtil.checkNumber(value) == true){ 
						where.append(" AND b." + it.getKey() + " = " + value);
				}
				else { 
					  where.append(" AND b.name LIKE '%" +  value + "%'");
				}
			}
		}
	}
	
	public static void joinQueryspecial(Map<String, Object> param, List<String> typeCode, StringBuilder where){
		  String staffid = param.get("staffid") != null ? param.get("staffid").toString() : "";
		if(StringUtil.checkString(staffid)) {
			where.append( " AND  assignmentbuilding.staffid = " + staffid );
		}
		 String rentPriceTo = param.get("rentPriceTo") != null ? param.get("rentPriceTo").toString() : "";
		 String rentPriceFrom = param.get("rentPriceFrom") != null ? param.get("rentPriceFrom").toString() : "";
		    
		if(StringUtil.checkString(rentPriceTo) ||StringUtil.checkString(rentPriceFrom)) {
			where.append(" AND b.rentprice >=" + rentPriceFrom);
			where.append(" AND b.rentprice <= " + rentPriceTo);
		}
		
		 String rentAreaTo = param.get("rentAreaTo") != null ? param.get("rentAreaTo").toString() : "";
		 String rentAreaFrom = param.get("rentAreaFrom") != null ? param.get("rentAreaFrom").toString() : "";
		    
		if(StringUtil.checkString(rentAreaTo) ||StringUtil.checkString(rentAreaFrom)) {
			where.append(" AND rentarea.value >=" + rentAreaFrom);
			where.append(" AND rentarea.value <= " + rentAreaTo);
		}
		
		if(typeCode != null && typeCode.size() != 0) {
			List<String> code = new ArrayList<>();
			for(String it : typeCode) {
				code.add("'" + it + "'");	
			}
			where.append(" AND renttype.code IN(" + String.join(",", code) + ")");
			
		}		
	}
	
	
	
	public List<BuildingEntity> findALL(Map<String, Object> param, List<String> typeCode){
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward,"
				     + "b.numberofbasement, b.managername, b.managerphonenumber, b.floorarea, b.rentprice, b.servicefee,b.brokeragefee FROM building b ");
		StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
		joinTable(param, typeCode, sql);
		joinQueryNormal(param,where);
		joinQueryspecial(param,typeCode, where);
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
