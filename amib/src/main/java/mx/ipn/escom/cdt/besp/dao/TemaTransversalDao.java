package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("temaTransversalDao")
public class TemaTransversalDao extends HibernateDaoSupport {

	public List<TemaTransversal> findAll() {
		return getHibernateTemplate().loadAll(TemaTransversal.class);
	}

	public TemaTransversal findById(Integer id) {
		return getHibernateTemplate().get(TemaTransversal.class, id);
	}

	public TemaTransversal save(TemaTransversal entity) {
		getSession().save(entity);
		return entity;
	}
	
	public TemaTransversal update(TemaTransversal entity) {
		TemaTransversal entidadAux = getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(entidadAux);
		return entidadAux;
	}

	public void delete(TemaTransversal entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la conversión de tipo List a List<TemaTransversal>
	public List<TemaTransversal> findByExample(TemaTransversal temaTransversal) {
		return getHibernateTemplate().findByExample(temaTransversal);
	}
}
