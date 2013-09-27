package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.TipoContacto;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("tipoContactoDao")
public class TipoContactoDao extends HibernateDaoSupport {

	public List<TipoContacto> findAll() {
		return getHibernateTemplate().loadAll(TipoContacto.class);
	}

	public TipoContacto findById(Integer id) {
		return getHibernateTemplate().get(TipoContacto.class, id);
	}

	public TipoContacto save(TipoContacto entity) {
		getSession().save(entity);
		return entity;
	}
	
	public TipoContacto update(TipoContacto entity) {
		TipoContacto entidadAux = getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(entidadAux);
		return entidadAux;
	}

	public void delete(TipoContacto entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// conversion de tipo List a List<Grupo>
	public List<TipoContacto> findByExample(TipoContacto grupo) {
		return getHibernateTemplate().findByExample(grupo);
	}
}
