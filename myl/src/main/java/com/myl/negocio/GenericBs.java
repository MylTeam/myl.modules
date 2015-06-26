package com.myl.negocio;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myl.dao.GenericDao;
import com.myl.exception.BusinessException;

/**
 * Permite relizar acciones del negocio para los objetos que pertenecen a
 * cualquier tipo de catálogo
 * 
 * @author HESP
 * @version 1.0 16 mayo 2014
 * @param <T>
 */
@Service("genericBs")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class GenericBs {
	
	/**
	 * Atributo que inyecta spring para permitir el acceso a los métodos del
	 * dao para su implementación
	 */
	@Autowired
	private GenericDao genericDao;

	/**
	 * Metodo busca todos los objetos de una determinada clase. Implenta al
	 * findAll del genericDao
	 * 
	 * @param clazz
	 *            : Clase que referencia al objeto que se busca.
	 * @return Lista de todos los objeto peristentes que se buscan en la base
	 */
	@Transactional(readOnly = true)
	public <C> List<C> findAll(Class<C> clazz) {
		return genericDao.findAll(clazz);
	}

	/**
	 * Método que busca un objeto apartir de su identificador. Implenta al
	 * findById del genericDao
	 * 
	 * @param <C>
	 * 
	 * @param id
	 *            : Identificador del objeto que se busca.
	 * @param clazz
	 *            : Clase que representa al objeto que se busca.
	 * @return Objeto correspondiente al Id que se busca
	 */
	@Transactional(readOnly = true)
	public <C> C findById(Class<C> clazz, Serializable id) {
		return genericDao.findById(clazz, id);
	}

	/**
	 * Metodo que busca a todos los objetos similares apartir de un objeto
	 * ejemplo. Implementa al findByExample del genericDao .
	 * 
	 * @param o
	 *            :bject: objeto que se busca como ejemplo.
	 * @return Lista de los objeto persistentes que se encontraron
	 */
	@Transactional(readOnly = true)
	public <C> List<C> findByExample(C example) {
		return genericDao.findByExample(example);
	}

	/**
	 * Método que guarda un objeto . Implemneta al metodosave del GenerciDao
	 * 
	 * @param C
	 *            : Objeto a guardar
	 * @return objeto guardado
	 */
	@Transactional(rollbackFor = Exception.class)
	public <C> C save(C entity) throws BusinessException {
		return genericDao.save(entity);
	}

	/**
	 * Método que actualiza un objeto de una determinada clase Implementa al
	 * update del GenerciDao
	 * 
	 * @param object
	 *            : Objeto a actulizar,
	 * @return Objeto actulizado
	 */
	@Transactional(rollbackFor = Exception.class)
	public <C> C update(C entity) throws BusinessException {
		return genericDao.update(entity);
	}

	/**
	 * Método que elimina un objeto Implementa al delete del GenericDao
	 * 
	 * @param object
	 *            : Objeto a eliminar.
	 */
	@Transactional(rollbackFor = Exception.class)
	public <C> void delete(C entity) throws BusinessException {
		genericDao.delete(entity);
	}

	/**
	 * Cuenta el número de elementos que existen de la clase T Implementa el
	 * método count() de la clase GenericDao
	 * 
	 * @param número
	 *            de elementos
	 */
	public <C> Integer count(Class<C> clazz) {
		return genericDao.count(clazz);
	}

	/**
	 * Verifica si existe un objeto del tipo T con el mismo nombre
	 * 
	 * @param {@link Class} clazz: Clase de la que se ha de buscar el nombre
	 * @param {@link String} nombre: Nombre a buscar
	 * 
	 * @return true si es que le elemento existe
	 */
	public <C> Boolean existeNombre(Class<C> clazz, String nombre) {
		return genericDao.existByName(clazz, nombre);
	}

	@Transactional(readOnly = true)
	public <C> Boolean existeAtributo(Class<C> clazz, String atributo,
			String nombre) {
		return genericDao.existByAttribute(clazz, atributo, nombre);
	}

	@Transactional(readOnly = true)
	public <C> Boolean existeAtributo(Class<C> clazz, String nombreAtributo,
			String nombreId, String nombre, Integer id) {
		return genericDao.existByAttribute(clazz, nombreAtributo, nombreId,
				nombre, id);
	}

	/**
	 * @return the genericDao
	 */
	public GenericDao getGenericDao() {
		return genericDao;
	}

	/**
	 * @param genericDao
	 *            the genericDao to set
	 */
	public void setGenericDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}
}