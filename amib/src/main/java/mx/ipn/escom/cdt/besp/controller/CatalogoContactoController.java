package mx.ipn.escom.cdt.besp.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.ContactoNegocio;
import mx.ipn.escom.cdt.besp.negocio.TipoContactoNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName",
		"catalogo-usuario/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@USUARIOSEL].idUsuario}/edit" }) })
public class CatalogoContactoController extends ActionSupport implements
		ModelDriven<Contacto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5891841404280141252L;
	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */
	private ContactoNegocio contactoNegocio;
	private TipoContactoNegocio tipoContactoNegocio;
	private UsuarioNegocio usuarioNegocio;
	// ////////////////////objetos para la validacion del mail usando la
	// expresion regular
	private Pattern pattern;
	private Matcher matcher;
	private static String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	// ///////
	/**
	 * Referencia el Capitulo con el que se est� trabajando, para el caso de
	 * nuevo, editar y eliminar.
	 */
	private Contacto model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se
	 * efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	private Usuario aux = new Usuario();
	/**
	 * Contiene todos los contactos que se encontraron el la BD.
	 */
	private List<Contacto> list = null;
	private Usuario usuario;

	private Integer idUsuarioSel;
	private Logger logger = Logger.getLogger(CatalogoContactoController.class);
	private List<TipoContacto> tipoContactos;

	@SkipValidation
	public HttpHeaders index() {

		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
			list = usuario.getContactos();
		}

		if (idUsuarioSel != null) {
			this.usuario = usuarioNegocio.findById(idUsuarioSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			list = usuario.getContactos();
		}
		logger.trace("El usuario es " + usuario + "el id es " + idUsuarioSel);
		return new DefaultHttpHeaders("index").disableCaching();

	}

	@SkipValidation
	public String editNew() {
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
		}
		tipoContactos = tipoContactoNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPO_CONTACTOS, tipoContactos);

		return "editNew";
	}

	@SuppressWarnings("unchecked")
	public void validateCreate() {

		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
		}
		if (model.getPrincipal() == 1
				&& contactoNegocio.existePrincipal(model.getIdTipoContacto(),
						usuario.getIdUsuario())) {

			addActionError("No puedes agregar el contacto,Ya tienes un contacto principal de este tipo de Contacto");
		}

		if (contactoNegocio.existe(model)) {
			addActionError("El contacto " + model.getContacto()
					+ " ya esta ocupado, favor de elegir otro.");
		}
		model.getTipoContacto();
		// si el contacto es un correo se valida su formato
		if (model.getIdTipoContacto() == TipoContacto.CORREO) {
			// aqui la expresio regular se evalua
			pattern = Pattern.compile(emailPattern);
			// el contacto se verifica con la expresion regular
			matcher = pattern.matcher(model.getContacto());
			// si el resultado de la validacion es falso lanza el error
			if (!matcher.matches()) {
				addActionError("No puedes agregar el contacto, el formato de este correo es incorrecto");
			}

		}

		tipoContactos = (List<TipoContacto>) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.TIPO_CONTACTOS);
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.contacto", type = ValidatorType.FIELD, key = "contacto.intro") })
	public HttpHeaders create() {
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
		}
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);
		model.setIdUsuario(usuario.getIdUsuario());
		model = contactoNegocio.save(model);
		addActionMessage("El contacto " + model.getContacto()
				+ "  se registró exitosamente");
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdContacto());
	}

	@SkipValidation
	public String edit() {
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
		}
		tipoContactos = tipoContactoNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPO_CONTACTOS, tipoContactos);
		return "edit";
	}

	@SuppressWarnings("unchecked")
	public void validateUpdate() {
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
		}
		tipoContactos = (List<TipoContacto>) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.TIPO_CONTACTOS);
		model.getTipoContacto();
		// si el contacto es un correo se valida su formato
		if (model.getIdTipoContacto() == TipoContacto.CORREO) {
			// aqui la expresio regular se evalua
			pattern = Pattern.compile(emailPattern);
			// el contacto se verifica con la expresion regular
			matcher = pattern.matcher(model.getContacto());
			// si el resultado de la validacion es falso lanza el error
			if (!matcher.matches()) {
				addActionError("No puedes agregar el contacto, el formato de este correo es incorrecto");
			}

		}
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.contacto", type = ValidatorType.FIELD, key = "contacto.intro") })
	public String update() {
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);

		if (usuario != null) {
			this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIOSEL, usuario);
			idUsuarioSel = usuario.getIdUsuario();
		}
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIOSEL);
		this.usuario = usuarioNegocio.findById(this.usuario.getIdUsuario());
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.USUARIOSEL, usuario);
		contactoNegocio.cambiarPrincipal(model.getIdTipoContacto(),
				usuario.getIdUsuario(), model.getIdContacto(),
				model.getPrincipal());
		contactoNegocio.save(model);
		addActionMessage("El contacto " + model.getContacto()
				+ " se actualizo correctamente");

		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {

	}

	public String destroy() {
		if (contactoNegocio.esPrincipalOblogatorio(model.getIdTipoContacto(),
				model.getPrincipal())) {
			addActionError(getText("elimContactoPrincipal"));
		} else {
			addActionMessage("El contacto " + model.getContacto()
					+ " se eliminó exitosamente");
			contactoNegocio.delete(model);
		}
		return "success";
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = contactoNegocio.findById(idSel);
		}
	}

	public Contacto getModel() {
		if (model == null) {
			model = new Contacto();
		}

		return model;
	}

	public List<Contacto> getList() {
		return list;
	}

	public void setList(List<Contacto> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Contacto model) {
		this.model = model;
	}

	public void setTipoContactos(List<TipoContacto> tipoContactos) {
		this.tipoContactos = tipoContactos;
	}

	public List<TipoContacto> getTipoContactos() {
		return tipoContactos;
	}

	public TipoContactoNegocio getTipoContactosNegocio() {
		return tipoContactoNegocio;
	}

	public void setTipoContactoNegocio(TipoContactoNegocio tipoContactoNegocio) {
		this.tipoContactoNegocio = tipoContactoNegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getIdUsuarioSel() {
		if (idUsuarioSel == null) {
			aux = (Usuario) ActionContext.getContext().get(
					NombreObjetosSesion.USUARIOSEL);
			idUsuarioSel = aux.getIdUsuario();
		}
		return idUsuarioSel;
	}

	public void setIdUsuarioSel(Integer idUsuarioSel) {
		this.idUsuarioSel = idUsuarioSel;
	}

	public ContactoNegocio getContactoNegocio() {
		return contactoNegocio;
	}

	public void setContactoNegocio(ContactoNegocio contactoNegocio) {
		this.contactoNegocio = contactoNegocio;
	}

	public TipoContactoNegocio getTipoContactoNegocio() {
		return tipoContactoNegocio;
	}
}