package com.myl.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myl.dao.PaisDao;
import com.myl.modelo.Pais;

@Service("paisNegocio")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class PaisNegocio {
	private PaisDao paisDao;	
	
	@Transactional
	public List<Pais> findAll() {
		return paisDao.findAll();
	}

	@Transactional
	public Pais findById(Integer id) {
		return paisDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Pais save(Pais entidad) {
		Pais modelo = paisDao.save(entidad);		
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Pais entidad) {
		paisDao.delete(entidad);
	}

	@Transactional
	public List<Pais> findByExample(Pais pais) {
		return paisDao.findByExample(pais);
	}

	@Transactional
	public Boolean existe(String nombre) { 
		Pais paisEjemplo = new Pais();
		List<Pais> paises = null;
		paisEjemplo.setNombre(nombre);
		paises = findByExample(paisEjemplo);
		if (paises != null && !paises.isEmpty()) {
			return true;
		}
		return false;
	}
	
     
	public PaisDao getPaisDao() {
		return paisDao;
	}

	public void setPaisDao(PaisDao paisDao) {
		this.paisDao = paisDao;
	}

	
	
}
