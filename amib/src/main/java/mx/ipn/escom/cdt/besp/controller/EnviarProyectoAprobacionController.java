package mx.ipn.escom.cdt.besp.controller;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Estado;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "operacion-editar-datos-proyecto/%{idSel}" }) })
public class EnviarProyectoAprobacionController extends ActionSupport implements
		ModelDriven<Proyecto> {

	private static final long serialVersionUID = 4101737855290744397L;
	private ProyectoNegocio proyectoNegocio;
	private Proyecto model;
	private Integer idSel;

	public String edit() {
		return "edit";
	}
	
	@SkipValidation
	public HttpHeaders index() {
		return new DefaultHttpHeaders("index").disableCaching();
	}


	public void validateUpdate() {
		if (!proyectoNegocio.validaEnvioAprobacion(model)) {
			addActionError(getText("errorValidaProyectoAprobacion"));
		}
	}

	public String update() {
		model.setIdEstado(Estado.REVISION);
		proyectoNegocio.update(model);
		return "success";
	}

	public Proyecto getModel() {
		if (model == null) {
			model = new Proyecto();
		}

		return model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = proyectoNegocio.findById(idSel);
		}
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
}
