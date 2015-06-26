package com.myl.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myl.dao.DueloDao;
import com.myl.modelo.Duelo;

@Service("dueloNegocio")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class DueloNegocio {
	private DueloDao dueloDao;	
	
	@Transactional
	public List<Duelo> findAll() {
		return dueloDao.findAll();
	}

	@Transactional
	public Duelo findById(Integer id) {
		return dueloDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Duelo save(Duelo entidad) {
		Duelo modelo = dueloDao.save(entidad);		
		return modelo;
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Duelo entidad) {
		dueloDao.delete(entidad);
	}

	@Transactional
	public List<Duelo> findByExample(Duelo duelo) {
		return dueloDao.findByExample(duelo);
	}
	
	@Transactional
	public void insertDuel(Integer winner,Integer loser,Integer cant) {
		dueloDao.insertDuel(winner, loser, cant);
	}
	
	public DueloDao getDueloDao() {
		return dueloDao;
	}

	public void setDueloDao(DueloDao dueloDao) {
		this.dueloDao = dueloDao;
	}

	
	
}
