package com.javaweb.repository.custom.IMPL;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.impl.DistricRepository;
import com.javaweb.utils.ConectionJDBCUtil;

@Repository
public class DistricRepositoryImpl {
//	@Override
//	public DistrictEntity findNameById(Long id) {
//		String sql = "SELECT d.name FROM  district d WHERE d.id=" + id;
//		
//		DistrictEntity districEntity = new DistrictEntity();
//		
//		try(Connection conn = ConectionJDBCUtil.getConnection();
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql);
//				){
//			while(rs.next()) {
//				districEntity.setName(rs.getString("Name"));
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}
//		return districEntity;
//		
//	}
//	
}
