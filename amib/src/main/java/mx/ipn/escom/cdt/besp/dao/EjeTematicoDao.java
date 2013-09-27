package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.EjeTematico;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("ejeTematicoDao")

public class EjeTematicoDao extends HibernateDaoSupport {
	//Devuelve la lista de TipoArea a AreasNegocio después de realizar la consulta a SQL (Gestionar Area 9,10,11)
	public List<EjeTematico> findAll() { 
		return getHibernateTemplate().loadAll(EjeTematico.class);
	}

	public EjeTematico findById(Integer id) { 
		return getHibernateTemplate().get(EjeTematico.class, id);
	}

	public EjeTematico save(EjeTematico entity) {
		getSession().save(entity);
		return entity;
	}
	
	public EjeTematico update(EjeTematico entity) {
		EjeTematico entidadAux = getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(entidadAux);
		return entidadAux;
	}

	public void delete(EjeTematico entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Area>
	public List<EjeTematico> findByExample(EjeTematico ejetematico) {
		return getHibernateTemplate().findByExample(ejetematico);
	}

}
