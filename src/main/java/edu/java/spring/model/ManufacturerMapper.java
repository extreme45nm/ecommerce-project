package edu.java.spring.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ManufacturerMapper implements RowMapper<Manufacturer>{
	
	@Override
	public Manufacturer mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		Manufacturer manu = new Manufacturer();
		manu.setManufacturerId(resultSet.getInt("manufacturerid"));
		manu.setName(resultSet.getString("name"));
		manu.setLocation(resultSet.getString("location"));
		return manu;
	}
}
