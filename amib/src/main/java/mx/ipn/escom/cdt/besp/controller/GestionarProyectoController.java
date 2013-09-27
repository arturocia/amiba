package mx.ipn.escom.cdt.besp.controller;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "gestionar-proyecto-preregistrado" }) })
public class GestionarProyectoController extends ActionSupport implements ModelDriven<Proyecto> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7884457654505831199L;


	/**
	 * 
	 */
	private EstructuraNegocio estructuraNegocio;
	
	private Proyecto model;
	
	public GestionarProyectoController() {
		// TODO Auto-generated constructor stub
	}
	
	@SkipValidation
	public HttpHeaders index() {
		return null;
	}

	@SkipValidation
	public String editNew() {
		return "editNew";
	}

	public void validateCreate() {
		
	}

	public HttpHeaders create() {
		return null;
	}

	public EstructuraNegocio getEstructuraNegocio() {
		return estructuraNegocio;
	}

	public void setEstructuraNegocio(EstructuraNegocio estructuraNegocio) {
		this.estructuraNegocio = estructuraNegocio;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	public void validateUpdate() {
		if(!estructuraNegocio.validaPeriodoHaciaAbajo(model)){
			addActionError(getText("errorValidaProyectoHaciaAbajo"));
		}
	}

	//@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "resumen", type = ValidatorType.FIELD, key = "introRes") })
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

	@Override
	public Proyecto getModel() {
		return model;
	}	
}
