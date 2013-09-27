package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.EstadoDao;
import mx.ipn.escom.cdt.besp.modelo.Estado;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("estadoNegocio")
public class EstadoNegocio {
	
	private EstadoDao estadoDao;

	@Transactional
	public List<Estado> findAll() {
		return estadoDao.findAll();
	}
    @Transactional
	public Estado findById(Integer id) {
		return estadoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Estado save(Estado entidad) {
		return estadoDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Estado entidad) {
		estadoDao.delete(entidad);
	}

	
    @Transactional
	public List<Estado> findByExample(Estado estado) {
		return estadoDao.findByExample(estado);
	}
    @Transactional
	public Boolean existe(String nombre) {
		Estado estadoEjemplo = new Estado();
		List<Estado> estados = null;
		estadoEjemplo.setNombre(nombre);
		estados = findByExample(estadoEjemplo);
		if (estados != null && estados.size() > 0) {
			return true;
		}
		return false;
	}

    public EstadoDao getEstadoDao() {
		return estadoDao;
	}

	public void setEstadoDao(EstadoDao estadoDao) {
		this.estadoDao = estadoDao;
	}
    
}
