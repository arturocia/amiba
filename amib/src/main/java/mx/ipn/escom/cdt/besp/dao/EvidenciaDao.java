package mx.ipn.escom.cdt.besp.dao;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.modelo.Evidencia;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

@Singleton
@Named("evidenciaDao")
public class EvidenciaDao extends HibernateDaoSupport {

	@SuppressWarnings("unchecked")
	// Por el cast de List a List<Evidencia>
	public List<Evidencia> getEvidencias(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Evidencia> evidencias = null;
		Query query;
		boolean band = false;
		String sentencia = "select distinct e from Evidencia e join fetch e.proyecto p join p.temaTransversales tt join p.ejeTematicos et join p.estructuras es where ";
		if (idAreas != null || idProgramas != null || idEjes != null
				|| idTemas != null) {
			if (idAreas != null) {
				sentencia = sentencia + " p.responsable.idArea in(:idAreas)";
				band = true;
			}
			if (idTemas != null) {
				if (band) {
					sentencia = sentencia + " and";
				} else {
					band = true;
				}
				sentencia = sentencia + " tt.idTema in(:idTemas)";
			}
			if (idEjes != null) {
				if (band) {
					sentencia = sentencia + " and";
				} else {
					band = true;
				}
				sentencia = sentencia + " et.idEje in(:idEjes)";
			}
			if (idProgramas != null) {
				if (band) {
					sentencia = sentencia + " and";
				}
				sentencia = sentencia + " es.idPrograma in(:idProgramas)";
			}
			query = getSession().createQuery(sentencia);

			if (idAreas != null) {
				query.setParameterList("idAreas", idAreas);
			}
			if (idTemas != null) {
				query.setParameterList("idTemas", idTemas);
			}
			if (idEjes != null) {
				query.setParameterList("idEjes", idEjes);
			}
			if (idProgramas != null) {
				query.setParameterList("idProgramas", idProgramas);
			}

			evidencias = query.list();
		} else {
			evidencias = this.findAll();
		}
		return evidencias;
	}
	
	@SuppressWarnings("unchecked")
	public List<Evidencia> buscaEvidencias(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Evidencia> evidencias = null;
		Query query;
		boolean band = false;
		String sentencia = "select distinct e from Evidencia e join fetch e.proyecto p join p.temaTransversales tt join p.ejeTematicos et where ";
		if (idAreas != null || idProgramas != null || idEjes != null
				|| idTemas != null) {
			if (idAreas != null) {
				sentencia = sentencia + " p.responsable.idArea in(:idAreas)";
				band = true;
			}
			if (idTemas != null) {
				if (band) {
					sentencia = sentencia + " and";
				} else {
					band = true;
				}
				sentencia = sentencia + " tt.idTema in(:idTemas)";
			}
			if (idEjes != null) {
				if (band) {
					sentencia = sentencia + " and";
				} else {
					band = true;
				}
				sentencia = sentencia + " et.idEje in(:idEjes)";
			}
			if (idProgramas != null) {
				if (band) {
					sentencia = sentencia + " and";
				}
				sentencia = sentencia + " es.idPrograma in(:idProgramas)";
			}
			query = getSession().createQuery(sentencia);

			if (idAreas != null) {
				query.setParameterList("idAreas", idAreas);
			}
			if (idTemas != null) {
				query.setParameterList("idTemas", idTemas);
			}
			if (idEjes != null) {
				query.setParameterList("idEjes", idEjes);
			}
			if (idProgramas != null) {
				query.setParameterList("idProgramas", idProgramas);
			}

			evidencias = query.list();
		} else {
			evidencias = this.findAll();
		}
		return evidencias;
	}


	public List<Evidencia> findAll() {
		return getHibernateTemplate().loadAll(Evidencia.class);
	}

	public Evidencia findById(Integer id) {
		return getHibernateTemplate().get(Evidencia.class, id);
	}

	public Evidencia save(Evidencia entity) {
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void delete(Evidencia entity) {
		getHibernateTemplate().refresh(entity);
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	public List<Evidencia> findByExample(Evidencia evidencia) {
		return getHibernateTemplate().findByExample(evidencia);
	}

	public Evidencia update(Evidencia entity) {
		Evidencia evidenciaAux=getHibernateTemplate().merge(entity);
		getHibernateTemplate().update(evidenciaAux);
		return evidenciaAux;
	}

}
