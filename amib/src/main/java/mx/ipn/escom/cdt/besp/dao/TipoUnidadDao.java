package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.*;

@Singleton
@Named("tipoUnidadDao")
public class TipoUnidadDao extends HibernateDaoSupport {
	public List<TipoUnidad> findAll() {
		return getHibernateTemplate().loadAll(TipoUnidad.class);
	}

	public TipoUnidad findById(Integer id) {
		return getHibernateTemplate().get(TipoUnidad.class, id);
	}

	public TipoUnidad save(TipoUnidad entity) {
		if (entity.getIdTipoUnidad() != null) {
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(TipoUnidad entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	@SuppressWarnings("unchecked")
	public List<TipoUnidad> findByExample(TipoUnidad tipoUnidad) {
		return getHibernateTemplate().findByExample(tipoUnidad);
	}

}
