package mx.ipn.escom.cdt.besp.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.Estado;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.PeriodoNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.ConversionErrorFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "operacion-editar-datos-proyecto/%{model.idProyecto}" }),
		@Result(name = "input", location = "registrar-proyecto/index.jsp") })
public class RegistrarProyectoController extends ActionSupport implements
		ModelDriven<Proyecto> {

	private static final long serialVersionUID = -5026251450188020528L;
	private static final int BANDERA_DURACION = 1;
	private static final int BANDERA_FECHA = 2;
	private static final int BANDERA_INDEFINIDO = 3;
	private Integer banderaTipoPeriodo;
	private UsuarioNegocio usuarioNegocio;
	private String mail, tel;
	private Usuario usuarioActual;
	private ProyectoNegocio proyectoNegocio;
	private PeriodoNegocio periodoNegocio;
	private Proyecto model;
	private List<Usuario> listCoordinadores;
	private Integer idCoordinadorSel = null;

	@SkipValidation
	public HttpHeaders index() {

		Usuario usuarioExample = new Usuario();
		usuarioExample.setActivado(true);

		usuarioActual = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		List<Contacto> contactos;

		contactos = usuarioActual.getContactos();

		for (Contacto contacto : contactos) {
			if (contacto.getPrincipal() == 1) {
				// saco los contactos
				if (contacto.getIdTipoContacto() == TipoContacto.CORREO)
				{
					mail = contacto.getContacto();
				}
				if (contacto.getIdTipoContacto() == TipoContacto.TELEFONO)
				{
					tel = contacto.getContacto();
				}
			}
		}

		listCoordinadores = usuarioNegocio.findByExample(usuarioExample);
		for (int i = 0; i < listCoordinadores.size();) {
			if (listCoordinadores.get(i).getPerfilUsuario().getIdPerfilUsuario() != 2) {
				listCoordinadores.remove(i);
			} else {
				i++;
			}
		}
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.LIST_COORDINADORES, listCoordinadores);
		return new DefaultHttpHeaders("index").disableCaching();
	}

	
	public void validateCreate() {
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError("El periodo no esta bien formado");
		}
		if(model.getCostoTotal()==null){
			addActionError("Favor de introducir el Costo Total");
		}else if (!proyectoNegocio.validaMayorAcero(model)) {			
			addActionError("El Costo Total debe ser mayor a cero");
		}
		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser indroducida");
				} else {
					if (model.getPeriodo().getDuracion() < 0)
					{
						addActionError("La duración no debe ser negativa");
					}
				}
				break;
			case BANDERA_FECHA:
				if (model.getPeriodo().getFechaFin() == null) {
					addActionError("La fecha final debe ser indroducida");
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser indroducida");
				}

				if (model.getPeriodo().getFechaInicio() != null
						&& model.getPeriodo().getFechaFin() != null
						&& (model.getPeriodo().getFechaInicio().getTime() > model
								.getPeriodo().getFechaFin().getTime())) {
					addActionError("La fecha inicial debe ser menor a la fecha final");
				}
				break;
			case BANDERA_INDEFINIDO:

				break;
			default:
				addActionError(getText("banderaTipoPeriodo"));
				break;
			}
		} else {
			addActionError(getText("banderaTipoPeriodo"));
		}
		
		
	}
	

	@SuppressWarnings("unchecked")
	// Por el cast de objeto a lista de usuarios
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.siglas", type = ValidatorType.FIELD, key = "introSiglas"),
			@RequiredStringValidator(fieldName = "model.resumen", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "model.objetivoGeneral", type = ValidatorType.FIELD, key = "introObj") }, 
			conversionErrorFields = {
			@ConversionErrorFieldValidator(fieldName = "model.costoTotal", key = "costoTotalError", type = ValidatorType.SIMPLE) })
	public HttpHeaders create() {
		List<Proyecto> listProyectos;
		usuarioActual = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		Periodo periodo;
		Date fechaHoy = new Date();
		fechaHoy.getTime();
		periodo = periodoNegocio.save(model.getPeriodo());
		model.setIdPeriodo(periodo.getIdPeriodo());
		model.setIdEstado(Estado.REGISTRADO);
		listCoordinadores = (List<Usuario>) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.LIST_COORDINADORES);
		if (idCoordinadorSel == null) {
			model.setIdResponsable(usuarioActual.getIdUsuario());
		} else {
			model.setIdResponsable(idCoordinadorSel);
		}
		model.setFechaRegistro(fechaHoy);
		model = proyectoNegocio.save(model);
		listProyectos = proyectoNegocio.proyectosUsuario(usuarioActual
				.getIdUsuario());
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROYECTOS_ASOCIADOS, listProyectos);
		addActionMessage(getText("proyectoRegistrado"));
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdProyecto());
	}


	public Integer getBanderaTipoPeriodo() {
		return banderaTipoPeriodo;
	}

	public void setBanderaTipoPeriodo(Integer banderaTipoPeriodo) {
		this.banderaTipoPeriodo = banderaTipoPeriodo;
	}

	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public Usuario getUsuarioActual() {
		if (usuarioActual == null) {
			usuarioActual = (Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO);
		}
		return usuarioActual;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public String getMail() {
		if (mail == null) {
			usuarioActual = (Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO);
			List<Contacto> contactos;

			contactos = usuarioActual.getContactos();

			for (Contacto contacto : contactos) {
				// saco los contactos
				if (contacto.getPrincipal() == 1  && contacto.getIdTipoContacto() == TipoContacto.CORREO)
				{
					mail = contacto.getContacto();
				}
			}
		}
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTel() {
		if (tel == null) {
			usuarioActual = (Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO);
			List<Contacto> contactos;

			contactos = usuarioActual.getContactos();

			for (Contacto contacto : contactos) {
				// saco los contactos
				if (contacto.getPrincipal() == 1 && contacto.getIdTipoContacto() == TipoContacto.TELEFONO)
				{
						tel = contacto.getContacto();
				}
			}
		}
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public Proyecto getModel() {
		if (model == null) {
			model = new Proyecto();
		}
		return model;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public void setListCoordinadores(List<Usuario> listCoordinadores) {
		this.listCoordinadores = listCoordinadores;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> getListCoordinadores() {
		if (listCoordinadores == null) {
			listCoordinadores = (List<Usuario>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.LIST_COORDINADORES);
		}
		return listCoordinadores;
	}

	public void setIdCoordinadorSel(Integer idCoordindadorSel) {
		this.idCoordinadorSel = idCoordindadorSel;
	}

	public Integer getIdCoordinadorSel() {
		return idCoordinadorSel;
	}
}