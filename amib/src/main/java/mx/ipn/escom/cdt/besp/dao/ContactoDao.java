package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.Contacto;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("contactoDao")
public class ContactoDao extends HibernateDaoSupport {

	public List<Contacto> findAll() {

		return getHibernateTemplate().loadAll(Contacto.class);
	}

	public Contacto findById(Integer id) {
		return getHibernateTemplate().get(Contacto.class, id);
	}

	public Contacto save(Contacto entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	public Contacto update(Contacto entity) {
		getHibernateTemplate().refresh(entity);
		getHibernateTemplate().merge(entity);
		getSession().update(entity);
		return entity;
	} 

	public void delete(Contacto entity) {
		Query query;
		query = getSession().getNamedQuery("eliminaContacto");
		query.setInteger("id", entity.getIdContacto());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	// Por la convesri�n de tipo List a List<Contacto>
	public List<Contacto> findByExample(Contacto contacto) {
		return getHibernateTemplate().findByExample(contacto);
	}

}
