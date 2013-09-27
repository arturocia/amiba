package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.AvanceFinanciero;
import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;
import mx.ipn.escom.cdt.besp.negocio.AvanceFinancieroNegocio;
import mx.ipn.escom.cdt.besp.negocio.IndicadorFinancieroNegocio;
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

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "operacion-revisar-avances/%{model.idProyecto}" }) })
public class CatalogoAvanceFinancieroController extends ActionSupport implements
		ModelDriven<AvanceFinanciero> {

	private static final long serialVersionUID = 6123442125490871581L;

	private Logger logger = Logger
			.getLogger(CatalogoAvanceFinancieroController.class);

	private AvanceFinanciero model;
	private AvanceFinancieroNegocio avanceFinancieroNegocio;
	private Integer idIndicadorFinancieroSel;
	private List<AvanceFinanciero> list = null;
	private IndicadorFinanciero indicadorFinanciero = null;
	private IndicadorFinancieroNegocio indicadorFinancieroNegocio;
	private Integer idProyectoSel;
	private Integer idSel;

	@SkipValidation
	public String editNew() {
		logger.trace("en ediNew");
		getIdIndicadorFinancieroSel();
	
		logger.trace("EditNew::::indicadorF:" + indicadorFinanciero);
		if (indicadorFinanciero != null) {
			this.indicadorFinanciero = indicadorFinancieroNegocio
					.findById(this.indicadorFinanciero
							.getIdIndicadorFinanciero());
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.INDICADORFINANCIEROSEL,
							indicadorFinanciero);
			logger.trace("indicadorFinancier NO nullo");
		} else {
			logger.trace("indicadorFinancier Nullo");
		}

		if (idIndicadorFinancieroSel != null) {
			this.indicadorFinanciero = indicadorFinancieroNegocio
					.findById(idIndicadorFinancieroSel);
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.INDICADORFINANCIEROSEL,
							indicadorFinanciero);
			logger.trace("IDindicadorFinancier NO nullo "
					+ this.indicadorFinanciero.getIdProyecto());
			idProyectoSel = this.indicadorFinanciero.getIdProyecto();
			ActionContext.getContext().getSession().put("idProyectoSel",idProyectoSel);
		} else {
			logger.trace("IDindicadorFinancier Nullo");
		}

		return "editNew";
	}
//FIXME validacion de fecha con la fecha del sistema
	public void validateCreate() {
		if(idIndicadorFinancieroSel!=null)
		{
			ActionContext.getContext().getSession().put("idIndicadorFinancieroSel",idIndicadorFinancieroSel);
		}
		indicadorFinanciero = (IndicadorFinanciero) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.INDICADORFINANCIEROSEL);
		logger.trace("****indicador ****"
				+ indicadorFinanciero.getIdIndicadorFinanciero());
		model.setIndicadorFinanciero(indicadorFinanciero);

		if (model.getFechaAvance() == null) {
			logger.trace("-*-*-*-*-*-Fecha " + model.getFechaAvance());
			addActionError("Favor de introducir la Fecha de Solicitud");
		}
		if (model.getMonto() == null) {
			logger.trace("-*-*-*-*-*-MONto " + model.getMonto());
			addActionError("Favor de introducir el Monto Solicitado");
		} else {
			if (!avanceFinancieroNegocio.validaMontoMayorAcero(model)) {
				logger.trace("Es menor a cero");
				addActionError("El monto debe ser mayor a cero");
			} else if (!avanceFinancieroNegocio
					.validaSumaMontoMenorAprobado(model)) {
				addActionError("La suma de los ejercicios debe ser menor al monto aprobado");
			}
		}
	}

	public HttpHeaders create() {
		indicadorFinanciero = (IndicadorFinanciero) ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.INDICADORFINANCIEROSEL);

		logger.trace("En create");

		model.setIdProyecto(indicadorFinanciero.getIdProyecto());
		model.setIdIndicadorFinanciero(indicadorFinanciero
				.getIdIndicadorFinanciero());

		logger.trace("IDindicaodorF:" + model.getIdIndicadorFinanciero()
				+ "idProy: " + model.getIdProyecto());
		model = avanceFinancieroNegocio.save(model);

		addActionMessage("El ejercicio se agregó exitosamente");
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdIndicadorFinanciero());
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	public String update() {
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {

	}

	public String destroy() {
		return "success";
	}

	@SkipValidation
	public HttpHeaders index() {
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@Override
	public AvanceFinanciero getModel() {
		if (model == null) {
			model = new AvanceFinanciero();
		}
		return model;
	}

	public AvanceFinancieroNegocio getAvanceFinancieroNegocio() {
		return avanceFinancieroNegocio;
	}

	public void setAvanceFinancieroNegocio(
			AvanceFinancieroNegocio avanceFinancieroNegocio) {
		this.avanceFinancieroNegocio = avanceFinancieroNegocio;
	}

	public Integer getIdIndicadorFinancieroSel() {
		if(idIndicadorFinancieroSel==null)
		{
			idIndicadorFinancieroSel= (Integer) ActionContext.getContext().getSession().get(NombreObjetosSesion.INDICADORFINANCIEROSEL);
		}
		return idIndicadorFinancieroSel;
	}

	public void setIdIndicadorFinancieroSel(Integer idIndicadorFinancieroSel) {
		this.idIndicadorFinancieroSel = idIndicadorFinancieroSel;
	}

	public List<AvanceFinanciero> getList() {
		return list;
	}

	public void setList(List<AvanceFinanciero> list) {
		this.list = list;
	}

	public IndicadorFinanciero getIndicadorFinanciero() {
		return indicadorFinanciero;
	}

	public void setIndicadorFinanciero(IndicadorFinanciero indicadorFinanciero) {
		this.indicadorFinanciero = indicadorFinanciero;
	}

	public IndicadorFinancieroNegocio getIndicadorFinancieroNegocio() {
		return indicadorFinancieroNegocio;
	}

	public void setIndicadorFinancieroNegocio(
			IndicadorFinancieroNegocio indicadorFinancieroNegocio) {
		this.indicadorFinancieroNegocio = indicadorFinancieroNegocio;
	}

	public void setModel(AvanceFinanciero model) {
		this.model = model;
	}

	public Integer getIdProyectoSel() {
		if(idProyectoSel==null)
		{
			idProyectoSel= (Integer) ActionContext.getContext().getSession().get("idProyectoSel");
		}
		return idProyectoSel;
	}

	public void setIdProyectoSel(Integer idProyectoSel) {
		this.idProyectoSel = idProyectoSel;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Integer getIdSel() {
		if(idSel==null)
		{
			idSel= (Integer) ActionContext.getContext().getSession().get(NombreObjetosSesion.INDICADORFINANCIEROSEL);
		}
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}	
}