package com.myl.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.myl.modelo.Duelo;

@Repository("dueloDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class DueloDao {

	@Autowired
	SessionFactory sessionFactory;

	public List<Duelo> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Duelo.class)
				.list();
	}

	public Duelo findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Duelo) sessionFactory.getCurrentSession().get(Duelo.class, id);
	}

	public Duelo save(Duelo entity) {
		if (entity.getDueloId() != null) {
			entity = (Duelo) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Duelo entity) {
		entity = (Duelo) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Duelo> findByExample(Duelo example) {
		return (List<Duelo>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}

	public void insertDuel(Integer winner, Integer loser, Integer cant) {
		Query query = sessionFactory
				.getCurrentSession()
				.createSQLQuery(
						"insert into duelo_tbl values(:winner,:loser,:cant)")
				.addEntity(Duelo.class).setParameter("winner", winner)
				.setParameter("loser", loser).setParameter("cant", cant);

		query.executeUpdate();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
