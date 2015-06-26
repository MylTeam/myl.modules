package com.myl.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.myl.modelo.Edicion;
import com.myl.modelo.Edicion;


@Repository("edicionDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class EdicionDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Edicion> findAll() {		
		return sessionFactory.getCurrentSession().createCriteria(Edicion.class).list();
	}

	public Edicion findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Edicion) sessionFactory.getCurrentSession().get(Edicion.class, id);
	}

	public Edicion save(Edicion entity) {
		if (entity.getId() != null) {
			entity = (Edicion) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);		
		return entity;
	}

	public void delete(Edicion entity) {
		entity = (Edicion) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Edicion> findByExample(Edicion example) { 
		return (List<Edicion>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
