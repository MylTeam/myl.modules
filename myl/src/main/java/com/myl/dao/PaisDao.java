package com.myl.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myl.modelo.Pais;
import com.myl.modelo.Pais;
import com.myl.modelo.Usuario;

@Repository("paisDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PaisDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	public List<Pais> findAll() {		
		System.out.println("buscando paises");
		return sessionFactory.getCurrentSession().createCriteria(Pais.class).list();		
	}

	public Pais findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Pais) sessionFactory.getCurrentSession().get(Pais.class, id);
	}

	public Pais save(Pais entity) {
		if (entity.getId() != null) {
			entity = (Pais) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);		
		return entity;
	}

	public void delete(Pais entity) {
		entity = (Pais) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Pais> findByExample(Pais example) { 
		return (List<Pais>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}

	
}
