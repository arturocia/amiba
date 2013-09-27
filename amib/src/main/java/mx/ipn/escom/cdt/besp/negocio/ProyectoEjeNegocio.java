package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.*;
import mx.ipn.escom.cdt.besp.modelo.*;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("proyectoEjeDaoNegocio")
public class ProyectoEjeNegocio {
	
	private ProyectoEjeDao proyectoEjeDao;

	@Transactional
	public List<ProyectoEje> findAll() {
		return proyectoEjeDao.findAll();
	}

	@Transactional
	public ProyectoEje findById(Integer id) {
		return proyectoEjeDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public ProyectoEje save(ProyectoEje entidad) {
		return proyectoEjeDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(ProyectoEje entidad) {
		proyectoEjeDao.delete(entidad);
	}

	public ProyectoEjeDao getEstadoDao() {
		return proyectoEjeDao;
	}

	public void setaccionDao(ProyectoEjeDao proyectoEjeDao) {
		this.proyectoEjeDao = proyectoEjeDao;
	}

	@Transactional
	public List<ProyectoEje> findByExample(ProyectoEje proyectoEje) {
		return proyectoEjeDao.findByExample(proyectoEje);
	}

}
