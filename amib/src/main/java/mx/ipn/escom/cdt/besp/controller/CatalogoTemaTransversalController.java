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
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;
import mx.ipn.escom.cdt.besp.negocio.TemaTransversalNegocio;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-tema-transversal" }),
		@Result(name = "input", location = "catalogo-tema-transversal/index-deleteConfirm.jsp") })
public class CatalogoTemaTransversalController extends ActionSupport implements
		ModelDriven<TemaTransversal> {
	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -4093777144840116931L;

	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas referencias se injectan desde el constructor.
	 */
	private TemaTransversalNegocio temaTransversalNegocio;

	/**
	 * Referencia el Tipo de Aviso con el que se está trabajando, para el caso de nuevo, editar y eliminar.
	 */
	private TemaTransversal model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	/**
	 * Contiene todos los Tipos de Avisos que se encontraron en la BD.
	 */
	private List<TemaTransversal> list = null;

	@Inject
	public CatalogoTemaTransversalController(TemaTransversalNegocio temaTransversalNegocio) {
		this.temaTransversalNegocio = temaTransversalNegocio;
	}

	@SkipValidation
	public String editNew() {
		return "editNew";
	}

	
	public void validateCreate() {
		if (temaTransversalNegocio.existe(model.getNombre())) {
			addActionError(getText("campoRepetidoTemaT"));
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "nombre", type = ValidatorType.FIELD, key = "Datos incompletos"),
			@RequiredStringValidator(fieldName = "descripcion", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "nombre", type = ValidatorType.FIELD, key = "introNombre") },
			regexFields = {
			@RegexFieldValidator(fieldName = "nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü]|\\s){0,249})", key = "nombreError.max250"),
			@RegexFieldValidator(fieldName = "descripcion", type = ValidatorType.SIMPLE, expression = "([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü]([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü.,_/#]|\\s|\\-){0,499})", key = "descripcionError.max500") 
			})
	public HttpHeaders create() {
		model = temaTransversalNegocio.save(model);
		addActionMessage(getText("temaTAgre"));
		return new DefaultHttpHeaders("success").setLocationId(
				model.getIdTema()
		);
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	@Validations(requiredStrings = { 
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") 
			},
			regexFields = {
			@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü]([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü.,_/#]|\\s|\\-){0,249})", key = "descripcionError.max250")	
	})
	public String update() {
		addActionMessage(getText("temaTEdit"));
		temaTransversalNegocio.update(model);
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}
	
	public void validateDestroy() {				
		if (temaTransversalNegocio.estaAsociado(model)) {			
			addActionError(getText("eliminarRegistro"));
		}				
	}

	//@SkipValidation
	public String destroy() {
		temaTransversalNegocio.delete(model);
		addActionMessage(getText("temaTElim"));
		return "success";
	}

	@SkipValidation
	public HttpHeaders index() {
		list = temaTransversalNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = temaTransversalNegocio.findById(idSel);
		}
	}

	public TemaTransversal getModel() {
		if (model == null) {
			model = new TemaTransversal();
		}

		return model;
	}


	public List<TemaTransversal> getList() {
		return list;
	}

	public void setList(List<TemaTransversal> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(TemaTransversal model) {
		this.model = model;
	}

}
