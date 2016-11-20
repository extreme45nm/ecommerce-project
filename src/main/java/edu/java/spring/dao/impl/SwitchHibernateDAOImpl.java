package edu.java.spring.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import edu.java.spring.dao.SwitchDAO;
import edu.java.spring.model.Switch;
import edu.java.spring.model.SwitchMapper;

public class SwitchHibernateDAOImpl implements SwitchDAO{

	@Autowired
	LocalSessionFactoryBean sessionFactory;
	
	@Autowired
	@Qualifier("switchMapper")SwitchMapper switchMapper;
	
	private static Log log = LogFactory.getLog(SwitchHibernateDAOImpl.class);
	
	
	@Override
	public void insertSwitch(final Switch newSwitch) {
		 Session session = sessionFactory.getObject().openSession();
		 Transaction tx = null;
		 try{
			 tx = session.beginTransaction();
			 session.save(newSwitch);
			 tx.commit();
			 log.info("CREATED switch with id = "+newSwitch.getSwitchName());
		 }catch(HibernateException exc){
			 if(tx != null) tx.rollback();
			 log.error(exc.toString());
		 }finally{
			 session.close();
		 }
		
	}

	@Override
	public Switch loadSwitch(int switchId) {
		Session session = sessionFactory.getObject().openSession();
		Switch sw = (Switch)session.load(Switch.class, new Integer(switchId));
		return sw;		
	}

	@Override
	public List<Switch> listSwitch() {
		Session session = sessionFactory.getObject().openSession();
		@SuppressWarnings("unchecked")
		Query<Switch> query = session.createQuery("from Switch");
		try{
			return (List<Switch>)query.getResultList();
		}finally{
			session.close();
		}
	}

	@Override
	public void deleteSwitch(Integer switchId) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Switch sw = (Switch)session.load(Switch.class, new Integer(switchId));
			if(sw != null) session.delete(sw);
			tx.commit();
			log.info("DELETED switch with id = "+switchId);
		}catch(HibernateException exc){
			if(tx != null) tx.rollback();
			log.error(exc.toString());
		}finally{
			session.close();
		}
		
		
		
	}

	@Override
	public void updateSwitch(Switch modifiedSwitch) {
		Session session = sessionFactory.getObject().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Switch sw = (Switch)session.merge(modifiedSwitch);
			session.update(sw);
			tx.commit();
			log.info("UPDATED switch with id = "+modifiedSwitch.getSwitchId());
			session.flush();
		}catch(HibernateException exc){
			if (tx != null) tx.rollback();
			log.error(exc.toString());
		}finally{
			session.close();
		}
	}
}
