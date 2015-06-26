package com.myl.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myl.dao.DeckDao;
import com.myl.modelo.Carta;
import com.myl.modelo.Deck;
import com.myl.modelo.DeckCarta;
import com.myl.modelo.Edicion;
import com.myl.modelo.Formato;

@Service("deckNegocio")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class DeckNegocio {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeckNegocio.class);
	private DeckDao deckDao;
	private CartaNegocio cartaNegocio;
	private Carta carta;
	private Formato formato;
	private FormatoNegocio formatoNegocio;

	@Transactional
	public List<Deck> findAll() {
		return deckDao.findAll();
	}

	@Transactional
	public Deck findById(Integer id) {
		return deckDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Deck save(Deck entidad) {
		Deck modelo = deckDao.save(entidad);
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Deck entidad) {
		deckDao.delete(entidad);
	}

	@Transactional
	public List<Deck> findByExample(Deck deck) {
		return deckDao.findByExample(deck);
	}

	public String isCorrectFormat(List<DeckCarta> deckCartas, Integer formatoId) {
		String cards="";
		String edicionesNombre="";
		String issue="";
		
		if(formatoId!=-1){
		List<Carta> deckTest = new ArrayList<Carta>();
		for (DeckCarta deckCarta : deckCartas) {
			carta = cartaNegocio.findById(deckCarta.getCartaId());
			Carta aux = new Carta();
			aux.setId(carta.getId());
			aux.setNombre(carta.getNombre());
			aux.setIdEdicion(carta.getIdEdicion());
			deckTest.add(aux);
		}
		
		formato=formatoNegocio.findById(formatoId);		
		List<Integer> ediciones=new ArrayList<Integer>();		
		for(Edicion edicion:formato.getEdiciones()){
			ediciones.add(edicion.getId());
			edicionesNombre=edicionesNombre+edicion.getNombre()+", ";			
		}
		
		for(Carta carta:deckTest){			
			if(ediciones.contains(carta.getIdEdicion())){
				LOGGER.debug("Ok: "+carta.getNombre());
			}else{
				LOGGER.debug("Error: "+carta.getNombre());
				cards=cards+carta.getNombre()+", ";
			}
		}
		if(!cards.isEmpty()){
			issue="Las cartas "+cards+"no pertenecen al formato seleccionado. \nLas ediciones permitidas para este formato son: "+edicionesNombre;
		}
		}

		return issue;
	}

	public DeckDao getDeckDao() {
		return deckDao;
	}

	public void setDeckDao(DeckDao deckDao) {
		this.deckDao = deckDao;
	}

	public CartaNegocio getCartaNegocio() {
		return cartaNegocio;
	}

	public void setCartaNegocio(CartaNegocio cartaNegocio) {
		this.cartaNegocio = cartaNegocio;
	}

	public Carta getCarta() {
		return carta;
	}

	public void setCarta(Carta carta) {
		this.carta = carta;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public FormatoNegocio getFormatoNegocio() {
		return formatoNegocio;
	}

	public void setFormatoNegocio(FormatoNegocio formatoNegocio) {
		this.formatoNegocio = formatoNegocio;
	}

}
