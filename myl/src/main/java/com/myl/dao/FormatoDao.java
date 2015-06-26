package com.myl.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.myl.modelo.Formato;


@Repository("formatoDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class FormatoDao {
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Formato> findAll() {		
		return sessionFactory.getCurrentSession().createCriteria(Formato.class).list();
	}

	public Formato findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Formato) sessionFactory.getCurrentSession().get(Formato.class, id);
	}

	public Formato save(Formato entity) {
		if (entity.getId() != null) {
			entity = (Formato) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);		
		return entity;
	}

	public void delete(Formato entity) {
		entity = (Formato) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Formato> findByExample(Formato example) { 
		return (List<Formato>) sessionFactory.getCurrentSession()
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
