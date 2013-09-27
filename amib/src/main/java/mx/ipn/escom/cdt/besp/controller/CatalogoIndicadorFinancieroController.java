package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.IndicadorFinancieroNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "operacion-planeacion/%{model.idProyecto}" }) })
public class CatalogoIndicadorFinancieroController extends ActionSupport
		implements ModelDriven<IndicadorFinanciero> {

	private static final long serialVersionUID = 4037594653876655883L;

	private Logger logger = Logger
			.getLogger(CatalogoIndicadorFinancieroController.class);
	private IndicadorFinancieroNegocio indicadorFinancieroNegocio;
	private Proyecto proyecto;
	private ProyectoNegocio proyectoNegocio;
	private IndicadorFinanciero model = null;
	private Integer idSel = null;
	private Integer idProyectoSel = null;
	private String optAccion;

	private List<IndicadorFinanciero> list = null;

	@SkipValidation
	public HttpHeaders index() {
		logger.trace("¡¡¡¡¡¡¡¡¡¡¡¡¡---------  EN Index");
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		logger.trace("---------  proyecto" + proyecto.getNombre());
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		return "editNew";
	}

	public void validateCreate() {
		logger.trace("en validate create");
		if (model.getFechaSolicitado() == null) {
			logger.trace("-*-*-*-*-*-Fecha " + model.getFechaSolicitado());
			addActionError("Favor de introducir la Fecha de Solicitud");
		}
		if (model.getMontoSolicitado() == null) {
			logger.trace("-*-*-*-*-*-MONto " + model.getMontoSolicitado());
			addActionError("Favor de introducir el Monto Solicitado");
		} else {
			if (!indicadorFinancieroNegocio.validaMayorAcero(model)) {
				logger.trace("Es menor a cero");
				addActionError("El monto solicitado debe ser mayor a cero");
			}
		}
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.nombreFuente", type = ValidatorType.FIELD, key = "introFuente") }, regexFields = { @RegexFieldValidator(fieldName = "model.nombreFuente", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){1,50}", key = "nombreError.max50") })
	public HttpHeaders create() {
		// TENER CUIDADO CUANDO INYECTA LOS DATOS AL ID DEL PROYECTO
		model = indicadorFinancieroNegocio.save(model);
		addActionMessage("El presupuesto se agregó exitosamente");
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdIndicadorFinanciero());
	}

	@SkipValidation
	public String view() {

		return "view";
	}

	@SkipValidation
	public String edit() {
		logger.trace("El model es: " + model.getNombreFuente());
		return "edit";
	}

	public void validateUpdate() {
		if (model.getFechaSolicitado() == null) {
			logger.trace("-*-*-*-*-*-Fecha " + model.getFechaSolicitado());
			addActionError("Favor de introducir la Fecha de Soliciud");
		}
		if (model.getMontoSolicitado() == null) {
			logger.trace("-*-*-*-*-*-MONto " + model.getMontoSolicitado());
			addActionError("Favor de introducir el Monto Solicitado");
		} else if (!indicadorFinancieroNegocio.validaMayorAcero(model)) {
			logger.trace("Es menor a cero");
			addActionError("El monto solicitado debe ser mayor a cero");
		}
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.nombreFuente", type = ValidatorType.FIELD, key = "introFuente") }, regexFields = { @RegexFieldValidator(fieldName = "model.nombreFuente", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){1,50}", key = "nombreError.max50") })
	public String update() {

		indicadorFinancieroNegocio.save(model);
		addActionMessage("El presupuesto se modificó exitosamente");
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		logger.trace("-*-*-*-*-*- Destroy Fecha "
				+ indicadorFinancieroNegocio.validaEstaAprobado(model));
		if (indicadorFinancieroNegocio.validaEstaAprobado(model)) {

			addActionError("No se puede eliminar un presupuesto que este aprobado");
			logger.trace("-*-*-*-*-*- en el iff despues del accion error");
		}

	}

	public String destroy() {

		addActionMessage("El presupuesto se eliminó exitosamente");
		indicadorFinancieroNegocio.delete(model);
		return "success";
	}

	@SkipValidation
	public String show() {
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		return "show";
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = indicadorFinancieroNegocio.findById(idSel);
			logger.trace("En setIdsel, el model es: " + model
					+ " y el nombre es: " + model.getNombreFuente());
		}
	}

	public IndicadorFinanciero getModel() {
		if (model == null) {
			model = new IndicadorFinanciero();
		}

		return model;
	}

	public List<IndicadorFinanciero> getList() {
		return list;
	}

	public void setList(List<IndicadorFinanciero> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(IndicadorFinanciero model) {
		this.model = model;
	}

	public Integer getIdProyectoSel() {
		return idProyectoSel;
	}

	public void setIdProyectoSel(Integer idProyectoSel) {
		this.idProyectoSel = idProyectoSel;
	}

	public IndicadorFinancieroNegocio getIndicadorFinancieroNegocio() {
		return indicadorFinancieroNegocio;
	}

	public void setIndicadorFinancieroNegocio(
			IndicadorFinancieroNegocio indicadorFinancieroNegocio) {
		this.indicadorFinancieroNegocio = indicadorFinancieroNegocio;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public String getOptAccion() {
		return optAccion;
	}

	public void setOptAccion(String optAccion) {
		this.optAccion = optAccion;
	}
}
