package mx.ipn.escom.cdt.besp.dao;

import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import mx.ipn.escom.cdt.besp.modelo.Estado;

@Singleton
@Named("estadoDao")
public class EstadoDao extends HibernateDaoSupport {

		public List<Estado> findAll() {
			return getHibernateTemplate().loadAll(Estado.class);
		}

		public Estado findById(Integer id) {
			return getHibernateTemplate().get(Estado.class, id);
		}

		public Estado save(Estado entity) {
			if (entity.getIdEstado() != null) {
				getHibernateTemplate().merge(entity);
			}
			getSession().saveOrUpdate(entity);
			return entity;
		}

		public void delete(Estado entity) {
			getHibernateTemplate().refresh(entity);
			getSession().delete(entity);
		}

		@SuppressWarnings("unchecked")
		// Por la convesrion de tipo List a List<Estado>
		public List<Estado> findByExample(Estado estado) {
			return getHibernateTemplate().findByExample(estado);
		}
}