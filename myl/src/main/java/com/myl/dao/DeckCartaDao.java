package com.myl.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.myl.modelo.Carta;
import com.myl.modelo.DeckCarta;

@Repository("deckCartaDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class DeckCartaDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public List<DeckCarta> findAll() {		
		return sessionFactory.getCurrentSession().createCriteria(DeckCarta.class).list();
	}

	public DeckCarta findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (DeckCarta) sessionFactory.getCurrentSession().get(DeckCarta.class, id);
	}

	public DeckCarta save(DeckCarta entity) {
		if (entity.getDeckCartaId() != null) {
			entity = (DeckCarta) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);		
		return entity;
	}

	public void delete(DeckCarta entity) {
		entity = (DeckCarta) sessionFactory.getCurrentSession().merge(entity);
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<DeckCarta> findByExample(DeckCarta example) { 
		return (List<DeckCarta>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}
	
	public void insertCard(Integer deckId,Integer cartaId,Integer cartaQt){
		Query query = sessionFactory.getCurrentSession().createSQLQuery("insert into deckcarta values(:deckId,:cartaId,:cartaQt)")
				.addEntity(DeckCarta.class)
				.setParameter("deckId", deckId).setParameter("cartaId", cartaId).setParameter("cartaQt", cartaQt);
		
		query.executeUpdate();
	}
	
	public void deleteCardsFromDeck(Integer deckId){
		Query query = sessionFactory.getCurrentSession().createSQLQuery("delete from deckcarta where DeckId=:deckId")		
				.addEntity(DeckCarta.class)
				.setParameter("deckId", deckId);
		
		query.executeUpdate();
	}
	
	public BigDecimal getDeckSize(Integer deckId){
		Query query = sessionFactory.getCurrentSession().createSQLQuery("select sum(CartaQt) from deckcarta where DeckId=:deckId")				
				.setParameter("deckId", deckId);
		
		return (BigDecimal) query.uniqueResult();
	}



	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
