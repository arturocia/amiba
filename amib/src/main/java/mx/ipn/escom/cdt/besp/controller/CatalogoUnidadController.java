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
import com.opensymphony.xwork2.validator.annotations.*;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-unidad" }),
		@Result(name = "input", location = "catalogo-unidad/index-deleteConfirm.jsp") })
public class CatalogoUnidadController extends ActionSupport implements
		ModelDriven<Unidad> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6123442199160871581L;

	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas referencias se injectan desde el constructor.
	 */
	private UnidadNegocio unidadNegocio;
	private TipoUnidadNegocio tipoUnidadNegocio;

	/**
	 * Referencia el Tipo de unidad con el que se est trabajando, para el caso de nuevo, editar y eliminar.
	 */
	private Unidad model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	/**
	 * Contiene todos los Tipos de Unidades que se encontraron el la BD.
	 */
	private List<Unidad> list = null;

	/**
	 * 
	 */
	private List<TipoUnidad> listTiposUnidad = null;
	
	@Inject
	public CatalogoUnidadController(UnidadNegocio unidadNegocio, TipoUnidadNegocio tipoUnidadNegocio) { 
		this.unidadNegocio = unidadNegocio;
		this.tipoUnidadNegocio = tipoUnidadNegocio;
	}

	@SkipValidation
	public String editNew() {
		listTiposUnidad = tipoUnidadNegocio.findAll();
		return "editNew";
	}

	public void validateCreate() {
		if (unidadNegocio.existe(model.getNombre())) {
			addActionError(getText("unidadRepetida"));
		}
	}
	
	@Validations(
		requiredStrings = {

			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, message = "Introduzca una Descripción"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, message = "Introduzca un Nombre") 

		},
		intRangeFields = { 
			@IntRangeFieldValidator(type = ValidatorType.FIELD, fieldName = "model.idTipoUnidad", min = "0", message = "Debe seleccionar un Tipo.")
		}
	)
	public HttpHeaders create() {
		model = unidadNegocio.save(model);
		addActionMessage(getText("unidadAgre"));
		return new DefaultHttpHeaders("success").setLocationId(
				model.getIdUnidad()
		);
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	@Validations(
		requiredStrings = { 

			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, message = "Introduzca una Descripción") 

		},
		intRangeFields = { 
			@IntRangeFieldValidator(type = ValidatorType.FIELD, fieldName = "model.idTipoUnidad", min = "0", message = "Debe Seleccionar un Tipo de Unidad.")
		}
	)
	public String update() {
		unidadNegocio.save(model);
		addActionMessage(getText("unidadEdit"));
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() { 
		return "deleteConfirm";
	}

	public void validateDestroy() {
		if (unidadNegocio.estaAsociado(model)) {
			addActionError("Un Indicador esta asociado a esta unidad");
		}
	}
	
	public String destroy() {
		unidadNegocio.delete(model);
		addActionMessage(getText("unidadElim"));
		return "success";
	}

	@SkipValidation
	public HttpHeaders index() { 
		list = unidadNegocio.findAll();
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = unidadNegocio.findById(idSel);
		}
	}

	public Unidad getModel() { 
		if (model == null) {
			model = new Unidad();
		}

		return model;
	}

	public List<Unidad> getList() {
		return list;
	}

	public void setList(List<Unidad> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Unidad model) {
		this.model = model;
	}

	public TipoUnidadNegocio getTipoUnidadNegocio() {
		return tipoUnidadNegocio;
	}

	public void setTipoUnidadNegocio(TipoUnidadNegocio tipoUnidadNegocio) {
		this.tipoUnidadNegocio = tipoUnidadNegocio;
	}

	public List<TipoUnidad> getListTiposUnidad() {
		if( listTiposUnidad == null)
		{
			listTiposUnidad = tipoUnidadNegocio.findAll();
		}
		return listTiposUnidad;
	}

	public void setListTiposUnidad(List<TipoUnidad> listTiposUnidad) {
		this.listTiposUnidad = listTiposUnidad;
	}

	public UnidadNegocio getUnidadNegocio() {
		return unidadNegocio;
	}

	public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}	
}