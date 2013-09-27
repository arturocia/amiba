package mx.ipn.escom.cdt.besp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nivel;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;
import mx.ipn.escom.cdt.besp.negocio.NivelNegocio;
import mx.ipn.escom.cdt.besp.negocio.PeriodoNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProgramaNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-estructura" }),
		@Result(name = "sinNivel", location = "catalogo-estructura/index.jsp") })
@RemoteProxy
public class CatalogoEstructuraController extends ActionSupport implements
		ModelDriven<Estructura> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2435993624471516075L;
	private Logger logger = Logger
			.getLogger(CatalogoEstructuraController.class);
	private UsuarioNegocio usuarioNegocio;

	public static final int BANDERA_DURACION = 0;
	public static final int BANDERA_FECHA = 1;
	public static final int BANDERA_INDEFINIDO = 2;
	private EstructuraNegocio estructuraNegocio;
	private ProgramaNegocio programaNegocio;
	private PeriodoNegocio periodoNegocio;
	private NivelNegocio nivelNegocio;
	private Integer idSel;
	private Estructura model;
	private Programa programaSel;
	private Estructura estructuraPadreSel;
	private String ruta;
	private Integer banderaTipoPeriodo;
	private Integer banderaTipoPeriodoPadre;
	private Integer idEstructuraPadreSel;
	private Nivel nivelEstructuraSel;
	private Nodo nodoRestrictivo;
	private Integer idProgramaSel;

	@SkipValidation
	public HttpHeaders index() {
		Usuario usuario;
		usuario = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		usuario = usuarioNegocio.findById(usuario.getIdUsuario());
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.USUARIO, usuario);
		ActionContext.getContext().getSession()
				.remove(NombreObjetosSesion.ESTRUCTURA_PADRE);

		if (idProgramaSel != null) {
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.PROGRAMA,
							programaNegocio.findById(idProgramaSel));
			logger.trace("El puto programa es "
					+ ActionContext.getContext().getSession()
							.get(NombreObjetosSesion.PROGRAMA));
		}
		if (idProgramaSel == null
				&& ActionContext.getContext().getSession()
						.get(NombreObjetosSesion.PROGRAMA) == null) {
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.PROGRAMA,
							((Usuario) ActionContext.getContext().getSession()
									.get(NombreObjetosSesion.USUARIO))
									.getProgramas().get(0));
			logger.trace("El programa del usuario "
					+ usuario.getLogin()
					+ " es "
					+ ((Usuario) ActionContext.getContext().getSession()
							.get(NombreObjetosSesion.USUARIO)).getProgramas());
		}
		return new DefaultHttpHeaders("index").disableCaching();
	}
	/**
	 * 4/jul/20013
	 * 
	 * 
	 * @return
	 */

	public String editNew() {
		List<Nivel> niveles;
		logger.trace("En editNew");
		estructuraPadreSel = (Estructura) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.ESTRUCTURA_PADRE);
		programaSel = (Programa) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROGRAMA);
		if (idEstructuraPadreSel != null) {
			estructuraPadreSel = estructuraNegocio.findById(idEstructuraPadreSel);
			ActionContext.getContext().getSession().put(NombreObjetosSesion.ESTRUCTURA_PADRE,estructuraPadreSel);
		}else{
			banderaTipoPeriodoPadre=programaSel.getPeriodo().getTipoPeriodo();
			if(periodoNegocio.isDefinido(programaSel)){
				banderaTipoPeriodo=BANDERA_FECHA;
			}else{
				banderaTipoPeriodo=BANDERA_INDEFINIDO;
			}
			
			
		}

		if (estructuraPadreSel != null) {
			banderaTipoPeriodoPadre=estructuraPadreSel.getPeriodo().getTipoPeriodo();
			if(estructuraPadreSel.getPeriodo().getTipoPeriodo().equals(Periodo.PERIODO_DEFINIDO)){
				banderaTipoPeriodo=BANDERA_FECHA;
			}else{
				banderaTipoPeriodo=BANDERA_INDEFINIDO;
			}
			
			

			ruta = estructuraNegocio.getRuta(estructuraPadreSel.getId());

			nivelEstructuraSel = new Nivel();
			nivelEstructuraSel.setIdPrograma(programaSel.getId());
			nivelEstructuraSel.setPosicion(estructuraPadreSel.getIdNivel() + 1);
			niveles = nivelNegocio.findByExample(nivelEstructuraSel);

			model.setPrograma(programaSel);
			model.setIdNivel(estructuraPadreSel.getIdNivel() + 1);
			if (!estructuraNegocio.validarNivel(model)) {
				addActionError("No se puede crear por que excede el nivel de profundidad definido por el programa");
				return "sinNivel";
			}

			logger.trace("Nombre de la estr sel: "
					+ estructuraPadreSel.getNombre() + " nivel nombre: "
					+ niveles.get(0).getNombre() + " nivel"
					+ niveles.get(0).getPosicion());

			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.NIVEL_ESTRUCTURA, niveles.get(0));
			logger.trace("Se subio a sesion nivel de padre " + niveles.get(0));
			ruta = estructuraNegocio.getRuta(idEstructuraPadreSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.MIGAJAS_ESTRUCTURA, ruta);
			model.setNodoPadre(estructuraPadreSel);

		} else {
			logger.trace("El programa al q se a�adira una estructura de primer nivel "
					+ programaSel.getNombre());
			if (!programaNegocio.tieneNiveles(programaSel)) {
				addActionError("El programa seleccionado debe tener niveles seleccionados");
				return "sinNivel";
			}
			ruta = programaSel.getNombre();
			model.setNodoPadre(programaSel);
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.NIVEL_ESTRUCTURA,
							programaSel.getNiveles().get(0));
			ruta = programaSel.getNombre();
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.MIGAJAS_ESTRUCTURA, ruta);
		}

		nodoRestrictivo = periodoNegocio
				.getNodoPadreConPeriodoRestrictivo(model);

		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.NODO_CON_PERIODO_RESTRICTIVO,
						nodoRestrictivo);
		ActionContext.getContext().getSession().put("banderaPAdre",banderaTipoPeriodoPadre);
		return "editNew";
	}

	public String show() {
		return "show";
	}

	public void validateCreate() {
		
		banderaTipoPeriodoPadre=(Integer) ActionContext.getContext().getSession().get("banderaPAdre");
		
		logger.trace("Validando la creación");
		Nodo nodoPadre = null;

		nodoPadre = (Nodo) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ESTRUCTURA_PADRE);
		if (nodoPadre == null) {
			nodoPadre = ((Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO)).getProgramas().get(0);
		}
		nivelEstructuraSel = (Nivel) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.NIVEL_ESTRUCTURA);
		programaSel = (Programa) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROGRAMA);

		model.setIdNivel(nivelEstructuraSel.getPosicion());
		logger.trace("El nivel de la estructura es " + nivelEstructuraSel);

		model.setIdPadre(nodoPadre.getClass().equals(Estructura.class) ? ((Estructura) nodoPadre)
				.getIdEstructura() : null);
		model.setEstructuraPadre(nodoPadre.getClass().equals(Estructura.class) ? (Estructura) nodoPadre
				: null);

		if (model.getPeriodo() == null) {
			model.setPeriodo(new Periodo());
		}

		model.setPrograma(programaSel);

		if (!estructuraNegocio.validarNivel(model)) {
			addActionError("No se puede crear por que excede el nivel de profundidad definido por el programa");
		}

		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser indroducida");
				}
				break;
			case BANDERA_FECHA:
				if (model.getPeriodo().getFechaFin() == null) {
					addActionError("La fecha final debe ser indroducida");
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser indroducida");
				}
				break;
			case BANDERA_INDEFINIDO:
				break;
			default:
				break;
			}
		} else {
			addActionError(getText("banderaTipoPeriodo"));
		}

		if (!periodoNegocio.validaPeriodo(model)) {
			addActionError("El periodo entra en conflicto con los ancestros de esta estructura");
		}
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError("El periodo no esta bien formado");
		}
		if (!estructuraNegocio.validaNombreUnico(model)) {
			addActionError(getText("validaNombreUnicoEstructura"));
		}

	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, key = "Datos incompletos"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, key = "introDescrip") }, regexFields = {
			@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-)+", key = "nombreErrore"),
			@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,250}", key = "descripcionError.max250") }

	)
	public String create() {
		Periodo periodo;

		logger.trace("en la creación del cielo y de la tierra");

		periodo = periodoNegocio.save(model.getPeriodo());

		model.setIdPeriodo(periodo.getIdPeriodo());
		nivelEstructuraSel = (Nivel) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.NIVEL_ESTRUCTURA);
		estructuraPadreSel = (Estructura) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.ESTRUCTURA_PADRE);
		programaSel = (Programa) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROGRAMA);

		model.setIdPadre(estructuraPadreSel == null ? null : estructuraPadreSel
				.getId());
		model.setIdPrograma(programaSel.getId());
		model.setIdNivel(nivelEstructuraSel.getPosicion());

		logger.trace("Nivel nuevo: " + model.getIdNivel());

		model = estructuraNegocio.save(model);

		return SUCCESS;

	}

	@SkipValidation
	public String edit() {

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.NIVEL_ESTRUCTURA, model.getNivel());

		ruta = estructuraNegocio.getRuta(idSel);
		nodoRestrictivo = periodoNegocio
				.getNodoPadreConPeriodoRestrictivo(model);

		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.NODO_CON_PERIODO_RESTRICTIVO,
						nodoRestrictivo);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.MIGAJAS_ESTRUCTURA, ruta);
		getBanderaTipoPeriodo();
		
		
		if(model.getEstructuraPadre()==null){
			banderaTipoPeriodoPadre=Periodo.PERIODO_DEFINIDO;
		}else{
			if(model.getEstructuraPadre().getPeriodo().getTipoPeriodo().equals(Periodo.PERIODO_DEFINIDO)){
				banderaTipoPeriodoPadre=Periodo.PERIODO_DEFINIDO;
			}else{
				banderaTipoPeriodoPadre=Periodo.PERIODO_INDEFINIDO;
			}
		}
		ActionContext.getContext().getSession().put("banderaPAdre",banderaTipoPeriodoPadre);
		return "edit";
	}

	public void validateUpdate() {
		banderaTipoPeriodoPadre=(Integer) ActionContext.getContext().getSession().get("banderaPAdre");
		
		logger.trace("************ENTRANDO A LA VALIDACION DE UPDATE*******");
		if (!periodoNegocio.validaPeriodo(model)) {
			addActionError(getText("errorValidaPeriodo"));
		}
		logger.trace("valido periodo");
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError(getText("errorValidaBienFormado"));
		}
		logger.trace("valido periodo bien formado");
		if (!estructuraNegocio.validaNombreUnico(model)) {
			addActionError(getText("validaNombreUnicoEstructura"));
		}
		if (!estructuraNegocio.validaPeriodoHaciaAbajo(model)) {
			addActionError(getText("errorValidaEstructuraHaciaAbajo"));
		}
		logger.trace("termino de validar ipdate");
	
	
		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser indroducida");
				}
				break;
			case BANDERA_FECHA:
				if (model.getPeriodo().getFechaFin() == null) {
					addActionError("La fecha final debe ser indroducida");
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser indroducida");
				}
				String patronFecha = "dd/MM/yyyy";
				SimpleDateFormat formato = new SimpleDateFormat(patronFecha);
				
				Date fechaMenorHijos = periodoNegocio.obtieneFechaInicialMenorHijos(model);
				Date fechaMayorHijos = periodoNegocio.obtieneFechaFinalMayorHijos(model);
				
				if(fechaMenorHijos!=null && fechaMenorHijos!=null){
					if(model.getPeriodo().getFechaInicio().after(fechaMenorHijos)){
						addActionError("La fecha inicial debe ser menor a la fecha "+formato.format(fechaMenorHijos));
					}
					if(model.getPeriodo().getFechaFin().before(fechaMayorHijos)){
						addActionError("La fecha final debe ser mayor a la fecha "+formato.format(fechaMayorHijos));
					}
				}
				
				break;
			case BANDERA_INDEFINIDO:
				if(periodoNegocio.tieneHijosConFecha(model)){
					addActionError("No se puede modificar el tipo de periodo.");
				}
				break;
			default:
				break;
			}
		} else {
			addActionError(getText("banderaTipoPeriodo"));
		}
	
	
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, key = "introDescrip") }, regexFields = {
			@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-)+", key = "nombreErrore"),
			@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,250}", key = "descripcionError.max250") }

	)
	public String update() {

		periodoNegocio.save(model.getPeriodo());
		model = estructuraNegocio.update(model);

		addActionMessage("El/La " + model + " se modificó exitosamente");
		return "success";
	}

	public void validateDestroy() {
		logger.trace("Validando la destrucción");
		if (estructuraNegocio.validaEstructurasAlineadas(model)) {
			addActionError("La estructura no debe tener proyectos alineados");
		}
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public String destroy() {
		Periodo periodo;
		logger.trace("Eliminando la basura");

		periodo = model.getPeriodo();
		estructuraNegocio.delete(model);
		periodoNegocio.delete(periodo);
		addActionMessage("El/La " + model.getNombre()
				+ " se eliminó exitosamente");

		return "success";
	}

	@RemoteMethod
	public Nodo getNodos() {
		Nodo raiz;
		Programa programa;

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();

		logger.trace("El programa de mierda en getNodos es "
				+ req.getSession().getAttribute(NombreObjetosSesion.PROGRAMA));

		programa = programaNegocio.findById(((Programa) req.getSession()
				.getAttribute(NombreObjetosSesion.PROGRAMA)).getIdPrograma());
		raiz = programa;
		logger.trace("Aventando las estructuras de " + programa);
		programa.getNodosHijo();
		logger.trace("Que son " + programa.getNodosHijo().size());
		return raiz;
	}

	public Integer getBanderaTipoPeriodo() {
		if (banderaTipoPeriodo == null && model != null
				&& model.getPeriodo() != null) {
			switch (model.getPeriodo().getTipoPeriodo()) {
			case Periodo.PERIODO_INDEFINIDO:
				banderaTipoPeriodo = BANDERA_INDEFINIDO;
				break;
			case Periodo.PERIODO_RELATIVO:
				banderaTipoPeriodo = BANDERA_DURACION;
				break;
			case Periodo.PERIODO_DEFINIDO:
				if (model.getPeriodo().getFechaFin() == null) {
					banderaTipoPeriodo = BANDERA_DURACION;
				} else {
					banderaTipoPeriodo = BANDERA_FECHA;
				}
				break;
			default:
				break;
			}
		}
		return banderaTipoPeriodo;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = estructuraNegocio.findById(idSel);
		}
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;

	}

	public EstructuraNegocio getEstructuraNegocio() {
		return estructuraNegocio;
	}

	public void setEstructuraNegocio(EstructuraNegocio estructuraNegocio) {
		this.estructuraNegocio = estructuraNegocio;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public Estructura getModel() {
		if (model == null) {
			model = new Estructura();
		}
		return model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Estructura model) {
		this.model = model;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}

	public void setBanderaTipoPeriodo(Integer banderaTipoPeriodo) {
		this.banderaTipoPeriodo = banderaTipoPeriodo;
	}

	public NivelNegocio getNivelNegocio() {
		return nivelNegocio;
	}

	public void setNivelNegocio(NivelNegocio nivelNegocio) {
		this.nivelNegocio = nivelNegocio;
	}

	public String getRuta() {
		if (ruta == null) {
			ruta = (String) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.MIGAJAS_ESTRUCTURA);
		}
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public Programa getProgramaSel() {
		return programaSel;
	}

	public void setProgramaSel(Programa programaSel) {
		this.programaSel = programaSel;
	}

	public Estructura getEstructuraPadreSel() {
		return estructuraPadreSel;
	}

	public void setEstructuraPadreSel(Estructura estructuraPadreSel) {
		this.estructuraPadreSel = estructuraPadreSel;
	}

	public Integer getIdEstructuraPadreSel() {
		return idEstructuraPadreSel;
	}

	public void setIdEstructuraPadreSel(Integer idEstructuraPadreSel) {
		this.idEstructuraPadreSel = idEstructuraPadreSel;
	}

	public Nodo getNodoRestrictivo() {
		return nodoRestrictivo;
	}

	public void setNodoRestrictivo(Nodo nodoRestrictivo) {
		this.nodoRestrictivo = nodoRestrictivo;
	}

	public Nivel getNivelEstructuraSel() {
		return nivelEstructuraSel;
	}

	public void setNivelEstructuraSel(Nivel nivelEstructuraSel) {
		this.nivelEstructuraSel = nivelEstructuraSel;
	}

	public Integer getIdProgramaSel() {
		return idProgramaSel;
	}

	public void setIdProgramaSel(Integer idProgramaSel) {
		this.idProgramaSel = idProgramaSel;
	}

	public Integer getBanderaTipoPeriodoPadre() {
		return banderaTipoPeriodoPadre;
	}

	public void setBanderaTipoPeriodoPadre(Integer banderaTipoPeriodoPadre) {
		this.banderaTipoPeriodoPadre = banderaTipoPeriodoPadre;
	}

	
}
