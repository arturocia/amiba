package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("proyectoTemaDao")
public class ProyectoTemaDao extends HibernateDaoSupport {
	public List<ProyectoTema> findAll() {
		return getHibernateTemplate().loadAll(ProyectoTema.class);
	}

	public ProyectoTema findById(Integer id) {
		return getHibernateTemplate().get(ProyectoTema.class, id);
	}

	public ProyectoTema save(ProyectoTema entity) {
		if (entity.getIdTema() != null) {
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(ProyectoTema entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ProyectoTema> findByExample(ProyectoTema tema) {
		return getHibernateTemplate().findByExample(tema);
	}
}
