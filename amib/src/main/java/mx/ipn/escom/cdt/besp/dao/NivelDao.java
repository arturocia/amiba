package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Nivel;

@Singleton
@Named("nivelDao")
public class NivelDao extends HibernateDaoSupport {

	public List<Nivel> findAll() { 
		return getHibernateTemplate().loadAll(Nivel.class);
	}

	public Nivel findById(Integer id) { 
		return getHibernateTemplate().get(Nivel.class, id);
	}

	public Nivel save(Nivel entity) {
			getSession().saveOrUpdate(entity);
		return entity;
	}
	public Nivel update(Nivel entity) {
		Nivel entityAux=getHibernateTemplate().merge(entity);
		getSession().update(entityAux);
	return entityAux;
	}

	public void delete(Nivel entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la conversión de tipo List a List<Nivel>
	public List<Nivel> findByExample(Nivel nivel) {
		return getHibernateTemplate().findByExample(nivel);
	}
}
