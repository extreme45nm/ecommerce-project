package edu.java.spring.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;

import org.apache.taglibs.standard.tag.common.sql.DriverTag;
import org.springframework.web.context.ContextLoaderListener;

import edu.java.spring.dao.KeyboardDAO;

public class CustomContextLoaderListener extends ContextLoaderListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
//		getCurrentWebApplicationContext().getBean(KeyboardImpl.class).shutdown();
//		KeyboardDAO kbDAO = (KeyboardDAO)getCurrentWebApplicationContext().getBean("keyboardDAO");
//		if(kbDAO != null) kbDAO.shutdown();
		
		try{
			System.out.println("===> hibernate shutdown database");
			DriverManager.getConnection("jdbc:sqlserver://localhost:49854;databaseName=ecommerce;username=sa;password=congminh"
					+ ";shutdown=true");			
		}catch(SQLException exc){
			exc.printStackTrace();
		}		
		System.out.println("\n\n Destroyed \n\n");
		super.contextDestroyed(event);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("\n\n Inited!!! \n\n");
		try{
			createTables();
		}catch(Exception exc){
			exc.printStackTrace();
		}
		super.contextInitialized(event);
	}

	
	private static void createTables() throws ClassNotFoundException, SQLException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection connection = 
			DriverManager.getConnection("jdbc:sqlserver://localhost:49854;"
					+ "databaseName=ecommerce;create=true;"
					+"username=sa;"
					+"password=congminh");
			
		try{
			createTableIfNotExist(connection,"keyboard","CREATE TABLE keyboard("+
						"productId int primary key,"+
						"name varchar(50) not null,"+
						"manufacturerId int not null,"+
						"switchId int not null,"+
						"profileId int not null,"+
						"price int"+ 
						")");
			createTableIfNotExist(connection,"manufacturer","CREATE TABLE manufacturer("+
					"manufacturerId int IDENTITY(1,1) primary key,"+
					"name varchar(50) not null,"+
					"location varchar(100) not null"+ 
					")");
			createTableIfNotExist(connection, "switch", "CREATE TABLE switch("
					+ "switchId int IDENTITY(1,1) primary key,"
					+ "manufacturerId int not null,"
					+ "switchName varchar(50) not null,"
					+ "switchForce int not null"
					+ ")");
			
		}finally{
			connection.close();			
		}
	}
	
	private static void createTableIfNotExist(Connection connection, String tableName,String createTableSql) throws SQLException{
		DatabaseMetaData dbmd = connection.getMetaData();
		ResultSet rs = dbmd.getTables(null, null,tableName.toUpperCase(), null);
		if(rs.next()){
			System.out.println("Table "+rs.getString("TABLE_NAME")+" already exist");
			return;			
		}
		
		Statement statement = connection.createStatement();
		try{
			statement.execute(createTableSql);
			System.out.println("\n\n executed sql Command!\n\n");
		}finally{
			statement.close();
		}
	}
}
