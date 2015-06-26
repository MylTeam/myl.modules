package com.myl.dao;


import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Permite realizar acciones de persistencia y busqueda de objetos 
 *   
 */
@Repository("genericDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class GenericDao {
	/**
	 * Permite el manejo de la session de hibernate
	 * */ 
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * Método que regresa una lista de todos los objetos. 
	 * 
	 * @param clazz: Clase que representa al tipo de objeto 
	 * @return Lista de todos los objetos de tipo clazz.
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <C> List<C> findAll(Class<C> clazz) {
		return  sessionFactory.getCurrentSession()
				.createCriteria(clazz).list();
	}

	/**
	 * Método que retorna un Objeto a partir de su identificador 
	 * 
	 * @param clazz : Clase que representa al tipo de objeto
	 * @param id: Identificador del objeto que se busca
	 * @return C: Instancia del objeto Clazz correspondiente al Id
	 */
	@SuppressWarnings("unchecked")
	public <C, Pk> C findById(Class<C> clazz, Serializable id) {
		sessionFactory.getCurrentSession().clear();
		return (C) sessionFactory.getCurrentSession().get(clazz, id);
	}

	/**
	 * metodo que recibe un objeto como ejemplo y retorna una lista de objetos correspondientes al dato ingresado
	 */
	@SuppressWarnings("unchecked")
	public <C> java.util.List<C> findByExample(C example) {
		return (List<C>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
	}

	/**
	 * Método que guarda a un objeto 
	 * 
	 * @param objet: Objeto que se hace persistente 
	 * @return Objeto persistente.
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public <C> C save(C entity) {
		sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	/**
	 * Método que actualiza a un objeto persistenete 
	 * 
	 * @param object: Objeto que se va a actulizar. 
	 * @return Objeto persistente actulizado
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public <C> C update(C entity) {
		System.out.println("en generico update");
		sessionFactory.getCurrentSession().update(entity);
		return entity;
	}

	/**
	 * Método que elimina a un objeto persistente 
	 * 
	 * @param object: Objeto a eliminar
	 */
	@Transactional(propagation = Propagation.MANDATORY)
	public <C> void delete(C entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

	/**
	 * Cuenta el número de elementos que se encuentran registrados correspondientes a la clase C
	 */
	public <C> Integer count(Class<C> clazz) {
		return ((Number) sessionFactory.getCurrentSession()
				.createCriteria(clazz.getCanonicalName())
				.setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}
	
	/**
	 * Devuelve true si existe almenos un elemento de tipo C con el nombre
	 * especificado.
	 * 
	 * @param clazz
	 * @param nombre
	 * @return
	 */
	public <C > Boolean existByName(Class<C> clazz, String nombre) {
		@SuppressWarnings("unchecked")
		List<C> result = sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.add(Restrictions.eq("nombre", nombre).ignoreCase()).list();
		return !result.isEmpty();
	}

	public <C > Boolean existByAttribute(Class<C> clazz,
			String atributo, String nombre) {
		@SuppressWarnings("unchecked")
		List<C> result = sessionFactory.getCurrentSession()
				.createCriteria(clazz)
				.add(Restrictions.eq(atributo, nombre).ignoreCase()).list();
		return !result.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public <C > Boolean existByAttribute(Class<C> clazz,
			String nombreAtributo, String nombreId, String nombre1, Integer id) {

		sessionFactory.getCurrentSession().clear();
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				clazz);
		criteria.add(Restrictions.eq(nombreAtributo, nombre1).ignoreCase())
				.add(Restrictions.ne(nombreId, id));
		List<C> result = criteria.list();

		return !result.isEmpty();
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}