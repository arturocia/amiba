package mx.ipn.escom.cdt.besp.negocio;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.EjeTematicoDao;
import mx.ipn.escom.cdt.besp.modelo.EjeTematico;

@Singleton
@Named("ejeTematicoNegocio")

public class EjeTematicoNegocio {
	private EjeTematicoDao ejeTematicoDao;
	
	/*(GestionarArea 8)
	 (GestionarArea 12) */
	@Transactional
	public List<EjeTematico> findAll() {
		return ejeTematicoDao.findAll();
	}
    @Transactional
	public EjeTematico findById(Integer id) {
		return ejeTematicoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public EjeTematico save(EjeTematico entidad) {
		return ejeTematicoDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public EjeTematico update(EjeTematico entidad) {
		return ejeTematicoDao.update(entidad);
	}
	@Transactional(rollbackFor = Exception.class)
	public void delete(EjeTematico entidad) {
		ejeTematicoDao.delete(entidad);
	}

	
    @Transactional
	public List<EjeTematico> findByExample(EjeTematico ejetematico) {
		return ejeTematicoDao.findByExample(ejetematico);
	}
    @Transactional
	public Boolean existe(String nombre) {
		EjeTematico ejetematicoEjemplo = new EjeTematico();
		List<EjeTematico> ejetematicos = null;
		ejetematicoEjemplo.setNombre(nombre);
		ejetematicos = findByExample(ejetematicoEjemplo);
		if (ejetematicos != null && ejetematicos.size() > 0) {
			return true;
		}
		return false;
	}
    @Transactional
	public Boolean estaAsociado(EjeTematico ejetematico) {
		return ejetematico.getProyectoEje().size() > 0;
	}
    
     public EjeTematicoDao getEjeTematicoDao() {
		return ejeTematicoDao;
	}

	public void setEjeTematicoDao(EjeTematicoDao ejeTematicoDao) {
		this.ejeTematicoDao = ejeTematicoDao;
	}
    
}
