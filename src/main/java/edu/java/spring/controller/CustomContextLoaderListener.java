package edu.java.spring.controller;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import edu.java.spring.dao.KeyboardDAO;

public class CustomContextLoaderListener extends ContextLoaderListener{
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		getCurrentWebApplicationContext().getBean(KeyboardDAO.class).shutdown();
		System.out.println("\n\n Destroyed \n\n");
		super.contextDestroyed(event);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("\n\n Inited!!! \n\n");
		super.contextInitialized(event);
	}

}
