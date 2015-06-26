package com.myl.negocio;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myl.dao.DeckCartaDao;
import com.myl.modelo.DeckCarta;



@Service("deckCartaNegocio")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class DeckCartaNegocio {
	private DeckCartaDao deckCartaDao;	
	
	@Transactional
	public List<DeckCarta> findAll() {
		return deckCartaDao.findAll();
	}

	@Transactional
	public DeckCarta findById(Integer id) {
		return deckCartaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public DeckCarta save(DeckCarta entidad) {
		DeckCarta modelo = deckCartaDao.save(entidad);		
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(DeckCarta entidad) {
		deckCartaDao.delete(entidad);
	}

	@Transactional
	public List<DeckCarta> findByExample(DeckCarta deckCarta) {
		return deckCartaDao.findByExample(deckCarta);
	}
	 
	@Transactional
	public void insertCard(Integer deckId,Integer cartaId,Integer cartaQt){
		deckCartaDao.insertCard(deckId, cartaId, cartaQt);
	}
	
	@Transactional
	public void deleteCardsFromDeck(Integer deckId){
		deckCartaDao.deleteCardsFromDeck(deckId);
	}
	
	@Transactional
	public BigDecimal getDeckSize(Integer deckId){
		return deckCartaDao.getDeckSize(deckId);
	}
	
	public DeckCartaDao getDeckCartaDao() {
		return deckCartaDao;
	}

	public void setDeckCartaDao(DeckCartaDao deckCartaDao) {
		this.deckCartaDao = deckCartaDao;
	}

	
	
}
