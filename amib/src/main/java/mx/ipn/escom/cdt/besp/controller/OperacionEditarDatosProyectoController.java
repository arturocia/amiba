package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import mx.ipn.escom.cdt.besp.modelo.Contacto;
import mx.ipn.escom.cdt.besp.modelo.EjeTematico;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;
import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.EjeTematicoNegocio;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;
import mx.ipn.escom.cdt.besp.negocio.PeriodoNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProgramaNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.negocio.TemaTransversalNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.jgeppert.struts2.jquery.tree.result.TreeNode;
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
				"actionName", "operacion-editar-datos-proyecto/%{model.id}" }),

		@Result(name = "traerEstructuras", type = "json", params = { "root",
				"estructurasSectorial" }) })
@RemoteProxy
public class OperacionEditarDatosProyectoController extends ActionSupport
		implements ModelDriven<Proyecto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7292201518830617104L;
	private Logger logger = Logger
			.getLogger(OperacionEditarDatosProyectoController.class);
	private UsuarioNegocio usuarioNegocio;
	private ProyectoNegocio proyectoNegocio;
	private EjeTematicoNegocio ejeTematicoNegocio;
	private TemaTransversalNegocio temaTransversalNegocio;
	private ProgramaNegocio programaNegocio;

	private Integer banderaTipoPeriodo;
	private Usuario usuarioActual;
	private Proyecto model;
	private List<EjeTematico> ejesProyecto;
	private List<TemaTransversal> temasProyecto;
	private List<EjeTematico> ejesDisponibles;
	private List<TemaTransversal> temasDisponibles;
	private Programa programaSectorial;
	private List<Programa> otrosProgramas;
	private List<Proyecto> listProyectos;

	private List<TreeNode> estructurasSectorial;
	private Integer idProgramaSel;
	private Integer idEstructuraSectorialSel;
	private Integer idEstructuraSel;
	private Integer idEstructuraSelTipo;
	private Boolean alineacion;
	private Boolean alineacionSectorial;
	private Integer idSel;
	private String mail, tel;
	private EstructuraNegocio estructuraNegocio;

	private PeriodoNegocio periodoNegocio;

	private List<EjeTematico> ejeTematicosDisponibles;
	private List<EjeTematico> ejeTematicosSelecccionados;
	private List<TemaTransversal> temaTransversalDisponibles;
	private List<TemaTransversal> temaTransversalSeleccionados;

	private List<Integer> idEjesTematicosDisponibles = new ArrayList<Integer>();
	private List<Integer> idEjesTematicosSeleccionados = new ArrayList<Integer>();

	private List<Integer> idTemasTransversalesDisponibles = new ArrayList<Integer>();
	private List<Integer> idTemasTransversalesSeleccionados = new ArrayList<Integer>();
	private List<String> caminosMigajas;

	private Date fecha = new Date();

	List<Estructura> estructurasAux;
	private Integer idEstructuraUnoAux, idEstructuraDosAux;
	private Integer banderaAux;
	private Boolean periNull;
	private Periodo periodoAux;

	@SkipValidation
	public String show() {
		List<Programa> todosProgramas;
		List<Contacto> contactos;
		List<Estructura> estructuras;
		Integer indiceInicioOtrosProgramas = 1;

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.OPERACION_ESTADO, 1);

		ejeTematicosDisponibles = proyectoNegocio.traerEjes(getModel());
		ejeTematicosSelecccionados = model.getEjeTematicos();

		temaTransversalDisponibles = proyectoNegocio.traerTemas(getModel());
		temaTransversalSeleccionados = model.getTemaTransversales();

		usuarioActual = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);

		estructuras = model.getEstructuras();

		for (Estructura estructura : estructuras) {
			if (estructura.getPrograma().getSectorial()) {
				idEstructuraSectorialSel = estructura.getId();
				alineacionSectorial = true;
			} else {

				idEstructuraSel = estructura.getId();
				ActionContext
						.getContext()
						.getSession()
						.put(NombreObjetosSesion.ID_ESTRUCTURA_SEL,
								idEstructuraSel);
				alineacion = true;
			}
		}

		contactos = usuarioActual.getContactos();

		for (Contacto contacto : contactos) {
			if (contacto.getPrincipal() == 1) {
				// saco los contactos
				if (contacto.getIdTipoContacto() == TipoContacto.CORREO) {
					mail = contacto.getContacto();
				}
				if (contacto.getIdTipoContacto() == TipoContacto.TELEFONO) {
					tel = contacto.getContacto();
				}
			}
		}

		temasDisponibles = temaTransversalNegocio.findAll();
		ejesDisponibles = ejeTematicoNegocio.findAll();

		todosProgramas = programaNegocio.getProgramaSectorialYOtros();
		logger.trace("Se encontraron " + todosProgramas.size()
				+ " programas en total");
		programaSectorial = todosProgramas.get(0);
		logger.trace("El programa sectorial que se encontro "
				+ programaSectorial);

		if (!programaSectorial.getSectorial()) {
			programaSectorial = null;
			indiceInicioOtrosProgramas = 0;
		}

		logger.trace("El indice de inicio de otros programas "
				+ indiceInicioOtrosProgramas);
		otrosProgramas = new ArrayList<Programa>();
		for (int i = indiceInicioOtrosProgramas; i < todosProgramas.size(); i++) {
			otrosProgramas.add(todosProgramas.get(i));
		}

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROGRAMA_SECTORIAL, programaSectorial);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROGRAMAS_OTROS, otrosProgramas);

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.EJES, ejesDisponibles);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TEMAS, temasDisponibles);

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROYECTO, model);
		getBanderaTipoPeriodo();

		return "show";
	}

	@SkipValidation
	public String edit() {
		banderaAux=1;
		List<Programa> todosProgramas;
		List<Contacto> contactos;
		List<Estructura> estructuras;
		Integer indiceInicioOtrosProgramas = 1;

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.OPERACION_ESTADO, 1);

		ejeTematicosDisponibles = proyectoNegocio.traerEjes(getModel());
		ejeTematicosSelecccionados = model.getEjeTematicos();

		temaTransversalDisponibles = proyectoNegocio.traerTemas(getModel());
		temaTransversalSeleccionados = model.getTemaTransversales();

		usuarioActual = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);

		estructuras = model.getEstructuras();

		for (Estructura estructura : estructuras) {
			if (estructura.getPrograma().getSectorial()) {
				idEstructuraSectorialSel = estructura.getId();
				alineacionSectorial = true;
			} else {

				idEstructuraSel = estructura.getId();
				ActionContext
						.getContext()
						.getSession()
						.put(NombreObjetosSesion.ID_ESTRUCTURA_SEL,
								idEstructuraSel);
				alineacion = true;
			}
		}

		contactos = usuarioActual.getContactos();

		for (Contacto contacto : contactos) {
			if (contacto.getPrincipal() == 1) {
				// saco los contactos
				if (contacto.getIdTipoContacto() == TipoContacto.CORREO) {
					mail = contacto.getContacto();
				}
				if (contacto.getIdTipoContacto() == TipoContacto.TELEFONO) {
					tel = contacto.getContacto();
				}
			}
		}

		temasDisponibles = temaTransversalNegocio.findAll();
		ejesDisponibles = ejeTematicoNegocio.findAll();

		todosProgramas = programaNegocio.getProgramaSectorialYOtros();
		logger.trace("Se encontraron " + todosProgramas.size()
				+ " programas en total");
		programaSectorial = todosProgramas.get(0);
		logger.trace("El programa sectorial que se encontro "
				+ programaSectorial);

		if (!programaSectorial.getSectorial()) {
			programaSectorial = null;
			indiceInicioOtrosProgramas = 0;
		}

		logger.trace("El indice de inicio de otros programas "
				+ indiceInicioOtrosProgramas);
		otrosProgramas = new ArrayList<Programa>();
		for (int i = indiceInicioOtrosProgramas; i < todosProgramas.size(); i++) {
			otrosProgramas.add(todosProgramas.get(i));
		}

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROGRAMA_SECTORIAL, programaSectorial);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROGRAMAS_OTROS, otrosProgramas);

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.EJES, ejesDisponibles);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TEMAS, temasDisponibles);

		getBanderaTipoPeriodo();

		estructurasAux = model.getEstructuras();

		if (estructurasAux != null && estructurasAux.size() > 0) {
			if (estructurasAux.size() == 1) {
				idEstructuraUnoAux = model.getEstructuras().get(0)
						.getIdEstructura();
			}
			if (estructurasAux.size() == 2) {
				idEstructuraUnoAux = model.getEstructuras().get(0)
						.getIdEstructura();
				idEstructuraDosAux = model.getEstructuras().get(1)
						.getIdEstructura();
			}
		}
		if(model.getPeriodo()!=null){
			
			System.out.println("perio del modelo: "+model.getPeriodo().getFechaInicio());
			setPeriodoAux(model.getPeriodo());
		}

		return "edit";
	}

	public void validateUpdate() {

		logger.trace("A punto de alinear " + model + " con estructuras "
				+ idEstructuraSel + "," + idEstructuraSectorialSel + " ejes "
				+ idEjesTematicosSeleccionados + " temas "
				+ idTemasTransversalesSeleccionados);
		Estructura estructura;
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError(getText("errorValidaBienFormado"));
		}
		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case CatalogoEstructuraController.BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser indroducida");
				} else {
					if (model.getPeriodo().getDuracion() < 0) {
						addActionError("La duración no debe ser negativa");
					}
				}
				break;
			case CatalogoEstructuraController.BANDERA_FECHA:
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
				
			
				
				if (periodoNegocio.tieneHijosConFecha(model)) {
						if(!(model.getPeriodo().getFechaInicio().equals(periodoAux.getFechaInicio())) ||
								!(model.getPeriodo().getFechaFin().equals(periodoAux.getFechaFin()))){
					
							addActionError("No es posible editar el periodo ya que tiene metas con fecha, favor de verificar.");
						}		
				}
				break;
			case CatalogoEstructuraController.BANDERA_INDEFINIDO:
				
				if (periodoNegocio.tieneHijosConFecha(model)) {
					addActionError("No es posible actualizar el tipo de periodo ya que tiene metas con fecha, favor de verificar.");
				}

				break;
			default:
				addActionError(getText("banderaTipoPeriodo"));
				break;
			}
		} else {
			addActionError(getText("banderaTipoPeriodo"));
		}

		logger.trace("Validando la creación");
		logger.trace("La estructura " + idEstructuraSel + " y la sectorial "
				+ idEstructuraSectorialSel);
		logger.trace("La alineacion sectorial " + alineacionSectorial
				+ " y la normal " + alineacion);
		if (alineacion != null && !alineacion && alineacionSectorial != null
				&& !alineacionSectorial) {
			addActionError(getText("alineacion"));
		}
		if (alineacion != null && alineacion && idEstructuraSel == null) {
			addActionError(getText("alineacionIncorrecta"));
		}
		if (alineacionSectorial != null && alineacionSectorial
				&& idEstructuraSectorialSel == null) {
			addActionError(getText("alineacionSectorialIncorrecta"));
		}
		model.setEstructuras(new ArrayList<Estructura>());
		if (idEstructuraSel != null) {
			estructura = estructuraNegocio.findById(idEstructuraSel);
			logger.trace("El nodo padres es " + estructura);
			model.getEstructuras().add(estructura);

		}
		if (idEstructuraSectorialSel != null) {
			estructura = estructuraNegocio.findById(idEstructuraSectorialSel);
			logger.trace("El nodo padres es " + estructura);
			model.getEstructuras().add(estructura);
		}
		
		if(periNull){
			addActionError(getText("errorIntercepcionPeriodo"));
		}
		
		if (!periodoNegocio.validaPeriodoEstructura(model)) {
			addActionError(getText("errorValidaPeriodo"));
		}

		if (!proyectoNegocio.validaNombreUnico(model)) {
			addActionError(getText("errorValidaNombreUnico"));
		}

		if (model.getCostoTotal() == null) {
			addActionError("Favor de introducir el Costo Total");
		} else if (!proyectoNegocio.validaMayorAcero(model)) {
			addActionError("El Costo Total debe ser mayor a cero");
		}

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.ID_ESTRUCTURA_SEL, idEstructuraSel);

		if (idEstructuraUnoAux != null && idEstructuraDosAux == null) {
			if (idEstructuraUnoAux.equals(idEstructuraSel)
					|| idEstructuraUnoAux.equals(idEstructuraSectorialSel)) {
			} else {
				if (periodoNegocio.tieneHijosConFecha(model)) {
					addActionError("No es posible asociar ya que tiene metas con fecha, favor de verificar.");
				}
			}
		}
		
		

		if (idEstructuraUnoAux != null && idEstructuraDosAux != null) {
			if ((idEstructuraUnoAux.equals(idEstructuraSel) && idEstructuraDosAux
					.equals(idEstructuraSectorialSel))
					|| (idEstructuraUnoAux.equals(idEstructuraSectorialSel) && idEstructuraDosAux
							.equals(idEstructuraSel))) {
			} else {
				if (periodoNegocio.tieneHijosConFecha(model)) {
					addActionError("No es posible asociar ya que tiene metas con fecha, favor de verificar.");
				}
			}
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.siglas", type = ValidatorType.FIELD, key = "introSiglas"),
			@RequiredStringValidator(fieldName = "model.resumen", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "model.objetivoGeneral", type = ValidatorType.FIELD, key = "introDescrip") }, conversionErrorFields = { @ConversionErrorFieldValidator(fieldName = "model.costoTotal", key = "costoTotalError", type = ValidatorType.SIMPLE) })
	public String update() {

		logger.trace("A punto de alinear " + model + " con estructuras "
				+ idEstructuraSel + "," + idEstructuraSectorialSel + " ejes "
				+ idEjesTematicosSeleccionados + " temas "
				+ idTemasTransversalesSeleccionados);

		proyectoNegocio.alinearProyecto(model, idEstructuraSel,
				idEstructuraSectorialSel, idEjesTematicosSeleccionados,
				idTemasTransversalesSeleccionados);

		model.setFechaModificacion(fecha);
		model.setProyectoEstructuras(null);
		proyectoNegocio.update(model);

		usuarioActual = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		listProyectos = proyectoNegocio.proyectosUsuario(usuarioActual
				.getIdUsuario());
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROYECTOS_ASOCIADOS, listProyectos);
		return "success";
	}

	@SkipValidation
	public String traerEstructurasSectorial() {
		TreeNode raizNodoArbol;
		if (idProgramaSel.equals(0)) {
			programaSectorial = (Programa) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.PROGRAMA_SECTORIAL);
			raizNodoArbol = new TreeNode();
			raizNodoArbol.setChildren(new ArrayList<TreeNode>());
			mapeaNodoATreeNode(programaSectorial, raizNodoArbol);
			estructurasSectorial = (List<TreeNode>) raizNodoArbol.getChildren();
			logger.trace("Aventando las estructuras del sectorial "
					+ estructurasSectorial.size());
		}

		return "traerEstructuras";
	}

	@SkipValidation
	@SuppressWarnings("unchecked")
	// Por el cast de Object a lista de programas
	public String traerEstructura() {
		Programa programaSel;
		TreeNode raizNodoArbol;
		TreeNode nodoArbol;
		Integer idEstructuraSeleccionada;
		List<Programa> programasOtros = null;
		Estructura estructuraSeleccionada = null;
		logger.trace("Aventando estructuras de " + idProgramaSel);

		raizNodoArbol = new TreeNode();
		raizNodoArbol.setChildren(new ArrayList<TreeNode>());

		if (idProgramaSel != null && !idProgramaSel.equals(0)) {
			programaSel = programaNegocio.findById(idProgramaSel);
			mapeaNodoATreeNode(programaSel, raizNodoArbol);
			estructurasSectorial = (List<TreeNode>) raizNodoArbol.getChildren();
			logger.trace("Aventando las estructuras del programa "
					+ programaSel + " que son " + estructurasSectorial.size());

		} else {
			idEstructuraSeleccionada = (Integer) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.ID_ESTRUCTURA_SEL);
			programasOtros = (List<Programa>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.PROGRAMAS_OTROS);
			estructurasSectorial = new ArrayList<TreeNode>();
			if (idEstructuraSeleccionada != null) {
				estructuraSeleccionada = estructuraNegocio
						.findById(idEstructuraSeleccionada);

			}
			for (Programa programa : programasOtros) {
				nodoArbol = new TreeNode(programa.getId().toString(),
						programa.getNombre(), new ArrayList<TreeNode>());
				nodoArbol.setState(TreeNode.NODE_STATE_CLOSED);
				if (estructuraSeleccionada != null
						&& estructuraSeleccionada.getIdPrograma().equals(
								programa.getId())) {
					logger.trace("Al poco que debuto..");
					mapeaNodoATreeNode(programa, nodoArbol);
					logger.trace("Maradó..");
				}
				estructurasSectorial.add(nodoArbol);
			}
		}
		return "traerEstructuras";
	}

	public void mapeaNodoATreeNode(Nodo nodo, TreeNode nodoArbol) {
		TreeNode nodoArbolHijo;
		nodoArbol.setState(TreeNode.NODE_STATE_LEAF);
		if (nodo.getNodosHijo() != null) {
			for (Nodo nodoHijo : nodo.getNodosHijo()) {
				if (nodoHijo instanceof Estructura) {
					nodoArbolHijo = new TreeNode((nodoHijo.getClass().equals(
							Programa.class) ? "" : nodoHijo.getClass()
							.getSimpleName() + "_")
							+ nodoHijo.getId().toString(),
							nodoHijo.getNombre(),
							(Collection<TreeNode>) new ArrayList<TreeNode>());
					mapeaNodoATreeNode(nodoHijo, nodoArbolHijo);
					nodoArbol.getChildren().add(nodoArbolHijo);
					nodoArbol.setState(TreeNode.NODE_STATE_CLOSED);
				}
			}
		}
	}

	@RemoteMethod
	public Periodo verificarPeriodoRestrictivo(Integer idEstructuraX,
			Integer idEstructuraY) {
		Estructura estructuraX = null;
		Estructura estructuraY = null;

		Nodo nodo = null;
		List<Estructura> listaEstructuras = new ArrayList<Estructura>();
		Proyecto proyecto = null;

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		proyecto = (Proyecto) req.getSession().getAttribute(
				NombreObjetosSesion.PROYECTO);

		proyecto = proyectoNegocio.findById(proyecto.getIdProyecto());

		logger.trace("idX: " + idEstructuraX + "  idY: " + idEstructuraY);

		if (idEstructuraX != null) {
			estructuraX = estructuraNegocio.findById(idEstructuraX);
		}
		if (idEstructuraY != null) {
			estructuraY = estructuraNegocio.findById(idEstructuraY);
		}

		if (estructuraX != null) {
			listaEstructuras.add(estructuraX);
		}
		if (estructuraY != null) {
			listaEstructuras.add(estructuraY);
		}
		if (listaEstructuras.size() >= 1) {
			proyecto.setEstructuras(listaEstructuras);
		}

		logger.trace("Las estructuras " + proyecto.getEstructuras());
		logger.trace("estructuraX: " + estructuraX);
		logger.trace("estructuraY: " + estructuraY);
		logger.trace("ejecutando metodo........");
		nodo = periodoNegocio.getNodoPadreConPeriodoRestrictivo(proyecto);
		logger.trace("Nodo::::" + nodo);

		return nodo.getPeriodo();
	}

	public void validate() {
		List<EjeTematico> ejesTodos;
		ejesTodos = ejeTematicoNegocio.findAll();
		ejeTematicosDisponibles = new ArrayList<EjeTematico>();
		ejeTematicosSelecccionados = new ArrayList<EjeTematico>();
		logger.trace("pinche madre" + idEjesTematicosDisponibles);
		for (EjeTematico ejeTematico : ejesTodos) {
			if (idEjesTematicosDisponibles.contains(ejeTematico.getIdEje())) {
				ejeTematicosDisponibles.add(ejeTematico);
			} else {
				ejeTematicosSelecccionados.add(ejeTematico);
			}
		}

		List<TemaTransversal> temasTodos;
		temasTodos = temaTransversalNegocio.findAll();
		temaTransversalDisponibles = new ArrayList<TemaTransversal>();
		temaTransversalSeleccionados = new ArrayList<TemaTransversal>();
		logger.trace("no hay mañana" + idTemasTransversalesDisponibles);
		for (TemaTransversal temaTransversal : temasTodos) {
			if (idTemasTransversalesDisponibles.contains(temaTransversal
					.getIdTema())) {
				temaTransversalDisponibles.add(temaTransversal);
			} else {
				temaTransversalSeleccionados.add(temaTransversal);
			}
		}

	}

	public Usuario getUsuarioActual() {
		if (usuarioActual == null) {
			usuarioActual = (Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO);
		}
		return usuarioActual;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;

		if (idSel != null) {
			model = proyectoNegocio.findById(idSel);
		}
	}

	@Override
	public Proyecto getModel() {
		if (model == null) {
			model = new Proyecto();
		}
		return model;
	}

	public Integer getBanderaTipoPeriodo() {
		if (banderaTipoPeriodo == null && model != null
				&& model.getPeriodo() != null) {
			switch (model.getPeriodo().getTipoPeriodo()) {
			case Periodo.PERIODO_INDEFINIDO:
				banderaTipoPeriodo = CatalogoEstructuraController.BANDERA_INDEFINIDO;
				break;
			case Periodo.PERIODO_RELATIVO:
				banderaTipoPeriodo = CatalogoEstructuraController.BANDERA_DURACION;
				break;
			case Periodo.PERIODO_DEFINIDO:
				if (model.getPeriodo().getFechaFin() == null) {
					banderaTipoPeriodo = CatalogoEstructuraController.BANDERA_DURACION;
				} else {
					banderaTipoPeriodo = CatalogoEstructuraController.BANDERA_FECHA;
				}
				break;
			default:
				break;
			}
		}
		return banderaTipoPeriodo;
	}

	public String getMail() {
		if (mail == null) {
			usuarioActual = (Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO);
			List<Contacto> contactos;

			contactos = usuarioActual.getContactos();
			// saco los contactos
			for (Contacto contacto : contactos) {
				if (contacto.getPrincipal() == 1
						&& contacto.getIdTipoContacto() == TipoContacto.CORREO) {
					mail = contacto.getContacto();
				}
			}
		}
		return mail;
	}

	public String getTel() {
		if (tel == null) {
			usuarioActual = (Usuario) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO);
			List<Contacto> contactos;

			contactos = usuarioActual.getContactos();

			for (Contacto contacto : contactos) {
				// saco los contactos
				if (contacto.getPrincipal() == 1
						&& contacto.getIdTipoContacto() == TipoContacto.TELEFONO) {
					tel = contacto.getContacto();
				}
			}
		}
		return tel;
	}

	public List<String> getCaminosMigajas() {
		logger.trace("Calculando camino de migajas de " + model);
		if (caminosMigajas == null) {
			caminosMigajas = proyectoNegocio
					.generarCaminoMigajasProyecto(model);
			logger.trace("El camino de migajas " + caminosMigajas);
		}
		return caminosMigajas;
	}

	public void setBanderaTipoPeriodo(Integer banderaTipoPeriodo) {
		this.banderaTipoPeriodo = banderaTipoPeriodo;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	public EjeTematicoNegocio getEjeTematicoNegocio() {
		return ejeTematicoNegocio;
	}

	public void setEjeTematicoNegocio(EjeTematicoNegocio ejeTematicoNegocio) {
		this.ejeTematicoNegocio = ejeTematicoNegocio;
	}

	public TemaTransversalNegocio getTemaTransversalNegocio() {
		return temaTransversalNegocio;
	}

	public void setTemaTransversalNegocio(
			TemaTransversalNegocio temaTransversalNegocio) {
		this.temaTransversalNegocio = temaTransversalNegocio;
	}

	public List<TemaTransversal> getTemasDisponibles() {
		return temasDisponibles;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public void setEjesDisponibles(List<EjeTematico> ejesDisponibles) {
		this.ejesDisponibles = ejesDisponibles;
	}

	public void setTemasDisponibles(List<TemaTransversal> temasDisponibles) {
		this.temasDisponibles = temasDisponibles;
	}

	public Programa getProgramaSectorial() {
		return programaSectorial;
	}

	public void setProgramaSectorial(Programa programaSectorial) {
		this.programaSectorial = programaSectorial;
	}

	public List<Programa> getOtrosProgramas() {
		return otrosProgramas;
	}

	public void setOtrosProgramas(List<Programa> otrosProgramas) {
		this.otrosProgramas = otrosProgramas;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public List<TreeNode> getEstructurasSectorial() {
		return estructurasSectorial;
	}

	public void setEstructurasSectorial(List<TreeNode> estructurasSectorial) {
		this.estructurasSectorial = estructurasSectorial;
	}

	public void setId(Integer id) {
		this.idProgramaSel = id;
	}

	public Integer getIdProgramaSel() {
		return idProgramaSel;
	}

	public void setIdProgramaSel(Integer idProgramaSel) {
		this.idProgramaSel = idProgramaSel;
	}

	public Integer getIdEstructuraSectorialSel() {
		return idEstructuraSectorialSel;
	}

	public void setIdEstructuraSectorialSel(Integer idEstructuraSectorialSel) {
		this.idEstructuraSectorialSel = idEstructuraSectorialSel;
	}

	public Integer getIdEstructuraSel() {
		return idEstructuraSel;
	}

	public void setIdEstructuraSel(Integer idEstructuraSel) {
		this.idEstructuraSel = idEstructuraSel;
	}

	public Boolean getAlineacion() {
		return alineacion;
	}

	public void setAlineacion(Boolean alineacion) {
		this.alineacion = alineacion;
	}

	public Boolean getAlineacionSectorial() {
		return alineacionSectorial;
	}

	public void setAlineacionSectorial(Boolean alineacionSectorial) {
		this.alineacionSectorial = alineacionSectorial;
	}

	public List<EjeTematico> getEjesProyecto() {
		return ejesProyecto;
	}

	public void setEjesProyecto(List<EjeTematico> ejesProyecto) {
		this.ejesProyecto = ejesProyecto;
	}

	public List<TemaTransversal> getTemasProyecto() {
		return temasProyecto;
	}

	public void setTemasProyecto(List<TemaTransversal> temasProyecto) {
		this.temasProyecto = temasProyecto;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<EjeTematico> getEjeTematicosDisponibles() {
		return ejeTematicosDisponibles;
	}

	public void setEjeTematicosDisponibles(
			List<EjeTematico> ejeTematicosDisponibles) {
		this.ejeTematicosDisponibles = ejeTematicosDisponibles;
	}

	public List<EjeTematico> getEjeTematicosSelecccionados() {
		return ejeTematicosSelecccionados;
	}

	public void setEjeTematicosSelecccionados(
			List<EjeTematico> ejeTematicosSelecccionados) {
		this.ejeTematicosSelecccionados = ejeTematicosSelecccionados;
	}

	public List<TemaTransversal> getTemaTransversalDisponibles() {
		return temaTransversalDisponibles;
	}

	public void setTemaTransversalDisponibles(
			List<TemaTransversal> temaTransversalDisponibles) {
		this.temaTransversalDisponibles = temaTransversalDisponibles;
	}

	public List<TemaTransversal> getTemaTransversalSeleccionados() {
		return temaTransversalSeleccionados;
	}

	public void setTemaTransversalSeleccionados(
			List<TemaTransversal> temaTransversalSeleccionados) {
		this.temaTransversalSeleccionados = temaTransversalSeleccionados;
	}

	public List<Integer> getIdEjesTematicosDisponibles() {
		return idEjesTematicosDisponibles;
	}

	public void setIdEjesTematicosDisponibles(
			List<Integer> idEjesTematicosDisponibles) {
		this.idEjesTematicosDisponibles = idEjesTematicosDisponibles;
	}

	public List<Integer> getIdEjesTematicosSeleccionados() {
		return idEjesTematicosSeleccionados;
	}

	public void setIdEjesTematicosSeleccionados(
			List<Integer> idEjesTematicosSeleccionados) {
		this.idEjesTematicosSeleccionados = idEjesTematicosSeleccionados;
	}

	public List<Integer> getIdTemasTransversalesDisponibles() {
		return idTemasTransversalesDisponibles;
	}

	public void setIdTemasTransversalesDisponibles(
			List<Integer> idTemasTransversalesDisponibles) {
		this.idTemasTransversalesDisponibles = idTemasTransversalesDisponibles;
	}

	public List<Integer> getIdTemasTransversalesSeleccionados() {
		return idTemasTransversalesSeleccionados;
	}

	public void setIdTemasTransversalesSeleccionados(
			List<Integer> idTemasTransversalesSeleccionados) {
		this.idTemasTransversalesSeleccionados = idTemasTransversalesSeleccionados;
	}

	public EstructuraNegocio getEstructuraNegocio() {
		return estructuraNegocio;
	}

	public void setEstructuraNegocio(EstructuraNegocio estructuraNegocio) {
		this.estructuraNegocio = estructuraNegocio;
	}

	public List<EjeTematico> getEjesDisponibles() {
		return ejesDisponibles;
	}

	public List<Proyecto> getListProyectos() {
		return listProyectos;
	}

	public void setListProyectos(List<Proyecto> listProyectos) {
		this.listProyectos = listProyectos;
	}

	public Integer getIdEstructuraUnoAux() {
		return idEstructuraUnoAux;
	}

	public void setIdEstructuraUnoAux(Integer idEstructuraUnoAux) {
		this.idEstructuraUnoAux = idEstructuraUnoAux;
	}

	public Integer getIdEstructuraDosAux() {
		return idEstructuraDosAux;
	}

	public void setIdEstructuraDosAux(Integer idEstructuraDosAux) {
		this.idEstructuraDosAux = idEstructuraDosAux;
	}

	public List<Estructura> getEstructurasAux() {
		return estructurasAux;
	}

	public void setEstructurasAux(List<Estructura> estructurasAux) {
		this.estructurasAux = estructurasAux;
	}

	public Integer getIdEstructuraSelTipo() {
		return idEstructuraSelTipo;
	}

	public void setIdEstructuraSelTipo(Integer idEstructuraSelTipo) {
		this.idEstructuraSelTipo = idEstructuraSelTipo;
	}
	
	public String otrasEstructuras(){
		return "otrasEstructuras";
	}

	public Integer getBanderaAux() {
		return banderaAux;
	}

	public void setBanderaAux(Integer banderaAux) {
		this.banderaAux = banderaAux;
	}

	public Boolean getPeriNull() {
		return periNull;
	}

	public void setPeriNull(Boolean periNull) {
		this.periNull = periNull;
	}

	public Periodo getPeriodoAux() {
		return periodoAux;
	}

	public void setPeriodoAux(Periodo periodoAux) {
		this.periodoAux = periodoAux;
	}

	
	
}