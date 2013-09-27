package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.AvanceFinanciero;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("avanceFinancieroDao")
public class AvanceFinancieroDao extends HibernateDaoSupport{

	public List<AvanceFinanciero> findAll() {
		return getHibernateTemplate().loadAll(AvanceFinanciero.class);
	}

	public AvanceFinanciero findById(Integer id) {
		return getHibernateTemplate().get(AvanceFinanciero.class, id);
	}

	public AvanceFinanciero save(AvanceFinanciero entity) {
		if(entity.getIdAvance()!=null){
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(AvanceFinanciero entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}


	@SuppressWarnings("unchecked")
	public List<AvanceFinanciero> findByExample(AvanceFinanciero avanceFinanciero) {
		return getHibernateTemplate().findByExample(avanceFinanciero);
	}
	
}
