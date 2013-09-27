package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.Usuario;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("usuarioDao")
public class UsuarioDao extends HibernateDaoSupport {

	public List<Usuario> findAll() {
		return getHibernateTemplate().loadAll(Usuario.class);
	}

	public Usuario findById(Integer id) {
		return getHibernateTemplate().get(Usuario.class, id);
	}

	public Usuario save(Usuario entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}
	
	public Usuario update(Usuario entity) {
		Usuario entidadAux = getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(entidadAux);
		return entidadAux;
	}

	public void delete(Usuario entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	public void borrarUsuario(Usuario entity) {
		Query query;
		query = getSession().getNamedQuery("eliminaDireccion");
		query.setInteger("id", entity.getIdUsuario());
		query.executeUpdate();
		
		query = getSession().getNamedQuery("eliminaUsuario");
		query.setInteger("id", entity.getIdUsuario());
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	// Por la convesrion de tipo List a List<Usuario>
	public List<Usuario> findByExample(Usuario usuario) {
		return getHibernateTemplate().findByExample(usuario);
	}
}