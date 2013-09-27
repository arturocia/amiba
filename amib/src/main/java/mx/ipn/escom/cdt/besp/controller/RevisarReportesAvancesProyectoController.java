package mx.ipn.escom.cdt.besp.controller;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named

public class RevisarReportesAvancesProyectoController extends ActionSupport
		implements ModelDriven<Accion> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4130017112852164626L;

	private Integer idSel;
	private Accion model;
	private AccionNegocio accionNegocio;
	
	@SkipValidation
	public HttpHeaders index() {
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String show() {
		return "show";
	}

	@Override
	public Accion getModel() {
		return model;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = accionNegocio.findById(idSel);
		}
	}

	public AccionNegocio getAccionNegocio() {
		return accionNegocio;
	}

	public void setAccionNegocio(AccionNegocio accionNegocio) {
		this.accionNegocio = accionNegocio;
	}

	public void setModel(Accion model) {
		this.model = model;
	}
}
