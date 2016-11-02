package edu.java.spring.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.SQLErrorCodes;

import edu.java.spring.model.Keyboard;
import edu.java.spring.model.KeyboardMapper;

public class KeyboardDAO {
	
	private static Log log = LogFactory.getLog(KeyboardDAO.class);
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	private String insertSQL;
	
	@Autowired @Qualifier("keyboardMapper") KeyboardMapper mapper;
	
	public List<Keyboard> searchKeyboardByName(String keyboardName){
		String sql = "SELECT * FROM keyboard WHERE name like ?"; 
		List<Keyboard> allKeyboards = jdbcTemplateObject.query(sql,new Object[]{'%'+keyboardName+'%'}, mapper);
		return allKeyboards;
	}
	
	public void deleteKeyboard(Integer productId){
		String sql = "DELETE FROM keyboard WHERE productId = ?";
		jdbcTemplateObject.update(sql,productId);
		log.info("DELETED record with ID = "+productId);
		return;
	}
	
	public void updateKeyboard(Keyboard keyboard){
		String sql = "UPDATE keyboard SET name = ?, manufacturerId = ?, "
				+ "switchId = ?, profileId = ?, price = ? WHERE productId = ?";
		jdbcTemplateObject.update(sql,keyboard.getName(),keyboard.getManufacturerId(),
				keyboard.getSwitchId(),keyboard.getProfileId(),keyboard.getPrice(),
				keyboard.getProductId());
		log.info("UPDATED record with ID = "+keyboard.getProductId());
		return;
	}
	
	public Keyboard loadKeyBoard(int productId){
		String sql = "SELECT * FROM keyboard WHERE productId = ?";
		return jdbcTemplateObject.queryForObject(sql, new Object[]{productId},mapper);
	}
	
	public List<Keyboard> listKeyboard(){
		String sql = "SELECT * FROM keyboard";
		List<Keyboard> allKeyboards = jdbcTemplateObject.query(sql, new KeyboardMapper());
		return allKeyboards;
	}
	
	public void insertKeyboard(final Keyboard keyboard){
		PreparedStatementCreator psKreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection konn) throws SQLException {
				 PreparedStatement stmt = konn.prepareStatement(insertSQL);
				 stmt.setInt(1,keyboard.getProductId());
				 stmt.setString(2, keyboard.getName());
				 stmt.setInt(3,keyboard.getManufacturerId());
				 stmt.setInt(4,keyboard.getSwitchId());
				 stmt.setInt(5,keyboard.getProfileId());
				 stmt.setInt(6,keyboard.getPrice());
				return stmt;
			}
		};
		jdbcTemplateObject.update(psKreator);
		log.info("CREATED record Name = "+keyboard.getName());
	}
	
	public void setDataSource(DataSource dataSource){
		if(dataSource == null){
			log.info("\n\n Datasource is NULLL \n\n");
			return;
		}else{
			this.dataSource = dataSource;
			jdbcTemplateObject = new JdbcTemplate(dataSource);
		}
	}
	
	public DataSource getDataSource(){
		return dataSource;
	}
	
	public void createTableIfNotExist(String tableName,String createTableSql) throws SQLException{
		DatabaseMetaData dbMetaData = dataSource.getConnection().getMetaData();
		ResultSet rs = dbMetaData.getTables(null, null, tableName.toUpperCase(),null);
		if(rs.next()){
			log.info("\n Table "+rs.getString("TABLE_NAME")+" already exists!! \n");
			return;
		}
		jdbcTemplateObject.execute(createTableSql);
	}
	
	public void shutdown(){
		try{
			dataSource.getConnection().close();
		}catch(SQLException exc){
			log.error(exc);
		}
		
		try{
			log.info("\n Shutdown database \n");
			DriverManager.getConnection("jdbc:sqlserver:;shutdown=true");
		}catch(SQLException exc){
			log.error(exc);
		}
	}

	public String getInsertSQL() {
		return insertSQL;
	}

	public void setInsertSQL(String insertSQL) {
		this.insertSQL = insertSQL;
	}
	
	
}
