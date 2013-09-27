package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Bitacora;

@Singleton
@Named("bitacoraDao")
public class BitacoraDao extends HibernateDaoSupport {

	public List<Bitacora> findAll() {
		return getHibernateTemplate().loadAll(Bitacora.class);
		
	}

	public Bitacora findById(Integer id) {
		return getHibernateTemplate().get(Bitacora.class, id);
	}

	public Bitacora save(Bitacora entity) {
		if (entity.getIdRegistro() != null) {
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Bitacora entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Bitacora> findByExample(Bitacora bitacora) {
		return getHibernateTemplate().findByExample(bitacora);
	}
}
