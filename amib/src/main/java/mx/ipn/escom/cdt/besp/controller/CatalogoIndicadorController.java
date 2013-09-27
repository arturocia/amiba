package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.*;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.util.*;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.*;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName",
				"catalogo-indicador?idAccionSel=%{model.idAccion}&idSel2=%{idPlan}" }),
		@Result(name = "cancelar", type = "redirectAction", params = {
				"actionName",
				"catalogo-indicador?idAccionSel=%{accionIdentificador}&idSel2=%{idPlan}" }),
		@Result(name = "combos", type = "json", params = { "includeProperties",
				"listUnidad.*" }) })
public class CatalogoIndicadorController extends ActionSupport implements
		ModelDriven<Indicador> {
	/**
	 * 
	 */

	private static final long serialVersionUID = -2658183684105775113L;
	private UnidadNegocio unidadNegocio;
	private TipoUnidadNegocio tipoUnidadNegocio;
	private IndicadorNegocio indicadorNegocio;
	private AccionNegocio accionNegocio;

	private Logger logger = Logger.getLogger(CatalogoIndicadorController.class);

	private List<Indicador> list = null;
	private List<Accion> listAccion = null;
	private List<Unidad> listUnidad = null;
	private List<TipoUnidad> listTipoUnidad = null;
	private Integer tipoUnidadSel;

	private Indicador model = null;
	private Accion accion = null;
	private Integer idAccionSel;
	private Integer idSel = null;
	private Integer idSel2;
	private Integer accionIdentificador;
	private Integer idPlan;

	@SkipValidation
	public HttpHeaders index() {

		listTipoUnidad = tipoUnidadNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPOSUNIDAD, listTipoUnidad);

		if (accion != null) {
			this.accion = accionNegocio.findById(this.accion.getIdAccion());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCION, accion);
			list = accion.getIndicadores();
		}

		if (idAccionSel != null) {
			this.accion = accionNegocio.findById(idAccionSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCION, accion);
			list = accion.getIndicadores();
			logger.trace("esta es una mierda" + list);
			
		}

		// FIXME esto es solo para pruebas
		if (accion == null && idAccionSel == null) {
			list = indicadorNegocio.findAll();
		}
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SkipValidation
	public String editNew() {
		listTipoUnidad = tipoUnidadNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPOSUNIDAD, listTipoUnidad);
		
		accion = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);
		accionIdentificador=accion.getIdAccion();
		return "editNew";
	}

	public void validateCreate() {
		Accion accEjemplo;
		accEjemplo = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);

		model.setAccion(accEjemplo);

		// //////////////////////si la accion tine uno o mas indicadores el
		// siguiente indicador no se puede agregar
		if (accEjemplo.getIndicadores().size() >= 1) {
			addActionError(getText("indicaddorMeta"));
		}
		if (model.getPeso() != null) {

			if (!indicadorNegocio.validaIndicador(model)) {
				addActionError(getText("errorValidaIndicador"));
			}
			if (!indicadorNegocio.validaPesoIndicador(model)) {
				addActionError(getText("errorValidaPesoIndicador"));
			}
			if (!indicadorNegocio.validaPesoMayorACero(model)) {
				addActionError(getText("errorValidaPesoMayorACero"));
			}
		}
		if(model.getMeta()==null){
			addActionError(getText("introMeta"));
		}
		if(model.getPeso()==null){
			addActionError(getText("introPeso"));
		}
		if(tipoUnidadSel!=null&&tipoUnidadSel.equals(-1)){
			addActionError(getText("tipoIndicador"));
		}
		
	}

	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") }, 
			regexFields = { @RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,250}", key = "descripcionError.max500") }, 
			requiredFields = {
			//@RequiredFieldValidator(fieldName = "tipoUnidadSel", type = ValidatorType.FIELD, key = "tipoUnidadError"),
			//@RequiredFieldValidator(fieldName = "model.peso", type = ValidatorType.FIELD, key = "introPeso"),
			//@RequiredFieldValidator(fieldName = "model.meta", type = ValidatorType.FIELD, key = "introMeta"),
			@RequiredFieldValidator(fieldName = "model.idUnidad", type = ValidatorType.FIELD, key = "unidadSelError") 
			}, intRangeFields = {
			@IntRangeFieldValidator(fieldName = "model.meta", type = ValidatorType.SIMPLE, min = "1", key = "errorTamaMeta"),
			@IntRangeFieldValidator(fieldName = "model.idUnidad", type = ValidatorType.SIMPLE, min = "1", key = "unidadSelError") })
	public HttpHeaders create() {

		accion = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);

		// FIXME esta mierda es de prueba

		if (accion == null) {
			accion = accionNegocio.findById(1);
		}
		model.setIdProyecto(((Proyecto) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROYECTO)).getIdProyecto());
		model.setIdAccion(accion.getId());
		model.setIdProyecto(accion.getIdProyecto());
		model = indicadorNegocio.save(model);
		
		addActionMessage(getText("indFisAgre"));
		return new DefaultHttpHeaders("success");
	}

	@SkipValidation
	public String edit() {

		listTipoUnidad = tipoUnidadNegocio.findAll();
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TIPOSUNIDAD, listTipoUnidad);
		tipoUnidadSel=unidadNegocio.findById(model.getIdUnidad()).getIdTipoUnidad();
		return "edit";
	}

	public void validateUpdate() {
		Accion accEjemplo;
		accEjemplo = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);

		model.setAccion(accEjemplo);

		logger.trace("entrando a  validadeUopdate");
		if (model.getPeso() != null) {
			if (!indicadorNegocio.validaIndicador(model)) {
				addActionError(getText("errorValidaIndicador"));
			}
			if (!indicadorNegocio.validaPesoIndicador(model)) {
				addActionError(getText("errorValidaPesoIndicador"));
			}
			if (!indicadorNegocio.validaMetaAvance(model)) {
				addActionError(getText("errorAvanceViejo"));
			}
		}
		if(model.getMeta()==null){
			addActionError(getText("introMeta"));
		}
		if(model.getPeso()==null){
			addActionError(getText("introPeso"));
		}
		if(tipoUnidadSel!=null&&tipoUnidadSel.equals(-1)){
			addActionError(getText("tipoIndicador"));
		}
		if(model.getIdUnidad()!=null && model.getIdUnidad().equals(-1)){
			addActionError(getText("unidadSelError"));
		}
		
	}

	@Validations(requiredStrings = {

	@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip") },

	regexFields = {

	@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,250}", key = "descripcionError.max500") },

	requiredFields = {
			@RequiredFieldValidator(fieldName = "tipoUnidadSel", type = ValidatorType.FIELD, key = "tipoUnidadError"),
			@RequiredFieldValidator(fieldName = "model.peso", type = ValidatorType.FIELD, key = "introPeso"),
			@RequiredFieldValidator(fieldName = "model.meta", type = ValidatorType.FIELD, key = "introMeta"),
			@RequiredFieldValidator(fieldName = "model.idUnidad", type = ValidatorType.FIELD, key = "unidadSelError") }, intRangeFields = {

	@IntRangeFieldValidator(fieldName = "model.meta", type = ValidatorType.SIMPLE, min = "1", key = "errorTamaMeta") })
	public String update() {
		logger.trace("entrando al metodo update");
		indicadorNegocio.update(model);
		addActionMessage(getText("indFisEdit"));
		return "success";
	}

	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		if (model.getAvance() != null) {
			addActionError("El indicador no se puede eliminar porque se han reportado avances");
		}
		logger.trace("entrando a validateDestroy");
	}

	public String destroy() {
		logger.trace("entrando a destroy");
		indicadorNegocio.delete(model);
		addActionMessage(getText("indFisElim"));
		return "success";
	}

	@SkipValidation
	public String traerUnidades() {

		logger.trace("webos gaspar no sirve");
		Unidad test;
		test = new Unidad();
		if (tipoUnidadSel != null && tipoUnidadSel != -1) {
			test.setIdTipoUnidad(tipoUnidadSel);
			listUnidad = unidadNegocio.findByExample(test);
		}
		return "combos";
	}
	
	public String cancelar() {
		String result = "cancelar";
		return result;
	}

	@Override
	public Indicador getModel() {
		if (model == null) {
			model = new Indicador();
		}
		return model;
	}

	@SuppressWarnings("unchecked")
	public List<TipoUnidad> getListTipoUnidad() {
		if (listTipoUnidad == null) {
			listTipoUnidad = (List<TipoUnidad>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.TIPOSUNIDAD);
		}
		return listTipoUnidad;
	}

	public void setIdAccionSel(Integer idAccionSel) {
		this.idAccionSel = idAccionSel;
		if (idAccionSel != null) {
			accion = accionNegocio.findById(idAccionSel);
		}
	}

	public void setIdSel(Integer idSel) {

		if (idSel != null) {
			model = indicadorNegocio.findById(idSel);
		}
		this.idSel = idSel;
	}

	public UnidadNegocio getUnidadNegocio() {
		return unidadNegocio;
	}

	public void setUnidadNegocio(UnidadNegocio unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}

	public List<Unidad> getListUnidad() {
		return listUnidad;
	}

	public void setListUnidad(List<Unidad> listUnidad) {
		this.listUnidad = listUnidad;
	}

	public TipoUnidadNegocio getTipoUnidadNegocio() {
		return tipoUnidadNegocio;
	}

	public void setTipoUnidadNegocio(TipoUnidadNegocio tipoUnidadNegocio) {
		this.tipoUnidadNegocio = tipoUnidadNegocio;
	}

	public void setListTipoUnidad(List<TipoUnidad> listTipoUnidad) {
		this.listTipoUnidad = listTipoUnidad;
	}

	public void setModel(Indicador model) {
		this.model = model;
	}

	public AccionNegocio getAccionNegocio() {
		return accionNegocio;
	}

	public void setAccionNegocio(AccionNegocio accionNegocio) {
		this.accionNegocio = accionNegocio;
	}

	public List<Accion> getListAccion() {
		return listAccion;
	}

	public void setListAccion(List<Accion> listAccion) {
		this.listAccion = listAccion;
	}

	public IndicadorNegocio getIndicadorNegocio() {
		return indicadorNegocio;
	}

	public void setIndicadorNegocio(IndicadorNegocio indicadorNegocio) {
		this.indicadorNegocio = indicadorNegocio;
	}

	public List<Indicador> getList() {
		return list;
	}

	public void setList(List<Indicador> list) {
		this.list = list;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Integer getIdAccionSel() {
		return idAccionSel;
	}

	public Integer getTipoUnidadSel() {
		return tipoUnidadSel;
	}

	public void setTipoUnidadSel(Integer tipoUnidadSel) {
		this.tipoUnidadSel = tipoUnidadSel;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public Integer getAccionIdentificador() {
		return accionIdentificador;
	}

	public void setAccionIdentificador(Integer accionIdentificador) {
		this.accionIdentificador = accionIdentificador;
	}

	public Integer getIdPlan() {
		return idPlan;
	}

	public void setIdPlan(Integer idPlan) {
		this.idPlan = idPlan;
	}

	public Integer getIdSel2() {
		return idSel2;
	}

	public void setIdSel2(Integer idSel2) {
		this.idSel2 = idSel2;
	}


	
}