package mx.ipn.escom.cdt.besp.dao;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Accion;

@Singleton
@Named("accionDao")
public class AccionDao extends HibernateDaoSupport {
	
	public List<Accion> findAll() {
		return getHibernateTemplate().loadAll(Accion.class);
	}

	public Accion findById(Integer id) {
		return getHibernateTemplate().get(Accion.class, id);
	}

	public Accion save(Accion entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	public Accion update(Accion entity){
		Accion entidad=getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(entidad);
		return entidad;
	}

	public void delete(Accion entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<Accion> findByExample(Accion accion) {
		return getHibernateTemplate().findByExample(accion);
	}

}
