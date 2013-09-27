package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("indicadorFinancieroDao")
public class IndicadorFinancieroDao extends HibernateDaoSupport {

	public List<IndicadorFinanciero> findAll() {
		return getHibernateTemplate().loadAll(IndicadorFinanciero.class);
	}

	public IndicadorFinanciero findById(Integer id) {
		return getHibernateTemplate().get(IndicadorFinanciero.class, id);
	}

	public IndicadorFinanciero save(IndicadorFinanciero entity) {
		getSession().save(entity);
		return entity;
	}
	
	public IndicadorFinanciero update(IndicadorFinanciero entity){
		IndicadorFinanciero entidad= getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(entidad);
		return entidad;
	}

	public void delete(IndicadorFinanciero entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	@SuppressWarnings("unchecked")
	public List<IndicadorFinanciero> findByExample(IndicadorFinanciero indicadorFinanciero) {
		return getHibernateTemplate().findByExample(indicadorFinanciero);
	}

}
