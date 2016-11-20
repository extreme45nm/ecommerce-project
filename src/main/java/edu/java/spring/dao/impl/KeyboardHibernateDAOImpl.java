package edu.java.spring.dao.impl;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.HEMLogging;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.fasterxml.classmate.util.ResolvedTypeCache.Key;

import edu.java.spring.dao.KeyboardDAO;
import edu.java.spring.model.Keyboard;
import edu.java.spring.model.KeyboardMapper;

public class KeyboardHibernateDAOImpl implements KeyboardDAO{
	
	@Autowired
	private LocalSessionFactoryBean sessionFactory;
	
	@Autowired @Qualifier("keyboardMapper") KeyboardMapper mapper;
	
	private static Log log = LogFactory.getLog(KeyboardHibernateDAOImpl.class);
	private String insertSQL;
	
	@Override
	public void insertKeyboard(Keyboard keyboard) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;		
		try{
			tx = session.beginTransaction();
			session.save(keyboard);
			tx.commit();
		}catch(HibernateException exc){
			if(tx != null) tx.rollback();
			log.error(exc);
		}
		finally{
			session.close();
		}
	}



	@Override
	public List<Keyboard> listKeyboard() {
		
		Session session = sessionFactory.getObject().openSession();
		@SuppressWarnings("unchecked")
		Query<Keyboard> query = session.createQuery("from Keyboard");
		try{
			return (List<Keyboard>)query.getResultList();
		}finally{
			session.close();
		}
	}



	@Override
	public Keyboard loadKeyBoard(int productId) {
		
		Session session = sessionFactory.getObject().openSession();
		Keyboard kb = (Keyboard)session.load(Keyboard.class, new Integer(productId));
		log.info("Keyboard with id = "+productId+" loaded sucessfullt");
		return kb;
	}



	@Override
	public void updateKeyboard(Keyboard keyboard) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Keyboard kb = (Keyboard)session.merge(keyboard);
			session.update(kb);
			tx.commit();
			session.flush();			
		}catch(HibernateException exc){
			if(tx != null) tx.rollback(); 
			log.error(exc);
		}finally {
			session.close();
		}
	}



	@Override
	public void deleteKeyboard(Integer productId) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();		
			Keyboard kb = (Keyboard)session.load(Keyboard.class, new Integer(productId));
			if( kb != null)	session.delete(kb);
			tx.commit();
			log.info("Deleted keyboard with id = "+productId);
		}catch(HibernateException exc){
			if(tx != null )tx.rollback();
			log.error(exc);
		}finally{
			session.close();
		}
	}

	

	@Override
	public List<Keyboard> searchKeyboardByName(String keyboardName) {

		Session session = sessionFactory.getObject().openSession();
		try{
			@SuppressWarnings("unchecked")
			Query<Keyboard> query = session.createQuery("from Keyboard WHERE name like :keyboardName");
			query.setParameter("keyboardName","%"+keyboardName+"%");
			List<Keyboard> kb = query.getResultList();
			return kb;
		}finally{
			session.close();
		}
	}
	

	public String getInsertSQL() {
		return insertSQL;
	}
	public void setInsertSQL(String insertSQL) {
		this.insertSQL = insertSQL;
	}
}
