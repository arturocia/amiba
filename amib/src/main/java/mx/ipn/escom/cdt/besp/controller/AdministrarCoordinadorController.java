package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Area;
import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.Direccion;
import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.AreaNegocio;
import mx.ipn.escom.cdt.besp.negocio.ContactoNegocio;
import mx.ipn.escom.cdt.besp.negocio.DireccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.PerfilUsuarioNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
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
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "administrar-coordinador" }) })
public class AdministrarCoordinadorController extends ActionSupport implements
		ModelDriven<Usuario> {

	private static final long serialVersionUID = -7870489808378084296L;

	private Logger logger = Logger
			.getLogger(AdministrarCoordinadorController.class);

	private UsuarioNegocio usuarioNegocio;
	private Usuario model = null;
	private Integer idSel = null;
	// lista usada en el index jsp para
	private List<Usuario> list = null;
	// mostrar los datos de usuario
	// lista para mostrar los nombres de perfil usuario
	private List<PerfilUsuario> perfilUsuarios; 
	// variable para acceder a sus metodos
	private PerfilUsuarioNegocio perfilUsuarioNegocio; 
	// para acceder a los metodos de
	private DireccionNegocio direccionNegocio;
	// direcc
	// lista para guardar todos los datosreferentes al area
	private List<Area> listareas;
	// para acceder a los metodos de area
	private AreaNegocio areaNegocio;
	// lista para guardar los proyectos del usuario
	private List<Proyecto> proyectos;
	// boleano para siver si tiene almenos un proyecto
	private Boolean proyecto;
	// asignado
	private Contacto tel;
	private Contacto correo;
	private ContactoNegocio contactoNegocio;
	private TipoContacto tipoContacto;

	private List<Usuario> usuariosExistentes = null;
	private int idUser;
	private ProyectoNegocio proyectoNegocio;
	private String psw2;
	private Integer idProyectoPreregistrado;
	private List<Contacto> listContactos = null;
	private Integer idUsuarioSel;

	@SkipValidation
	public HttpHeaders index() {
		Usuario usuarioNuevo = new Usuario();
		usuarioNuevo.setIdPerfilUsuario(PerfilUsuario.COORDINADOR);

		list = usuarioNegocio.findByExample(usuarioNuevo);
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = usuarioNegocio.findById(idSel);
		}
	}

	@SkipValidation
	public String editNew() {
		model.setIdPerfilUsuario(2);
		perfilUsuarios = perfilUsuarioNegocio.findAll();

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PERFIL_USUARIOS, perfilUsuarios);
		listareas = areaNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.AREAS, listareas);
		return "editNew";
	}

	public void validateCreate() {
		if (usuarioNegocio.existe(model.getLogin())) {
			addActionError(getText("usuarioRepetido"));
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.login", type = ValidatorType.FIELD, key = "introLogin"),
			@RequiredStringValidator(fieldName = "model.psw", type = ValidatorType.FIELD, key = "introPass"),
			@RequiredStringValidator(fieldName = "psw2", type = ValidatorType.FIELD, key = "introPass2"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.apPat", type = ValidatorType.FIELD, key = "introApPat"),
			@RequiredStringValidator(fieldName = "model.apMat", type = ValidatorType.FIELD, key = "introApMat"),
			@RequiredStringValidator(fieldName = "model.cargo", type = ValidatorType.FIELD, key = "introCargo"),
			@RequiredStringValidator(fieldName = "model.rfc", type = ValidatorType.FIELD, key = "introRFC"),
			@RequiredStringValidator(fieldName = "model.direccion.calle", type = ValidatorType.FIELD, key = "introCalle"),
			@RequiredStringValidator(fieldName = "model.direccion.colonia", type = ValidatorType.FIELD, key = "introCol"),
			@RequiredStringValidator(fieldName = "model.direccion.deleg", type = ValidatorType.FIELD, key = "introDel"),
			@RequiredStringValidator(fieldName = "model.direccion.edo", type = ValidatorType.FIELD, key = "introEdo"),
			@RequiredStringValidator(fieldName = "model.direccion.pais", type = ValidatorType.FIELD, key = "introPais"),
			@RequiredStringValidator(fieldName = "tel.descripcion", type = ValidatorType.FIELD, key = "introTelDescr"),
			@RequiredStringValidator(fieldName = "correo.descripcion", type = ValidatorType.FIELD, key = "introCorreoDescr"),
			@RequiredStringValidator(fieldName = "correo.contacto", type = ValidatorType.FIELD, key = "introCorreo"),
			@RequiredStringValidator(fieldName = "tel.contacto", type = ValidatorType.FIELD, key = "introTel") }, requiredFields = {
			@RequiredFieldValidator(fieldName = "model.direccion.cp", key = "introCP", type = ValidatorType.SIMPLE),
			@RequiredFieldValidator(fieldName = "model.direccion.direccion", key = "introNum", type = ValidatorType.SIMPLE) }, conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "model.direccion.cp", key = "cpError", type = ValidatorType.SIMPLE),
			@ConversionErrorFieldValidator(fieldName = "model.direccion.direccion", key = "numError", type = ValidatorType.SIMPLE) }, intRangeFields = {
			@IntRangeFieldValidator(fieldName = "model.idPerfilUsuario", type = ValidatorType.SIMPLE, min = "0", key = "selecPerfil"),
			@IntRangeFieldValidator(fieldName = "model.idArea", type = ValidatorType.SIMPLE, min = "0", key = "selecArea") }, expressions = { @ExpressionValidator(key = "introPassNotEq", expression = "psw2==model.psw") }, regexFields = {
			@RegexFieldValidator(fieldName = "model.login", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü]){1,20}", key = "loginError"),
			@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 ]){1,50}", key = "nombreError.max50"),
			@RegexFieldValidator(fieldName = "model.aptPat", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9]){1,50}", key = "apPatError"),
			@RegexFieldValidator(fieldName = "model.aptMat", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9]){1,50}", key = "apMatError"),
			@RegexFieldValidator(fieldName = "model.cargo", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,250}", key = "cargoError"),
			@RegexFieldValidator(fieldName = "model.rfc", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9-]){1,20}", key = "rfcError"),
			@RegexFieldValidator(fieldName = "model.direccion.calle", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "calleError"),
			@RegexFieldValidator(fieldName = "model.direccion.colonia", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "colError"),
			@RegexFieldValidator(fieldName = "model.direccion.deleg", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "delError"),
			@RegexFieldValidator(fieldName = "model.direccion.pais", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "paisError"),
			@RegexFieldValidator(fieldName = "tel.contacto", type = ValidatorType.SIMPLE, expression = "([0-9]){1,20}", key = "telError")

	}, emails = { @EmailValidator(fieldName = "correo.contacto", key = "correoError") })
	public HttpHeaders create() {

		Direccion direccion;
		setIdProyectoPreregistrado((Integer) ActionContext.getContext()
				.getSession()
				.get(NombreObjetosSesion.ID_PROYECTO_PREREGISTRADO));
		desactivarUsuario();
		model = usuarioNegocio.save(model);
		model.getDireccion().setIdUsuario(model.getIdUsuario());
		direccion = model.getDireccion();
		direccionNegocio.save(direccion);
		// AQUI VA EL ID(en la base de datos) DEL TIPO DE CONTACTO TELEFONO, DEBERIA SER FIJO
		tel.setIdTipoContacto(TipoContacto.TELEFONO);
		tel.setIdUsuario(model.getIdUsuario());
		tel.setPrincipal(1);
		this.contactoNegocio.save(tel);

		correo.setIdTipoContacto(TipoContacto.CORREO);
		correo.setIdUsuario(model.getIdUsuario());
		correo.setPrincipal(1);
		this.contactoNegocio.save(correo);

		addActionMessage(getText("usuarioAgre"));
		if (ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO_POTENCIAL) != null) {
			ActionContext.getContext().getSession()
					.remove(NombreObjetosSesion.USUARIO_POTENCIAL);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.USUARIO_NUEVO, model);
			logger.trace("Se sube la bilirrubina!!!");
			idProyectoPreregistrado = (Integer) ActionContext.getContext()
					.getSession()
					.get(NombreObjetosSesion.ID_PROYECTO_PREREGISTRADO);
			return new DefaultHttpHeaders("preregistro").setLocationId(model
					.getIdUsuario());
		}
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdUsuario());
	}

	@SkipValidation
	public String edit() {

		perfilUsuarios = perfilUsuarioNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PERFIL_USUARIOS, perfilUsuarios);

		listareas = areaNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.AREAS, listareas);

		this.proyecto = usuarioNegocio.validaTrabajoAsociado(model);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROYECTO, proyecto);
		Usuario user = new Usuario();
		user.setActivado(true);
		usuariosExistentes = usuarioNegocio.findByExample(user);

		for (int i = 0; i < usuariosExistentes.size(); i++) {
			if (usuariosExistentes.get(i).getIdUsuario().equals(model
					.getIdUsuario())) {
				usuariosExistentes.remove(i);
			}
		}

		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.USUARIOS_EXISTENTES,
						usuariosExistentes);

		Usuario usuario = new Usuario();
		usuario = usuarioNegocio.findById(model.getIdUsuario());
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.USUARIOSEL, usuario);

		listContactos = usuario.getContactos();

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.CONTACTOS, listContactos);

		return "edit";
	}

	@SkipValidation
	public String view() {
		PerfilUsuario perfilNuevo = new PerfilUsuario();
		perfilNuevo.setIdPerfilUsuario(2);

		perfilUsuarios = perfilUsuarioNegocio.findByExample(perfilNuevo);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PERFIL_USUARIOS, perfilUsuarios);
		listareas = areaNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.AREAS, listareas);

		Usuario usuario;
		usuario = usuarioNegocio.findById(model.getIdUsuario());
		listContactos = usuario.getContactos();

		return "view";
	}

	public void validateUpdate() {

	}

	@Validations(requiredStrings = {

			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.apPat", type = ValidatorType.FIELD, key = "introApPat"),
			@RequiredStringValidator(fieldName = "model.apMat", type = ValidatorType.FIELD, key = "introApMat"),
			@RequiredStringValidator(fieldName = "model.psw", type = ValidatorType.FIELD, key = "introPass"),
			@RequiredStringValidator(fieldName = "psw2", type = ValidatorType.FIELD, key = "introPass2"),
			@RequiredStringValidator(fieldName = "model.cargo", type = ValidatorType.FIELD, key = "introCargo"),
			@RequiredStringValidator(fieldName = "model.rfc", type = ValidatorType.FIELD, key = "introRFC"),
			@RequiredStringValidator(fieldName = "model.direccion.calle", type = ValidatorType.FIELD, key = "introCalle"),
			@RequiredStringValidator(fieldName = "model.direccion.colonia", type = ValidatorType.FIELD, key = "introCol"),
			@RequiredStringValidator(fieldName = "model.direccion.deleg", type = ValidatorType.FIELD, key = "introDel"),
			@RequiredStringValidator(fieldName = "model.direccion.edo", type = ValidatorType.FIELD, key = "introEdo"),
			@RequiredStringValidator(fieldName = "model.direccion.pais", type = ValidatorType.FIELD, key = "introPais") },

	expressions = { @ExpressionValidator(key = "introPassNotEq", expression = "psw2==model.psw") },

	requiredFields = {
			@RequiredFieldValidator(fieldName = "model.direccion.cp", key = "introCP", type = ValidatorType.SIMPLE),
			@RequiredFieldValidator(fieldName = "model.direccion.direccion", key = "introNum", type = ValidatorType.SIMPLE) },

	conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "model.direccion.cp", key = "cpError", type = ValidatorType.SIMPLE),
			@ConversionErrorFieldValidator(fieldName = "model.direccion.direccion", key = "numError", type = ValidatorType.SIMPLE) },

	intRangeFields = {
			@IntRangeFieldValidator(fieldName = "model.idPerfilUsuario", type = ValidatorType.SIMPLE, min = "0", key = "selecPerfil"),
			@IntRangeFieldValidator(fieldName = "model.idArea", type = ValidatorType.SIMPLE, min = "0", key = "selecArea") },

	regexFields = {
			@RegexFieldValidator(fieldName = "model.aptPat", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9]){1,50}", key = "apPatError"),
			@RegexFieldValidator(fieldName = "model.aptMat", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9]){1,50}", key = "apMatError"),
			@RegexFieldValidator(fieldName = "model.cargo", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,250}", key = "cargoError"),
			@RegexFieldValidator(fieldName = "model.rfc", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9-]){1,20}", key = "rfcError"),
			@RegexFieldValidator(fieldName = "model.direccion.calle", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "calleError"),
			@RegexFieldValidator(fieldName = "model.direccion.colonia", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "colError"),
			@RegexFieldValidator(fieldName = "model.direccion.deleg", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 ]){1,20}", key = "delError"),
			@RegexFieldValidator(fieldName = "model.direccion.pais", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9 -_,.#]){1,20}", key = "paisError")

	})
	public String update() {
		desactivarUsuario();
		usuarioNegocio.save(model);
		direccionNegocio.save(model.getDireccion());

		addActionMessage(getText("usuarioEdit"));

		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.PROYECTOS_ASOCIADOS,
						usuarioNegocio.validaTrabajoAsociado(model));
		return "deleteConfirm";
	}

	public void validateDestroy() {
		logger.trace("Validando destruccion del usuario");
		if (usuarioNegocio.validaTrabajoAsociado(model)) {
			addActionError(getText("elimUsuario"));
		}
		logger.trace("Validando la destrucción");
	}

	public String destroy() {
		logger.trace("Destruccion del usuario");
		usuarioNegocio.borrarUsuario(model);
		logger.trace("Se destruyo esta madre");
		addActionMessage(getText("usuarioElim"));

		return "success";
	}

	public void desactivarUsuario() {
		if (!model.getActivado()) {
			model.setPsw("");
			if (model.getProyectos() != null && model.getProyectos().size() > 0) {
				proyectos = model.getProyectos();
				Proyecto proy;

				for (int i = 0; i < proyectos.size(); i++) {
					proy = proyectoNegocio.findById(proyectos.get(i)
							.getIdProyecto());
					proy.setIdResponsable(idUser);
					proyectoNegocio.save(proy);
				}
			}
			addActionMessage("La cuenta del usuario " + model.getLogin()
					+ " ha sido desactivada.");
		} else {
			if (model.getPsw().equals("")) {
				model.setPsw("1234");
			}
			addActionMessage("La cuenta del usuario " + model.getLogin()
					+ " esta activa.");
		}
	}

	public void validate() {
		perfilUsuarios = perfilUsuarioNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PERFIL_USUARIOS, perfilUsuarios);
		listareas = areaNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.AREAS, listareas);

	}

	public Usuario getModel() {
		if (model == null) {
			model = new Usuario();
		}
		return model;
	}

	@SuppressWarnings("unchecked")
	// Por el cast de objeto a lista de usuarios
	public List<Usuario> getUsuariosExistentes() {
		if (usuariosExistentes == null) {
			usuariosExistentes = (List<Usuario>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.USUARIOS_EXISTENTES);
		}
		return usuariosExistentes;
	}

	@SuppressWarnings("unchecked")
	// Por el cast de objeto a lista de usuarios
	public List<PerfilUsuario> getPerfilUsuarios() {
		if (perfilUsuarios == null) {
			perfilUsuarios = (List<PerfilUsuario>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.PERFIL_USUARIOS);
		}
		return perfilUsuarios;
	}

	@SuppressWarnings("unchecked")
	// Por el cast de objeto a lista de usuarios
	public List<Area> getListareas() {
		if (listareas == null) {
			listareas = (List<Area>) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.AREAS);
		}
		return listareas;
	}

	public Boolean isProyecto() {
		if (proyecto == null) {
			proyecto = (Boolean) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.PROYECTO);
		}
		return proyecto;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public void setUsuariosExistentes(List<Usuario> usuariosExistentes) {
		this.usuariosExistentes = usuariosExistentes;
	}

	public List<Usuario> getList() {
		return list;
	}

	public void setList(List<Usuario> list) {
		this.list = list;
	}

	public void setPerfilUsuarioNegocio(
			PerfilUsuarioNegocio perfilUsuarioNegocio) {
		this.perfilUsuarioNegocio = perfilUsuarioNegocio;
	}

	public PerfilUsuarioNegocio getPerfilUsuarioNegocio() {
		return this.perfilUsuarioNegocio;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Usuario model) {
		this.model = model;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public void setPerfilUsuarios(List<PerfilUsuario> perfilUsuarios) {
		this.perfilUsuarios = perfilUsuarios;
	}

	public DireccionNegocio getDireccionNegocio() {
		return direccionNegocio;
	}

	public void setDireccionNegocio(DireccionNegocio direccionNegocio) {
		this.direccionNegocio = direccionNegocio;
	}

	public TipoContacto getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(TipoContacto tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	public AreaNegocio getAreaNegocio() {
		return areaNegocio;
	}

	public void setAreaNegocio(AreaNegocio areaNegocio) {
		this.areaNegocio = areaNegocio;
	}

	public void setListareas(List<Area> listareas) {
		this.listareas = listareas;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public void setProyecto(boolean proyecto) {
		this.proyecto = proyecto;
	}

	public ContactoNegocio getContactoNegocio() {
		return contactoNegocio;
	}

	public void setContactoNegocio(ContactoNegocio contactoNegocio) {
		this.contactoNegocio = contactoNegocio;
	}

	public Contacto getTel() {
		return tel;
	}

	public void setTel(Contacto tel) {
		this.tel = tel;
	}

	public Contacto getCorreo() {
		return correo;
	}

	public void setCorreo(Contacto correo) {
		this.correo = correo;
	}

	public Boolean getProyecto() {
		return proyecto;
	}

	public void setProyecto(Boolean proyecto) {
		this.proyecto = proyecto;
	}

	public String getPsw2() {
		return psw2;
	}

	public void setPsw2(String psw2) {
		this.psw2 = psw2;
	}

	public Integer getIdProyectoPreregistrado() {
		return idProyectoPreregistrado;
	}

	public void setIdProyectoPreregistrado(Integer idProyectoPreregistrado) {
		this.idProyectoPreregistrado = idProyectoPreregistrado;
	}

	@SuppressWarnings("unchecked")
	// Por el cast de objeto a lista de contactos.
	public List<Contacto> getListContactos() {

		if (listContactos == null) {
			listContactos = (List<Contacto>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.CONTACTOS);

		}

		return listContactos;
	}

	public void setListContactos(List<Contacto> listContactos) {
		this.listContactos = listContactos;
	}

	public Integer getIdUsuarioSel() {
		return idUsuarioSel;
	}

	public void setIdUsuarioSel(Integer idUsuarioSel) {
		this.idUsuarioSel = idUsuarioSel;
	}

}
