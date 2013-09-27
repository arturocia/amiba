package mx.ipn.escom.cdt.besp.controller;

import java.util.Date;
import java.util.List;

import javax.inject.*;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;

import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.interceptor.validation.SkipValidation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.*;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "operacion-revisar-avances/%{model.idProyecto}" }) })
public class AprobarPresupuestoController extends ActionSupport
		implements ModelDriven<IndicadorFinanciero> {

	private static final long serialVersionUID = -784454592546747595L;
	
	private Integer idProyecto;
	private IndicadorFinancieroNegocio indicadorFinancieroNegocio;
	private IndicadorFinanciero model = null;
	private Integer idSel;
	private List<IndicadorFinanciero> list = null;

	@SkipValidation
	public String edit() {
		model.setFechaAprobado(new Date());
		return "edit";
	}

	public void validateUpdate() {
		if (model.getMontoAprobado() != null
				&& !indicadorFinancieroNegocio.validaAprobacionMayorAcero(model)) {
			addActionError("El monto por aprobar debe ser una cifra mayor a 0");
		}

	}

	@Validations(requiredFields = {
			@RequiredFieldValidator(fieldName = "model.fechaAprobado", key = "fechaAprob", type = ValidatorType.SIMPLE),
			@RequiredFieldValidator(fieldName = "model.montoAprobado", key = "montoAprob", type = ValidatorType.SIMPLE)})
	public String update() {
		indicadorFinancieroNegocio.update(model);
		return "success";
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = indicadorFinancieroNegocio.findById(idSel);
		}
	}

	public IndicadorFinanciero getModel() {
		if (model == null) {
			model = new IndicadorFinanciero();
		}
		return model;
	}

	public void setModel(IndicadorFinanciero model) {
		this.model = model;
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

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public IndicadorFinancieroNegocio getIndicadorFinancieroNegocio() {
		return indicadorFinancieroNegocio;
	}

	public void setIndicadorFinancieroNegocio(
			IndicadorFinancieroNegocio indicadorFinancieroNegocio) {
		this.indicadorFinancieroNegocio = indicadorFinancieroNegocio;
	}

}
