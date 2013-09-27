package mx.ipn.escom.cdt.besp.negocio;


import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import mx.ipn.escom.cdt.besp.dao.PerfilUsuarioDao;
import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;

@Singleton
@Named("perfilUsuarioNegocio")
public class PerfilUsuarioNegocio{
	private PerfilUsuarioDao perfilUsuarioDao;
    @Transactional
	public List<PerfilUsuario> findAll() {
		return perfilUsuarioDao.findAll();
	}
   @Transactional
	public PerfilUsuario findById(Integer id) {
		return perfilUsuarioDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public PerfilUsuario save(PerfilUsuario entidad) {
		return perfilUsuarioDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(PerfilUsuario entidad) {
		perfilUsuarioDao.delete(entidad);
	}
	
    @Transactional
	public List<PerfilUsuario> findByExample(PerfilUsuario perfilUsuario) {
		return perfilUsuarioDao.findByExample(perfilUsuario);
	}

	public Boolean existe(String nombre) {
		PerfilUsuario perfilUsuarioEjemplo = new PerfilUsuario();
		List<PerfilUsuario> perfilUsuarios = null;
		perfilUsuarioEjemplo.setNombre(nombre);
		perfilUsuarios = findByExample(perfilUsuarioEjemplo);
		if (perfilUsuarios != null && perfilUsuarios.size() > 0) {
			return true;
		}
		return false;
	}
	public Boolean estaAsociado(PerfilUsuario perfilUsuario){
		return perfilUsuario.getUsuarios().size() > 0;
	}
	public PerfilUsuarioDao getPerfilUsuarioDao() {
		return perfilUsuarioDao;
	}

	public void setPerfilUsuarioDao(PerfilUsuarioDao perfilUsuarioDao) {
		this.perfilUsuarioDao = perfilUsuarioDao;
	}
}