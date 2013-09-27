package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.*;
import mx.ipn.escom.cdt.besp.modelo.*;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("proyectoTemaDaoNegocio")
public class ProyectoTemaNegocio {
	
	private ProyectoTemaDao proyectoTemaDao;

	@Transactional
	public List<ProyectoTema> findAll() {
		return proyectoTemaDao.findAll();
	}

	@Transactional
	public ProyectoTema findById(Integer id) {
		return proyectoTemaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public ProyectoTema save(ProyectoTema entidad) {
		return proyectoTemaDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(ProyectoTema entidad) {
		proyectoTemaDao.delete(entidad);
	}

	public ProyectoTemaDao getEstadoDao() {
		return proyectoTemaDao;
	}

	public void setaccionDao(ProyectoTemaDao proyectoTemaDao) {
		this.proyectoTemaDao = proyectoTemaDao;
	}

	@Transactional
	public List<ProyectoTema> findByExample(ProyectoTema proyectoTema) {
		return proyectoTemaDao.findByExample(proyectoTema);
	}

}
