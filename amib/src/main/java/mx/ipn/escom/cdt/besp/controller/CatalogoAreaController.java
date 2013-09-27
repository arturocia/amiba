package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Area;
import mx.ipn.escom.cdt.besp.negocio.AreaNegocio;

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
		"actionName", "catalogo-area" }) })
public class CatalogoAreaController extends ActionSupport implements
		ModelDriven<Area> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 558107555367329498L;


	private Logger logger = Logger.getLogger(CatalogoAreaController.class);


	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */
	private AreaNegocio areaNegocio;

	/**
	 * Referencia el área con el que se está trabajando, para el caso de nuevo,
	 * editar y eliminar. Paso 7 para Agregar Areas
	 * 
	 */
	private Area model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se
	 * efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	private List<Area> list = null;

	/**
	 * Struts llama este método (Paso 6 Agregar área) Devuelve "editNew" (Paso 8
	 * Agregar área)
	 */
	@SkipValidation
	public String editNew() {
		return "editNew";
	}

	public void validateCreate() {

		if (areaNegocio.existe(model.getNombre())) {
			addActionError(getText("areaRepetida"));
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "datosIncompletos"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.siglas", type = ValidatorType.FIELD, key = "introSiglas"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") }, regexFields = {
			//@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-)", key = "nombreError.max50") })
			@RegexFieldValidator(fieldName = "model.siglas", type = ValidatorType.SIMPLE, expression = "([A-Z]){1,10}", key = "siglasError.max10") })
			//@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-)", key = "descripcionError.max250") })
	public HttpHeaders create() {
		model = areaNegocio.save(model);
		addActionMessage(getText("areaAgre"));
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdArea());
	}

	@SkipValidation
	public String edit() {
		System.out.println(" entro en el puto edit------------------");
		return "edit";
	}

	//@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") }, regexFields = { @RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-)", key = "descripcionError.max250") })
	public String update() {
		areaNegocio.update(model);
		addActionMessage(getText("areaEdit"));
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		logger.trace("Invalidate Destroy");
		logger.trace("El resultado de la validacion es"
				+ areaNegocio.estaAsociado(model));
		
	}

	public String destroy() {
		if (areaNegocio.estaAsociado(model)) {
			addActionMessage(getText("eliminarRegistro"));
		}
		else{
		addActionMessage(getText("areaElim"));
		areaNegocio.delete(model);
		}
		return "success";
	}

	/*
	 * (GestiónarArea 7) (GestionarArea 13)
	 */
	@SkipValidation
	public HttpHeaders index() {
		list = areaNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = areaNegocio.findById(idSel);
		}
	}

	public Area getModel() {
		if (model == null) {
			model = new Area();
		}

		return model;
	}

	public List<Area> getList() {
		return list;
	}

	public void setList(List<Area> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Area model) {
		this.model = model;
	}

	public AreaNegocio getAreaNegocio() {
		return areaNegocio;
	}

	public void setAreaNegocio(AreaNegocio areaNegocio) {
		this.areaNegocio = areaNegocio;
	}

}
