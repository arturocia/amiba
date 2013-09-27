package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Nivel;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.negocio.NivelNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProgramaNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Named
@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName", "catalogo-nivel" }),
		@Result(name = "input", location = "catalogo-nivel/index-deleteConfirm.jsp") })
public class CatalogoNivelController extends ActionSupport implements
		ModelDriven<Nivel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8886324527210230359L;
	private Logger logger = Logger.getLogger(CatalogoNivelController.class);

	private int pos;
	/**
	 * Referencia a los Objetos de negocios con los que colabora. Estas
	 * referencias se injectan desde el constructor.
	 */
	private NivelNegocio nivelNegocio;
	private ProgramaNegocio programaNegocio;
	private String nombrep;
	/**
	 * Referencia el Tipo de Aviso con el que se está trabajando, para el caso
	 * de nuevo, editar y eliminar.
	 */
	private Nivel model = null;

	/**
	 * Se refiere al Identificador seleccionado, este tiene valor cuando se
	 * efectua una accion sobre el index.jsp
	 */
	private Integer idSel = null;

	/**
	 * Contiene todos los Tipos de Avisos que se encontraron en la BD.
	 */
	private List<Nivel> list = null;

	private Programa programaSel;
	private Integer idProgramaSel;

	@SkipValidation
	public HttpHeaders index() {
		programaSel = (Programa) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROGRAMA);
		if (programaSel != null) {
			this.programaSel = programaNegocio.findById(programaSel
					.getIdPrograma());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.PROGRAMA, programaSel);
			list = programaSel.getNiveles();
		}

		if (idProgramaSel != null) {
			this.programaSel = programaNegocio.findById(idProgramaSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.PROGRAMA, programaSel);
			list = programaSel.getNiveles();
		}

		return new DefaultHttpHeaders("index").disableCaching();

	}

	@SkipValidation
	public String editNew() {
		pos = 0;
		list = nivelNegocio.findAll();
		programaSel = (Programa) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROGRAMA);
		if (programaSel != null) {
			this.programaSel = programaNegocio.findById(programaSel
					.getIdPrograma());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.PROGRAMA, programaSel);
			list = programaSel.getNiveles();
			programaSel = (Programa) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.PROGRAMA);
			model.setIdPrograma(programaSel.getIdPrograma());
		}

		if (idProgramaSel != null) {
			this.programaSel = programaNegocio.findById(idProgramaSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.PROGRAMA, programaSel);
			list = programaSel.getNiveles();
			nombrep = programaSel.getNombre();
		}
		if (list != null && list.size() > 0) {
			pos = list.get(list.size() - 1).getPosicion() + 1;
		} else {
			pos = 1;
		}
		logger.trace("A little less conversation " + programaSel.getNombre());
		model.setPosicion(pos);

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.POSICION_NIVEL, model.getPosicion());
		return "editNew";
	}

	public void validateCreate() {
		model.setIdPrograma(((Programa) (ActionContext.getContext()
				.getSession().get(NombreObjetosSesion.PROGRAMA)))
				.getIdPrograma());
		logger.trace("El nivel que se esta validando " + model);
		if (nivelNegocio.existe(model)) {
			addActionError(getText("campoRepetidoNivel"));
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "Datos incompletos"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre") }, regexFields = {
			@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü]|\\s){0,249})", key = "nombreError.max250"),
			@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü]([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü.,_/#]|\\s|\\-){0,499})", key = "descripcionError.max500"),
			@RegexFieldValidator(fieldName = "model.posicion", type = ValidatorType.SIMPLE, expression = "([0-9]{0,10})", key = "posicionError") },

	intRangeFields = { @IntRangeFieldValidator(fieldName = "model.posicion", type = ValidatorType.SIMPLE, key = "introPosicion") },

	requiredFields = { @RequiredFieldValidator(fieldName = "model.posicion", type = ValidatorType.SIMPLE, key = "introPosicion") })
	public HttpHeaders create() {
		programaSel = (Programa) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.PROGRAMA);
		this.programaSel = programaNegocio
				.findById(programaSel.getIdPrograma());

		model.setIdPrograma(this.programaSel.getIdPrograma());

		model = nivelNegocio.save(model);
		addActionMessage(getText("nivelAgre"));

		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdNivel());
	}

	@SkipValidation
	public String edit() {
		return "edit";
	}

	public void validateUpdate() {
		if (nivelNegocio.existe(model)) {
			addActionError(getText("campoRepetidoNivel"));
		}
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre") }, regexFields = {
			@RegexFieldValidator(fieldName = "model.nombre", type = ValidatorType.SIMPLE, expression = "([A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü]|\\s){0,249})", key = "nombreError.max250"),
			@RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü]([A-Za-z0-9ÑñÁÉÍÓÚáéíóúÜü.,_/#]|\\s|\\-){0,499})", key = "descripcionError.max500") })
	public String update() {
		nivelNegocio.update(model);
		addActionMessage(getText("nivelEdit"));
		return "success";
	}

	@SkipValidation
	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public void validateDestroy() {
		if (nivelNegocio.estaAsociado(model)) {
			addActionError(getText("eliminarRegistro"));
		}
		if (!nivelNegocio.esMayor(model)) {
			addActionError(getText("eliminarNIVEL"));
		}
	}

	public String destroy() {
		nivelNegocio.delete(model);
		addActionMessage(getText("nivelElim"));
		return "success";
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = nivelNegocio.findById(idSel);
		}
	}

	public Nivel getModel() {
		if (model == null) {
			model = new Nivel();
			model.setPosicion((Integer) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.POSICION_NIVEL));
		}

		return model;
	}

	public List<Nivel> getList() {
		return list;
	}

	public void setList(List<Nivel> list) {
		this.list = list;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Nivel model) {
		this.model = model;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public Programa getProgramaSel() {
		if (programaSel == null) {
			programaSel = (Programa) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.PROGRAMA);
		}
		return programaSel;
	}

	public void setProgramaSel(Programa programa) {
		this.programaSel = programa;
	}

	public NivelNegocio getNivelNegocio() {
		return nivelNegocio;
	}

	public void setNivelNegocio(NivelNegocio nivelNegocio) {
		this.nivelNegocio = nivelNegocio;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getNombrep() {
		return nombrep;
	}

	public void setNombrep(String nombrep) {
		this.nombrep = nombrep;
	}

	public Integer getIdProgramaSel() {
		return idProgramaSel;
	}

	public void setIdProgramaSel(Integer idProgramaSel) {
		this.idProgramaSel = idProgramaSel;
	}

}
