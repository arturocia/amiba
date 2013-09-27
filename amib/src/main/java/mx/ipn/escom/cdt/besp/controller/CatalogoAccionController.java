package mx.ipn.escom.cdt.besp.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.PeriodoNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
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
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "operacion-planeacion/%{idProyecto}" }),
		@Result(name = "input", location = "catalogo-accion/index-deleteConfirm.jsp") })
public class CatalogoAccionController extends ActionSupport implements
		ModelDriven<Accion> {

	private static final long serialVersionUID = -784454592546747595L;
	private Logger logger = Logger.getLogger(CatalogoAccionController.class);
	private static final int BANDERA_DURACION = 1;
	private static final int BANDERA_FECHA = 2;
	private static final int BANDERA_INDEFINIDO = 3;
	private Integer idProyectoSel;
	private AccionNegocio accionNegocio;
	private PeriodoNegocio periodoNegocio;
	private ProyectoNegocio proyectoNegocio;
	private Accion model = null;
	private Integer idSel;
	private Nodo nodoRestrictivo;
	private List<Accion> list = null;

	private Integer banderaTipoPeriodo;

	@SkipValidation
	public String editNew() {
		model = null;
		model = new Accion();
		model.setNodoPadre(proyectoNegocio.findById(idSel));
		nodoRestrictivo = periodoNegocio
				.getNodoPadreConPeriodoRestrictivo(model);
		logger.trace("idSel:: " + idSel);

		if (nodoRestrictivo == null) {
			logger.trace("Nodorestric es null");
		}
		if (idSel != null) {
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.PROYECTO,
							proyectoNegocio.findById(idSel));
		}

		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.NODO_CON_PERIODO_RESTRICTIVO,
						nodoRestrictivo);

		return "editNew";
	}

	public void validateCreate() {
		idSel = model.getIdProyecto();
		logger.trace(model.getNombre());
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError("El periodo no esta bien formado");
		}

		if (banderaTipoPeriodo != null) {
			switch (banderaTipoPeriodo) {
			case BANDERA_DURACION:
				if (model.getPeriodo().getDuracion() == null) {
					addActionError("La duración debe ser introducida");
				} else {
					if (model.getPeriodo().getDuracion() < 0)
					{
						addActionError("La duración no debe ser negativa");
					}
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser introducida");
				}
				break;
			case BANDERA_FECHA:
				if (model.getPeriodo().getFechaFin() == null) {
					addActionError("La fecha final debe ser introducida");
				}
				if (model.getPeriodo().getFechaInicio() == null) {
					addActionError("La fecha inicial debe ser introducida");
				}

				if (model.getPeriodo().getFechaInicio() != null
						&& model.getPeriodo().getFechaFin() != null
						&& (model.getPeriodo().getFechaInicio().getTime() > model
								.getPeriodo().getFechaFin().getTime())) {
					addActionError("La fecha inicial debe ser menor a la fecha final");
				}
				Nodo nodoAuxiliar = (Nodo) ActionContext.getContext().getSession().get(NombreObjetosSesion.NODO_CON_PERIODO_RESTRICTIVO);
				String patronFecha = "dd/MM/yyyy";
				SimpleDateFormat formato = new SimpleDateFormat(patronFecha);
				
				if(model.getPeriodo().getFechaInicio()!= null && (model.getPeriodo().getFechaInicio().before(nodoAuxiliar.getPeriodo().getFechaInicio()))){
					addActionError("La fecha inicial debe estar despues de la fecha "+formato.format(nodoAuxiliar.getPeriodo().getFechaInicio()));
				}
				if(model.getPeriodo().getFechaFin()!=null && (model.getPeriodo().getFechaFin().after(nodoAuxiliar.getPeriodo().getFechaFin()))){
					addActionError("La fecha final debe estar antes de la fecha "+formato.format(nodoAuxiliar.getPeriodo().getFechaFin()));
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

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.objetivo", type = ValidatorType.FIELD, key = "introObjetivoAccion"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introAccDescr") })
	public HttpHeaders create() {
		Periodo periodo;

		periodo = periodoNegocio.save(model.getPeriodo());
		model.setIdPeriodo(periodo.getIdPeriodo());
		logger.trace("Proyecto: " + model.getIdProyecto());
		model.setIdProyecto(((Proyecto) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROYECTO)).getIdProyecto());
		model = accionNegocio.save(model);
		addActionMessage("Se agregó " + model.getNombre() + " exitosamente");
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdAccion());
	}

	@SkipValidation
	public String edit() {
		if (model.getPeriodo().getDuracion() != null) {
			banderaTipoPeriodo = BANDERA_DURACION;
		} else if ((model.getPeriodo().getFechaInicio() != null)
				&& (model.getPeriodo().getFechaFin() != null)) {
			banderaTipoPeriodo = BANDERA_FECHA;
		} else {
			banderaTipoPeriodo = BANDERA_INDEFINIDO;
		}
		nodoRestrictivo = periodoNegocio
				.getNodoPadreConPeriodoRestrictivo(model);
		ActionContext
				.getContext()
				.getSession()
				.put(NombreObjetosSesion.NODO_CON_PERIODO_RESTRICTIVO,
						nodoRestrictivo);

		return "edit";
	}

	public void validateUpdate() {
		/**
		 * valida si esta bien formado el periodo
		 */
		logger.trace("Validando programa " + model);
		if (!periodoNegocio.validaBienFormado(model.getPeriodo())) {
			addActionError("El periodo no esta bien formado");
		}

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
			model.getPeriodo().setFechaFin(null);
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
			model.getPeriodo().setDuracion(null);
			Nodo nodoAuxiliar = (Nodo) ActionContext.getContext().getSession().get(NombreObjetosSesion.NODO_CON_PERIODO_RESTRICTIVO);
			String patronFecha = "dd/MM/yyyy";
			SimpleDateFormat formato = new SimpleDateFormat(patronFecha);
			
			if(model.getPeriodo().getFechaInicio()!= null && (model.getPeriodo().getFechaInicio().before(nodoAuxiliar.getPeriodo().getFechaInicio()))){
				addActionError("La fecha inicial debe estar despues de la fecha "+formato.format(nodoAuxiliar.getPeriodo().getFechaInicio()));
			}
			if(model.getPeriodo().getFechaFin()!=null && (model.getPeriodo().getFechaFin().after(nodoAuxiliar.getPeriodo().getFechaFin()))){
				addActionError("La fecha final debe estar antes de la fecha "+formato.format(nodoAuxiliar.getPeriodo().getFechaFin()));
			}
			break;
		case BANDERA_INDEFINIDO:
			model.getPeriodo().setDuracion(null);
			model.getPeriodo().setFechaFin(null);
			model.getPeriodo().setFechaInicio(null);
			break;
		default:
			break;
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.objetivo", type = ValidatorType.FIELD, key = "introObjetivoAccion"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introTelDescr") })
	public String update() {

		periodoNegocio.save(model.getPeriodo());
		accionNegocio.update(model);
		addActionMessage("Se modifico  " + model.getNombre() + " exitosamente");
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
	}

	public String destroy() {
		Periodo periodo = model.getPeriodo();
		accionNegocio.delete(model);
		periodoNegocio.delete(periodo);
		addActionMessage("Se elimino  " + model.getNombre() + " exitosamente");
		return "success";
	}

	@SkipValidation
	public HttpHeaders index() {

		list = accionNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = accionNegocio.findById(idSel);
		}
	}

	public Accion getModel() {
		if (model == null) {
			model = new Accion();
		}
		return model;
	}

	public void setModel(Accion model) {
		this.model = model;
	}

	public List<Accion> getList() {
		return list;
	}

	public void setList(List<Accion> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public AccionNegocio getAccionNegocio() {
		return accionNegocio;
	}

	public void setAccionNegocio(AccionNegocio accionNegocio) {
		this.accionNegocio = accionNegocio;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}

	public Integer getBanderaTipoPeriodo() {
		return banderaTipoPeriodo;
	}

	public void setBanderaTipoPeriodo(Integer banderaTipoPeriodo) {
		this.banderaTipoPeriodo = banderaTipoPeriodo;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Nodo getNodoRestrictivo() {
		return nodoRestrictivo;
	}

	public void setNodoRestrictivo(Nodo nodoRestrictivo) {
		this.nodoRestrictivo = nodoRestrictivo;
	}

	public Integer getIdProyectoSel() {
		return idProyectoSel;
	}

	public void setIdProyectoSel(Integer idProyectoSel) {
		this.idProyectoSel = idProyectoSel;
	}
}