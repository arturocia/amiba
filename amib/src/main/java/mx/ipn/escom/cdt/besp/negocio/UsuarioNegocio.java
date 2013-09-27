package mx.ipn.escom.cdt.besp.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.UsuarioDao;
import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;
import mx.ipn.escom.cdt.besp.modelo.Usuario;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("usuarioNegocio")
public class UsuarioNegocio {
	private UsuarioDao usuarioDao;
	private ContactoNegocio contactoNegocio;

	private Logger logger = Logger.getLogger(UsuarioNegocio.class);

	@Transactional
	public List<Usuario> findAll() {
		return usuarioDao.findAll();
	}

	@Transactional
	public Usuario findById(Integer id) {
		return usuarioDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Usuario save(Usuario entidad) {
		return usuarioDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Usuario update(Usuario entidad) {
		return usuarioDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Usuario entidad) {
		usuarioDao.delete(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void borrarUsuario(Usuario entidad) {
		int c = entidad.getContactos().size();
		List<Contacto> contactosAborrar = new ArrayList<Contacto>();
		int i;
		for (i = 0; i < c; i++) {
			contactosAborrar.add(entidad.getContactos().get(i));
		}

		for (i = 0; i < c; i++) {
			logger.trace("Borrando el contacto " + contactosAborrar.get(i));
			contactoNegocio.delete(contactosAborrar.get(i));
		}
		usuarioDao.borrarUsuario(entidad);
	}

	@Transactional
	public List<Usuario> findByExample(Usuario usuario) {
		return usuarioDao.findByExample(usuario);
	}

	@Transactional
	public Boolean existe(String nombre) {
		Usuario usuarioEjemplo = new Usuario();
		List<Usuario> usuarios = null;
		usuarioEjemplo.setLogin(nombre);
		usuarios = findByExample(usuarioEjemplo);
		if (usuarios != null && usuarios.size() > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public Boolean estaAsociado(Usuario usuario) {
		return usuario.getProyectos().size() > 0
				|| usuario.getProgramas().size() > 0
				|| usuario.getContactos().size() > 0;
	}

	/**
	 * 
	 * @param usuario
	 * @return Si el usuario tiene proyectos, programas o empleados asociados
	 */
	@Transactional
	public Boolean validaTrabajoAsociado(Usuario usuario) {
		return (usuario.getProyectos().size() > 0)
				|| (usuario.getProgramas().size() > 0)
				|| (usuario.getSuperior() != null && usuario.getSuperior()
						.size() > 0);
	}

	@Transactional
	public Usuario getUsuarioSecretaria() {
		Usuario usuarioSecretaria = new Usuario();
		List<Usuario> usuarios;

		usuarios = this.findAll();

		for (Usuario usuario : usuarios) {
			if (usuario.getIdPerfilUsuario().equals(PerfilUsuario.SECRETARIO)) {
				usuarioSecretaria = usuario;
			}
		}

		return usuarioSecretaria;
	}

	/**
	 * Obtiene los usuarios disponibles para reasignar el trabajo del usuario
	 * <code>usuario</code> en caso de que este sea eliminado.
	 * 
	 * Se contemplan los siguientes perfiles:
	 * 
	 * <ul>
	 * <li> {@link PerfilUsuario#COORDINADOR} Se encuentran todos los
	 * coordinadores registrados y activos que no sean <code>usuario</code>.
	 * <li> {@link PerfilUsuario#GERENTE} Se encuentran los gerentes que esten
	 * activos y que no tengan ya un programa asignado.
	 * </ul>
	 * 
	 * @return
	 */
	@Transactional
	public List<Usuario> getUsuariosDisponibles(Usuario usuario) {
		List<Usuario> usuariosDisponibles = null;
		Usuario usuarioEjemplo = null;

		usuarioEjemplo = new Usuario();
		usuarioEjemplo.setActivado(true);
		usuariosDisponibles = new ArrayList<Usuario>();

		if (usuario.getIdPerfilUsuario().equals(PerfilUsuario.COORDINADOR)) {
			usuarioEjemplo.setIdPerfilUsuario(PerfilUsuario.COORDINADOR);
			for (Usuario usuarioActual : findByExample(usuarioEjemplo)) {
				if (!usuarioActual.getIdUsuario()
						.equals(usuario.getIdUsuario())) {
					usuariosDisponibles.add(usuarioActual);
				}
			}
		}
		if (usuario.getIdPerfilUsuario().equals(PerfilUsuario.GERENTE)) {
			usuarioEjemplo.setIdPerfilUsuario(PerfilUsuario.GERENTE);
			for (Usuario usuarioActual : findByExample(usuarioEjemplo)) {
				if (usuarioActual.getProgramas().size() < 1
						&& !usuarioActual.getIdUsuario().equals(
								usuario.getIdUsuario())) {
					usuariosDisponibles.add(usuarioActual);
				}
			}

		}
		return usuariosDisponibles;
	}

	/**
	 * Busca los usuarios de tipo {@link PerfilUsuario#GERENTE} que no tengan
	 * asociado un programa.
	 * 
	 * @return La lista de los gerentes que se pueden asignar a un programa.
	 */
	@Transactional
	public List<Usuario> getGerentesAsignables() {
		List<Usuario> gerentes = null;
		Usuario usuarioEjemplo;

		usuarioEjemplo = new Usuario();
		usuarioEjemplo.setIdPerfilUsuario(PerfilUsuario.GERENTE);

		gerentes = new ArrayList<Usuario>();
		for (Usuario usuario : usuarioDao.findByExample(usuarioEjemplo)) {
			if (usuario.getProgramas().size() == 0) {
				gerentes.add(usuario);
			}
		}
		return gerentes;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public ContactoNegocio getContactoNegocio() {
		return contactoNegocio;
	}

	public void setContactoNegocio(ContactoNegocio contactoNegocio) {
		this.contactoNegocio = contactoNegocio;
	}

}
