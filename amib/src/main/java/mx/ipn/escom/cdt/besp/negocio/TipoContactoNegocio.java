package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.TipoContactoDao;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;


@Singleton
@Named("tipoContactoNegocio")
public class TipoContactoNegocio {
	private TipoContactoDao tipoContactoDao;
    @Transactional
	public List<TipoContacto> findAll() {
		return tipoContactoDao.findAll();
	}
    @Transactional
	public TipoContacto findById(Integer id) {
		return tipoContactoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public TipoContacto save(TipoContacto entidad) {
		return tipoContactoDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public TipoContacto update(TipoContacto entidad) {
		return tipoContactoDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(TipoContacto entidad) {
		tipoContactoDao.delete(entidad);
	}
	
    @Transactional
	public List<TipoContacto> findByExample(TipoContacto tipoContacto) {
		return tipoContactoDao.findByExample(tipoContacto);
	}
    @Transactional
	public Boolean existe(TipoContacto ejemplo) {
		TipoContacto tipoContactoEjemplo= new TipoContacto();
		tipoContactoEjemplo.setNombre(ejemplo.getNombre());
		List<TipoContacto> grupos = null;
		grupos = findByExample(tipoContactoEjemplo);
		if (grupos != null && grupos.size() > 0) {
			return false;
		}
		return true;
	}
    public TipoContactoDao getTipoContactoDao() {
		return tipoContactoDao;
	}

	public void setTipoContactoDao(TipoContactoDao tipoContactoDao) {
		this.tipoContactoDao = tipoContactoDao;
	}
}

