package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.ProyectoEstructura;
import mx.ipn.escom.cdt.besp.modelo.ProyectoEstructuraId;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("proyectoEstructuraDao")
public class ProyectoEstructuraDao extends HibernateDaoSupport {

	public List<ProyectoEstructura> findAll() {
		return getHibernateTemplate().loadAll(ProyectoEstructura.class);
	}

	public ProyectoEstructura findById(ProyectoEstructuraId id) {
		return getHibernateTemplate().get(ProyectoEstructura.class, id);
	}

	public ProyectoEstructura save(ProyectoEstructura entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	public void delete(ProyectoEstructura entity) {
		getHibernateTemplate().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<ProyectoEstructura>
	public List<ProyectoEstructura> findByExample(ProyectoEstructura estructura) {
		return getHibernateTemplate().findByExample(estructura);
	}

}
