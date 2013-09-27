package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import mx.ipn.escom.cdt.besp.dao.TipoRegistroDao;
import mx.ipn.escom.cdt.besp.modelo.TipoRegistro;

import org.springframework.transaction.annotation.Transactional;

public class TipoRegistroNegocio {

	private TipoRegistroDao tipoRegistroDao;
    @Transactional
	public List<TipoRegistro> findAll() {
		return tipoRegistroDao.findAll();
	}
    @Transactional
	public TipoRegistro findById(Integer id) {
		return tipoRegistroDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public TipoRegistro save(TipoRegistro entidad) {
		return tipoRegistroDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(TipoRegistro entidad) {
		tipoRegistroDao.delete(entidad);
	}
    @Transactional
	public List<TipoRegistro> findByExample(TipoRegistro tipoRegistro) {
		return tipoRegistroDao.findByExample(tipoRegistro);
	}

	public TipoRegistroDao getTipoRegistroDao() {
		return tipoRegistroDao;
	}

	public void setTipoRegistroDao(TipoRegistroDao tipoRegistroDao) {
		this.tipoRegistroDao = tipoRegistroDao;
	}

}
