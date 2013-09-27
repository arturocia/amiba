package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.*;
import mx.ipn.escom.cdt.besp.modelo.*;

@Singleton
@Named("unidadNegocio")
public class UnidadNegocio {
	private UnidadDao unidadDao;
	private IndicadorDao indicadorDao;

	@Transactional
	public List<Unidad> findAll() {
		return unidadDao.findAll();
	}

	@Transactional
	public Unidad findById(Integer id) {
		return unidadDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Unidad save(Unidad entidad) {
		return unidadDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Unidad entidad) {
		unidadDao.delete(entidad);
	}

	@Transactional
	public List<Unidad> findByExample(Unidad unidad) {
		return unidadDao.findByExample(unidad);
	}

	@Transactional
	public Boolean existe(String nombre) {
		Unidad unidadEjemplo = new Unidad();
		List<Unidad> unidades = null;
		unidadEjemplo.setNombre(nombre);
		unidades = findByExample(unidadEjemplo);
		if (unidades != null && unidades.size() > 0) {
			return true;
		}
		return false;
	}

	public UnidadDao getUnidadDao() {
		return unidadDao;
	}

	public void setUnidadDao(UnidadDao unidadDao) {
		this.unidadDao = unidadDao;
	}
	
	@Transactional
	public Boolean estaAsociado(Unidad unidad) {
		Indicador indicador=new Indicador();
		indicador.setIdUnidad(unidad.getIdUnidad());
		if(indicadorDao.findByExample(indicador).size()>0){
			return true;
		}
		return false;
				
	}

	public IndicadorDao getIndicadorDao() {
		return indicadorDao;
	}

	public void setIndicadorDao(IndicadorDao indicadorDao) {
		this.indicadorDao = indicadorDao;
	}
	
	
	
}
