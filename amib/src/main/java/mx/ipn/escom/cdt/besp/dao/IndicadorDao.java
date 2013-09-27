package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.*;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.*;

@Singleton
@Named("indicadorDao")
public class IndicadorDao extends HibernateDaoSupport {
	
	public List<Indicador> findAll() {
		return getHibernateTemplate().loadAll(Indicador.class);
	}

	public Indicador findById(Integer id) {
		return getHibernateTemplate().get(Indicador.class, id);
	}

	public Indicador save(Indicador entity) {
		getSession().save(entity);
		return entity;
	}

	public Indicador update(Indicador entity){
		Indicador indicador=getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(indicador);
		return indicador;
	}
	public void delete(Indicador entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Area>
	public List<Indicador> findByExample(Indicador indicador) {
		return getHibernateTemplate().findByExample(indicador);
	}

}
