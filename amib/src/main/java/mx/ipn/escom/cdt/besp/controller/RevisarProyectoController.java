package mx.ipn.escom.cdt.besp.controller;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RevisarProyectoController extends ActionSupport implements
ModelDriven<Proyecto> {
	private Logger logger = Logger.getLogger(RevisarProgramaController.class);
	private Proyecto model;
	private ProyectoNegocio proyectoNegocio;
	private Integer idSel;
	
	@SkipValidation
	public String show() {
		logger.trace("dentro del show con idSel = " + idSel);
		return "show";
	}

	@Override
		public Proyecto getModel() {
			return model;
		}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		logger.trace("idSel::::" + idSel);
		if (idSel != null) {
			model = proyectoNegocio.findById(idSel);	
		}
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}
}
