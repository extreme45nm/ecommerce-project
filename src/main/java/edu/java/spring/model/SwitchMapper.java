package edu.java.spring.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SwitchMapper implements RowMapper<Switch>{

	@Override
	public Switch mapRow(ResultSet rs, int arg1) throws SQLException {
		Switch sw = new Switch();
		sw.setSwitchId(rs.getInt("switchId"));
		sw.setManufacturerId(rs.getInt("manufacturerId"));
		sw.setSwitchName(rs.getString("switchName"));
		sw.setSwitchForce(rs.getInt("switchForce"));
		return sw;
	}

}
