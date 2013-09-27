package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.AreaDao;
import mx.ipn.escom.cdt.besp.modelo.Area;

@Singleton
@Named("areaNegocio")
public class AreaNegocio {
	private AreaDao areaDao;
	private Logger logger = Logger.getLogger(AreaNegocio.class);
	/*
	 * (GestionarArea 8) (GestionarArea 12)
	 */
	@Transactional
	public List<Area> findAll() {
		return areaDao.findAll();
	}

	@Transactional
	public Area findById(Integer id) {
		return areaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Area save(Area entidad) {
		return areaDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Area update(Area entidad) {
		return areaDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Area entidad) {
		areaDao.delete(entidad);
	}

	@Transactional
	public List<Area> findByExample(Area area) {
		return areaDao.findByExample(area);
	}

	@Transactional
	public Boolean existe(String nombre) {
		Area areaEjemplo = new Area();
		List<Area> areas = null;
		areaEjemplo.setNombre(nombre);
		areas = findByExample(areaEjemplo);
		if (areas != null && areas.size() > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public Boolean estaAsociado(Area area) {
		logger.trace("El usuario del area "+area.getNombre()+" trae"+area.getUsuarios().size());
		return area.getUsuarios().size() > 0;
	}
     
	
	public AreaDao getAreaDao() {
		return areaDao;
	}

	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

}
