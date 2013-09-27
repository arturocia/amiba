package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.*;
import mx.ipn.escom.cdt.besp.modelo.*;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("tipoUnidadNegocio")
public class TipoUnidadNegocio {

	private TipoUnidadDao tipoUnidadDao;
    @Transactional
	public List<TipoUnidad> findAll() {
		return tipoUnidadDao.findAll();
	}
    @Transactional
	public TipoUnidad findById(Integer id) {
		return tipoUnidadDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public TipoUnidad save(TipoUnidad entidad) {
		return tipoUnidadDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(TipoUnidad entidad) {
		tipoUnidadDao.delete(entidad);
	}
    @Transactional
	public List<TipoUnidad> findByExample(TipoUnidad tipoUnidad) {
		return tipoUnidadDao.findByExample(tipoUnidad);
	}

	public TipoUnidadDao getEstadoDao() {
		return tipoUnidadDao;
	}

	public void settipoUnidadDao(TipoUnidadDao tipoUnidadDao) {
		this.tipoUnidadDao = tipoUnidadDao;
	}
}
