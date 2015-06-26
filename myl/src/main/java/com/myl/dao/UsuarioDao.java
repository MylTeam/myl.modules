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

import com.myl.modelo.Usuario;

@Repository("usuarioDao")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class UsuarioDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Usuario> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Usuario.class).list();
//		return getHibernateTemplate().loadAll(Usuario.class);
	}

	public Usuario findById(Integer id) {
		sessionFactory.getCurrentSession().clear();
		return (Usuario) sessionFactory.getCurrentSession().get(Usuario.class, id);
//		return getHibernateTemplate().get(Usuario.class, id);
	}

	public Usuario save(Usuario entity) {
		if (entity.getIdUsuario() != null) {
			entity = (Usuario) sessionFactory.getCurrentSession().merge(entity);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
		return entity;
	}	

	@SuppressWarnings("unchecked")
	public List<Usuario> findByExample(Usuario example) {
		return (List<Usuario>) sessionFactory.getCurrentSession()
				.createCriteria(example.getClass())
				.add(Example.create(example)).list();
//		return getHibernateTemplate().findByExample(usuario);
	}

	public List<Usuario> findByName(String s) {
		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from usuario where UsuarioNb=:s")
				.addEntity(Usuario.class).setParameter("s", s);
		return query.list();
	}
	
	public List<Usuario> findFirstX(Integer i) {
		Query query = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from usuario order by dl_won desc")
				.addEntity(Usuario.class).setMaxResults(i);
		return query.list();
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	

}
