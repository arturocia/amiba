package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;

@Singleton
@Named("perfilUsuarioDao")
public class PerfilUsuarioDao extends HibernateDaoSupport {

	public List<PerfilUsuario> findAll() {
		return getHibernateTemplate().loadAll(PerfilUsuario.class);
	}

	public PerfilUsuario findById(Integer id) {
		return getHibernateTemplate().get(PerfilUsuario.class, id);
	}

	public PerfilUsuario save(PerfilUsuario entity) {
		if (entity.getIdPerfilUsuario() != null) {
			getHibernateTemplate().merge(entity);
		}
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(PerfilUsuario entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	// Por la convesri�n de tipo List a List<TipoUnidad>
	public List<PerfilUsuario> findByExample(PerfilUsuario perfilUsuario) {
		return getHibernateTemplate().findByExample(perfilUsuario);
	}
}
