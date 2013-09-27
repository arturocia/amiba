package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;

import mx.ipn.escom.cdt.besp.modelo.EjeTematico;
import mx.ipn.escom.cdt.besp.negocio.EjeTematicoNegocio;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-eje-tematico" }),
		@Result(name = "input", location = "catalogo-eje-tematico/index-deleteConfirm.jsp") })

public class CatalogoEjeTematicoController extends ActionSupport implements ModelDriven<EjeTematico>{
	
	private static final long serialVersionUID = 4073586637181295062L;

	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas referencias se injectan desde el constructor.
	 */
	private EjeTematicoNegocio ejeTematicoNegocio;

	/**
	 * Referencia el área con el que se está trabajando, para el caso de nuevo, editar y eliminar.
	 * Paso 7 para Agregar Areas
	 * 
	 */
	private EjeTematico model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	
	private List<EjeTematico> list = null;

	@Inject
	public CatalogoEjeTematicoController(EjeTematicoNegocio ejeTematicoNegocio) {
		this.ejeTematicoNegocio = ejeTematicoNegocio;
	}
	
	/**
	 *Struts llama este método (Paso 6 Agregar área)
	 *Devuelve "editNew" (Paso 8 Agregar área) 
	 */
	@SkipValidation
	public String editNew() {
		return "editNew";
	}
		
	public void validateCreate() {
		if (ejeTematicoNegocio.existe(model.getNombre())) {
			addActionError(getText("campoRepetidoEjeT"));
		}
	}
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Datos incompletos"), 
					@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"), 
					@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip")},
			regexFields = 
			{ 
					@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü]|\\s){1,250}", key = "nombreError.max250"),
					@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü.#,¿?0-9]([A-Za-zÑñÁÉÍÓÚáéíóúÜü.#,¿?0-9]|\\s|\\-){1,500}", key = "descripcionError.max500")
			}
	)
	public HttpHeaders create() {
		model = ejeTematicoNegocio.save(model);
		addActionMessage(getText("ejeTAgre"));
		return new DefaultHttpHeaders("success").setLocationId(
				model.getIdEje()
		);
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") },
					 regexFields = { @RegexFieldValidator(fieldName = "descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü.#,¿?0-9]([A-Za-zÑñÁÉÍÓÚáéíóúÜü.#,¿?0-9]|\\s|\\-){1,500}", key = "descripcionError.max500")}) 
	public String update() {
		addActionMessage(getText("ejeTEdit"));
		ejeTematicoNegocio.update(model);
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		if (ejeTematicoNegocio.estaAsociado(model)) {
			addActionError(getText("eliminarRegistro"));
		}
	}

	public String destroy() {
		ejeTematicoNegocio.delete(model);
		addActionMessage(getText("ejeTElim"));
		return "success";
	}

	
	
	/*(GestiónarArea 7)
	 (GestionarArea 13) */
	@SkipValidation
	public HttpHeaders index() {
		list = ejeTematicoNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = ejeTematicoNegocio.findById(idSel);
		}
	}

	public EjeTematico getModel() {
		if (model == null) {
			model = new EjeTematico();
		}

		return model;
	}


	public List<EjeTematico> getList() {
		return list;
	}

	public void setList(List<EjeTematico> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(EjeTematico model) {
		this.model = model;
	}


}
