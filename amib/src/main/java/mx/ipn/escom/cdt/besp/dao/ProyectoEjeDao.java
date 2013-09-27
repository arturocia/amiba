package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("proyectoEjeDao")
public class ProyectoEjeDao extends HibernateDaoSupport {
	public List<ProyectoEje> findAll() {
		return getHibernateTemplate().loadAll(ProyectoEje.class);
	}

	public ProyectoEje findById(Integer id) {
		return getHibernateTemplate().get(ProyectoEje.class, id);
	}

	public ProyectoEje save(ProyectoEje entity) {
		if (entity.getIdEje() != null) {
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(ProyectoEje entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<ProyectoEje> findByExample(ProyectoEje eje) {
		return getHibernateTemplate().findByExample(eje);
	}
}
