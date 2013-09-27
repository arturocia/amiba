package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.TipoContacto;
import mx.ipn.escom.cdt.besp.negocio.TipoContactoNegocio;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-tipo-contacto" }),
		@Result(name = "input", location = "catalogo-tipo-contacto/index-deleteConfirm.jsp") })
public class CatalogoTipoContactoController extends ActionSupport implements
		ModelDriven<TipoContacto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5752426633751741832L;

	private Logger logger = Logger.getLogger(CatalogoTipoContactoController.class);

	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */
	private TipoContactoNegocio tipoContactoNegocio;

	/**
	 * Referencia el Tipo de unidad con el que se est� trabajando, para el caso
	 * de nuevo, editar y eliminar.
	 */
	private TipoContacto model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se
	 * efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	/**
	 * Contiene todos los Tipos de Unidades que se encontraron el la BD.
	 */
	private List<TipoContacto> list = null;

	@SkipValidation
	public String editNew() {
		return "editNew";
	}

	public void validateCreate() {
		if (!tipoContactoNegocio.existe(model)) {
			addActionError(getText("tipoContactoRepetido"));
		}
	}

	
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "descripcion", type = ValidatorType.FIELD, key = "datosIncompletos"),
			@RequiredStringValidator(fieldName = "nombre", type = ValidatorType.FIELD, key = "introNombre") })
	//@RequiredStringValidator(fieldName = "descripcion", type = ValidatorType.FIELD, key = "introDescrip")
	public HttpHeaders create() {
		logger.trace("la descripcion es " + model.getDescripcion());
		model = tipoContactoNegocio.save(model);
		addActionMessage(getText("tipoContactoAgre"));
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdTipoContacto());
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "datosIncompletos") })
	public String update() {
		logger.trace("la descripcion es " + model.getDescripcion());
		tipoContactoNegocio.update(model);
		addActionMessage(getText("tipoContactoEdit"));
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		if (!model.getContactos().isEmpty()) {
			addActionError(getText("elimTipoContacto"));
		}

	}

	public String destroy() {
		addActionMessage(getText("tipoContactoElim"));
		tipoContactoNegocio.delete(model);
		return "success";
	}

	@SkipValidation
	public HttpHeaders index() {
		list = tipoContactoNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = tipoContactoNegocio.findById(idSel);
		}
	}

	public TipoContacto getModel() {
		if (model == null) {
			model = new TipoContacto();
		}

		return model;
	}

	public List<TipoContacto> getList() {
		return list;
	}

	public void setList(List<TipoContacto> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(TipoContacto model) {
		this.model = model;
	}

	public TipoContactoNegocio getTipoContactoNegocio() {
		return tipoContactoNegocio;
	}

	public void setTipoContactoNegocio(TipoContactoNegocio tipoContactoNegocio) {
		this.tipoContactoNegocio = tipoContactoNegocio;
	}

}
