package mx.ipn.escom.cdt.besp.negocio;


import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;


import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.DireccionDao;
import mx.ipn.escom.cdt.besp.modelo.Direccion;

@Singleton
@Named("direccionNegocio")
public class DireccionNegocio {
	private DireccionDao direccionDao;
    @Transactional
	public List<Direccion> findAll() {
		return direccionDao.findAll();
	}
    @Transactional
	public Direccion findById(Integer id) {
		return direccionDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Direccion save(Direccion entidad) {
		return direccionDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Direccion update(Direccion entidad) {
		return direccionDao.update(entidad);
	}


	@Transactional(rollbackFor = Exception.class)
	public void delete(Direccion entidad) {
		direccionDao.delete(entidad);
	}

	
    @Transactional
	public List<Direccion> findByExample(Direccion direccion) {
		return direccionDao.findByExample(direccion);
	}
    @Transactional
	public Boolean existe(Integer user) {
		Direccion direccionEjemplo = new Direccion();
		List<Direccion> direccions = null;
		direccionEjemplo.setIdUsuario(user);
		direccions = findByExample(direccionEjemplo);
		if (direccions != null && direccions.size() > 0) {
			return true;
		}
		return false;
	}
    
    public DireccionDao getDireccionDao() {
		return direccionDao;
	}

	public void setDireccionDao(DireccionDao direccionDao) {
		this.direccionDao = direccionDao;
	}
}
