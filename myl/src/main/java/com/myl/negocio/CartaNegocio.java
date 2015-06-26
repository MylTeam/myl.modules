package com.myl.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myl.dao.CartaDao;
import com.myl.modelo.Carta;
import com.myl.modelo.DeckCarta;

@Service("cartaNegocio")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class CartaNegocio {
	private CartaDao cartaDao;	
	
	@Transactional
	public List<Carta> findAll() {
		return cartaDao.findAll();
	}

	@Transactional
	public Carta findById(Integer id) {
		return cartaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Carta save(Carta entidad) {
		Carta modelo = cartaDao.save(entidad);		
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Carta entidad) {
		cartaDao.delete(entidad);
	}

	@Transactional
	public List<Carta> findByExample(Carta carta) {
		return cartaDao.findByExample(carta);
	}
	
	@Transactional
	public List<String> findByCriteria() { 		
		return cartaDao.findByCriteria();
	}
	
	@Transactional
	public List<String> findByCriteriaTipo() { 		
		return cartaDao.findByCriteriaTipo();
	}
	
	@Transactional
	public List<Carta> findByCriterioBusqueda(Carta carta) { 		
		return cartaDao.findByCriterioBusqueda(carta);
	}
	
	public CartaDao getCartaDao() {
		return cartaDao;
	}

	public void setCartaDao(CartaDao cartaDao) {
		this.cartaDao = cartaDao;
	}

	
	
}
