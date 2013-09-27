package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.*;

@Singleton
@Named("unidadDao")
public class UnidadDao extends HibernateDaoSupport {

	public List<Unidad> findAll() { 
		return getHibernateTemplate().loadAll(Unidad.class);
	}

	public Unidad findById(Integer id) { 
		return getHibernateTemplate().get(Unidad.class, id);
	}

	public Unidad save(Unidad entity) { 
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Unidad entity) { 
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la conversion de tipo List a List<TipoUnidad>
	public List<Unidad> findByExample(Unidad unidad) { 
		return getHibernateTemplate().findByExample(unidad);
	}
}
