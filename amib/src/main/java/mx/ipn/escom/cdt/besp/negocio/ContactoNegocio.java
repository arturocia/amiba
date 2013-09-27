package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import mx.ipn.escom.cdt.besp.dao.ContactoDao;
import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

@Singleton
@Named("contactoNegocio")
public class ContactoNegocio {
	private ContactoDao contactoDao;

	@Transactional
	public List<Contacto> findAll() {
		return contactoDao.findAll();
	}

	@Transactional
	public Contacto findById(Integer id) {
		return contactoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Contacto save(Contacto entidad) { 
		return contactoDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Contacto entidad) { 
		contactoDao.delete(entidad);
	}

	@Transactional
	public List<Contacto> findByExample(Contacto contacto) {
		return contactoDao.findByExample(contacto);
	}

	/**
	 * Revisa si ya existe un contacto idéntico al que se quiere agregar
	 * @param contacto Nuevo contacto
	 * @return true Si es idéntico a un contacto existente, false en caso contrario
	 */
	@Transactional
	public Boolean existe(Contacto contacto) { 
		List<Contacto> contactos = null;
		contactos = findByExample(contacto);
		if (contactos != null && contactos.size() > 0) {
			for (int i = 0; i < contactos.size(); i++) {
				if (contacto.getContacto().equals(contactos.get(i).getContacto()) && 
						contacto.getTipoContacto().equals(contactos.get(i).getTipoContacto())) {
					return true;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Revisa si ya existe un contacto idéntico al que se quiere agregar
	 * @param contacto Nuevo contacto
	 * @return true Si es idéntico a un contacto existente, false en caso contrario
	 */
	@Transactional
	public Boolean existeTemporal(Contacto contacto) {
		List<Contacto> contactos = (List<Contacto>) ActionContext.getContext().
				getSession().get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (contactos != null) {
			for (int i = 0; i < contactos.size(); i++) {
				if (contacto.getContacto().equals(contactos.get(i).getContacto()) &&
						contacto.getTipoContacto().equals(contactos.get(i).getTipoContacto())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Valida...
	 * 
	 * @param idTipoContacto
	 *            idTipoContacto
	 * @param idUsuarioP
	 *            idUsuario
	 * @return
	 */
	@Transactional
	public Boolean existePrincipal(Integer idTipoContacto, Integer idUsuarioP) {
		Contacto contactoEjemploP = new Contacto();
		List<Contacto> contactoP = null;
		contactoEjemploP.setIdUsuario(idUsuarioP);
		contactoEjemploP.setIdTipoContacto(idTipoContacto);
		contactoEjemploP.setPrincipal(1);
		contactoP = findByExample(contactoEjemploP);
		if (contactoP != null && contactoP.size() == 1) {
			return true;
		}
		return false;
	}

	public Boolean existePrincipalTemporal(Contacto contacto) {
		List<Contacto> contactos = (List<Contacto>) ActionContext.getContext().getSession().
				get(NombreObjetosSesion.CONTACTOS_TEMPORALES);
		if (contactos != null) {
			for (int i = 0; i < contactos.size(); i++) {
				if (contacto.getPrincipal().equals(contactos.get(i).getPrincipal()) &&
						contacto.getTipoContacto().equals(contactos.get(i).getTipoContacto())) {
					return true;
				}
			}
		}
		return false;
	}
	
	// ******
	@Transactional
	public Boolean esPrincipalOblogatorio(Integer idTipoContacto,
			Integer identificadorPrincipal) {

		if ((idTipoContacto == TipoContacto.TELEFONO || idTipoContacto == TipoContacto.CORREO)
				&& identificadorPrincipal == 1) {
			return true;
		}
		return false;
	}

	// *************

	/**
	 * 
	 * @param idTipoContacto
	 * @param idUsuarioP
	 * @param idContacto
	 */
	@Transactional
	public void cambiarPrincipal(Integer idTipoContacto, Integer idUsuarioP,
			Integer idContacto, Integer principal) {
		Contacto contactoEjemploP = new Contacto();
		Contacto contactoEjemploC;
		List<Contacto> contactoP = null;
		contactoEjemploP.setIdUsuario(idUsuarioP);
		contactoEjemploP.setIdTipoContacto(idTipoContacto);
		contactoEjemploP.setPrincipal(1);
		contactoP = findByExample(contactoEjemploP);
		if (contactoP != null && contactoP.size() == 1) {
			contactoEjemploC = contactoP.get(0);
			if (!contactoEjemploC.getIdContacto().equals(idContacto)
					&& principal.equals(1)) {
				contactoEjemploC.setPrincipal(0);
			}
		}

	}

	public ContactoDao getContactoDao() {
		return contactoDao;
	}

	public void setContactoDao(ContactoDao contactoDao) {
		this.contactoDao = contactoDao;
	}
}
