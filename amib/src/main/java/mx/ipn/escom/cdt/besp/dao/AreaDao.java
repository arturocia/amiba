package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import mx.ipn.escom.cdt.besp.modelo.Area;

@Singleton
@Named("areaDao")
public class AreaDao extends HibernateDaoSupport {
	// Devuelve la lista de TipoArea a AreasNegocio después de realizar la
	// consulta a SQL (Gestionar Area 9,10,11)

	public List<Area> findAll() {
		return getHibernateTemplate().loadAll(Area.class);
	}

	public Area findById(Integer id) {
		return getHibernateTemplate().get(Area.class, id);
	}

	public Area save(Area entity) {
		getSession().save(entity);
		return entity;
	}
	
	public Area update(Area entity){
		Area area=getHibernateTemplate().merge(entity);
		getSession().update(area);
		return area;
	}

	public void delete(Area entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	// Por la convesrión de tipo List a List<Area>
	public List<Area> findByExample(Area area) {
		return getHibernateTemplate().findByExample(area);
	}

}
