package edu.java.spring.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import edu.java.spring.dao.ManufacturerDAO;
import edu.java.spring.model.Manufacturer;
import edu.java.spring.model.ManufacturerMapper;

public class ManuHibernateDAOImpl implements ManufacturerDAO {
	
	@Autowired
	private LocalSessionFactoryBean sessionFactory;
	
	@Autowired
	@Qualifier("manufacturerMapper") ManufacturerMapper mapper;
	
	private static Log log = LogFactory.getLog(ManuHibernateDAOImpl.class);
//	private String insertSQL;
	
	
	@Override
	public Manufacturer loadManufacturer(int manufacturerId) {
		Session session = sessionFactory.getObject().openSession();
		Manufacturer manu = (Manufacturer)session.load(Manufacturer.class, new Integer(manufacturerId));
		log.info("Load Manufacturer with id = "+manufacturerId);
		return manu;
	}
	
	
	@Override
	public List<Manufacturer> listManufacturer() {
		Session session = sessionFactory.getObject().openSession();
		@SuppressWarnings("unchecked")
		Query<Manufacturer> query = session.createQuery("from Manufacturer");
		try{
			return (List<Manufacturer>)query.getResultList();
		}finally{
			session.close();
		}
	}
	
	
	@Override
	public void delete(int manufacturerId) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx=session.beginTransaction();
			Manufacturer manu = (Manufacturer)session.load(Manufacturer.class, new Integer(manufacturerId));
			if(manu!=null) session.delete(manu);
			tx.commit();
			log.info("deleted manufacturer with id = "+manufacturerId);
		}catch(HibernateException exc){
			if(tx != null)tx.rollback();
			log.error(exc.toString());
		}finally{
			session.close();
		}
		
	}
	
	
	@Override
	public void insert(final Manufacturer manu) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(manu);
			tx.commit();
			log.info("CREATED manufacturer with id = "+manu.getManufacturerId());
		}catch(Exception exc){
			if(tx != null) tx.rollback();
			log.error(exc.toString());
		}finally {
			session.close();
		}
	}
	
	@Override
	public void update(Manufacturer newManu) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Manufacturer manu = (Manufacturer)session.merge(newManu);
			session.update(manu);
			tx.commit();
			log.info("UPDATED manufacturer with id = "+newManu.getManufacturerId());
			session.flush();
		}catch(HibernateException exc){
			if(tx != null) tx.rollback();
			log.error(exc.toString());
		}finally{
			session.close();
		}
	}
	
	
	
}
