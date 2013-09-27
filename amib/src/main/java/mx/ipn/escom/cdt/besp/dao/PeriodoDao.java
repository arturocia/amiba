package mx.ipn.escom.cdt.besp.dao;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Periodo;

@Singleton
@Named("periodoDao")
public class PeriodoDao extends HibernateDaoSupport {

	public List<Periodo> findAll() {
		return getHibernateTemplate().loadAll(Periodo.class);
	}

	public Periodo findById(Integer id) {
		return getHibernateTemplate().get(Periodo.class, id);
	}
	
	public Periodo save(Periodo entity) {
		if (entity.getIdPeriodo() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	public void delete(Periodo entity) {
		if (entity.getIdPeriodo() != null) {
			entity = getHibernateTemplate().merge(entity);
		}
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrion de tipo List a List<Periodo>
	public List<Periodo> findByExample(Periodo periodo) {
		return getHibernateTemplate().findByExample(periodo);
	}

}
