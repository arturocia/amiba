package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.*;

@Singleton
@Named("programaDao")
public class ProgramaDao extends HibernateDaoSupport {
	public List<Programa> findAll() {
		return getHibernateTemplate().loadAll(Programa.class);
	}

	public Programa findById(Integer id) {
		return getHibernateTemplate().get(Programa.class, id);
	}

	public Programa save(Programa entity) {
		if (entity.getIdPrograma() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Programa entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<TipoUnidad>
	public List<Programa> findByExample(Programa programa) {
		return getHibernateTemplate().findByExample(programa);
	}
}
