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

import com.myl.modelo.Deck;

@Repository("deckDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class DeckDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<Deck> findAll() {		
		return sessionFactory.getCurrentSession().createCriteria(Deck.class).list();
	}

	public Deck findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Deck) sessionFactory.getCurrentSession().get(Deck.class, id);
	}

	public Deck save(Deck entity) {
		if (entity.getDeckId() != null) {
			entity = (Deck) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);		
		return entity;
	}

	public void delete(Deck entity) {
		entity = (Deck) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Deck> findByExample(Deck example) { 
		return (List<Deck>) sessionFactory.getCurrentSession()
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
