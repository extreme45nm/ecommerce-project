package edu.java.spring.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class KeyboardMapper implements RowMapper<Keyboard>{
	
	public Keyboard mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		Keyboard kb = new Keyboard();
		kb.setProductId(resultSet.getInt("productId"));
		kb.setName(resultSet.getString("name"));
		kb.setManufacturerId(resultSet.getInt("manufacturerId"));
		kb.setSwitchId(resultSet.getInt("switchId"));
		kb.setProfileId(resultSet.getInt("profileId"));
		kb.setPrice(resultSet.getInt("price"));
		return kb;
	}

	
}
