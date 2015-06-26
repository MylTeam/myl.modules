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
import org.springframework.stereotype.Repository;

import com.myl.modelo.Carta;

@Repository("cartaDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class CartaDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Carta> findAll() {		
		return sessionFactory.getCurrentSession().createCriteria(Carta.class).list();
	}

	public Carta findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Carta) sessionFactory.getCurrentSession().get(Carta.class, id);
	}

	public Carta save(Carta entity) {
		if (entity.getId() != null) {
			entity = (Carta) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);		
		return entity;
	}

	public void delete(Carta entity) {
		entity = (Carta) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Carta> findByExample(Carta example) { 
		return (List<Carta>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}
	
	public List<String> findByCriteria() { 		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Carta.class);		
		dCriteria.setProjection(Projections.distinct(Projections.property("raza")));
		return (List<String>) dCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
								
//		return (List<String>) getHibernateTemplate().findByCriteria(dCriteria);
	}
	
	public List<String> findByCriteriaTipo() { 		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Carta.class);		
		dCriteria.setProjection(Projections.distinct(Projections.property("tipo")));
		return (List<String>) dCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
//		return (List<String>) getHibernateTemplate().findByCriteria(dCriteria);
	}
	
	public List<Carta> findByCriterioBusqueda(Carta carta) { 		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Carta.class);
		
		if(carta.getNombre()!=null){
			dCriteria.add(Restrictions.like("nombre", "%"+carta.getNombre()+"%"));
		}
		if(carta.getIdEdicion()!=null){
			dCriteria.add(Restrictions.eq("idEdicion", carta.getIdEdicion()));
		}
		if(carta.getFrecuencia()!=null){
			dCriteria.add(Restrictions.eq("frecuencia", carta.getFrecuencia()));
		}
		if(carta.getTipo()!=null){
			dCriteria.add(Restrictions.eq("tipo", carta.getTipo()));
		}
		if(carta.getCoste()!=null){
			dCriteria.add(Restrictions.eq("coste", carta.getCoste()));
		}
		if(carta.getFuerza()!=null){
			dCriteria.add(Restrictions.eq("fuerza", carta.getFuerza()));
		}
		if(carta.getRaza()!=null){
			dCriteria.add(Restrictions.eq("raza", carta.getRaza()));
		}
		if(carta.getEfecto()!=null){
			dCriteria.add(Restrictions.like("efecto", "%"+carta.getEfecto()+"%"));
		}
		
		return (List<Carta>) dCriteria.getExecutableCriteria(sessionFactory.getCurrentSession()).list();
//		return (List<Carta>) getHibernateTemplate().findByCriteria(dCriteria);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
