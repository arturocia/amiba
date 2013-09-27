package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.TipoRegistro;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("tipoRegistroDao")
public class TipoRegistroDao extends HibernateDaoSupport {
	
	public List<TipoRegistro> findAll() {
		return getHibernateTemplate().loadAll(TipoRegistro.class);
	}

	public TipoRegistro findById(Integer id) {
		return getHibernateTemplate().get(TipoRegistro.class, id);
	}

	public TipoRegistro save(TipoRegistro entity) {
		if (entity.getIdTipoRegistro() != null) {
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(TipoRegistro entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<TipoRegistro> findByExample(TipoRegistro tipoRegistro) {
		return getHibernateTemplate().findByExample(tipoRegistro);
	}

}
