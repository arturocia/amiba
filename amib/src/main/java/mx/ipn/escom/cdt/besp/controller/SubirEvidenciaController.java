package mx.ipn.escom.cdt.besp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Evidencia;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.EvidenciaNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

@Results({
		@Result(name = "success", type = "redirectAction", params = {
				"actionName",
				"revisar-reportes-avances-proyecto/%{#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@ACCIONSEL].idAccion}" }),
		@Result(name = "input", location = "subir-evidencia/index-editNew.jsp") })
public class SubirEvidenciaController extends ActionSupport implements
		ModelDriven<Evidencia> {

	/**
	 * http://localhost:8087/bespIcon/1?idAccionSel=&idSel=221&
	 * idEstructuraPadreSel
	 * =&%2Frevisar-reportes-avances-proyecto%2F=&task=Accion_1
	 * 
	 */
	private static final long serialVersionUID = -2690144399891436230L;

	private Logger logger = Logger.getLogger(SubirEvidenciaController.class);
	private EvidenciaNegocio evidenciaNegocio;
	private Evidencia model = null;
	private Integer idSel = null;
	private List<Evidencia> list = null;
	private Accion accion;
	private Proyecto proyecto;
	private AccionNegocio accionNegocio;
	private ProyectoNegocio proyectoNegocio;
	private Integer idAccionSel;
	private File file = null;
	private String filename;
	private String fileFileName;
	private String contentType;
	
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;

	@SkipValidation
	public HttpHeaders index() {
		list = evidenciaNegocio.findAll();
		accion = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);
		if (accion != null) {
			this.accion = accionNegocio.findById(this.accion.getIdAccion());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCIONSEL, accion);
			idAccionSel = accion.getIdAccion();
		}
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = evidenciaNegocio.findById(idSel);
		}
	}

	@SkipValidation
	public String editNew() {
		accion = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);
		if (accion != null) {
			this.accion = accionNegocio.findById(this.accion.getIdAccion());
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCIONSEL, accion);
			idAccionSel = accion.getIdAccion();
		} else {
			logger.trace("accion nula");
		}

		if (idAccionSel != null) {
			this.accion = accionNegocio.findById(idAccionSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCIONSEL, accion);

		} else {
			logger.trace("IDaccion Nullo");
		}
		return "editNew";
	}

	public void validateCreate() {
		accion = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCIONSEL);
		model.setAccion(accion);
	}

	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "model.nombre", type = ValidatorType.FIELD, key = "introNombre"),
			@RequiredStringValidator(fieldName = "model.descripcion", type = ValidatorType.FIELD, key = "introDescrip"),
			@RequiredStringValidator(fieldName = "model.claveDocumental", type = ValidatorType.FIELD, key = "claveDocumental") }, requiredFields = {
			@RequiredFieldValidator(fieldName = "model.fecha", key = "introFechaSolicitada", type = ValidatorType.SIMPLE),
			@RequiredFieldValidator(fieldName = "fileUpload", key = "archivoEvidencia", type = ValidatorType.SIMPLE), }, regexFields = { @RegexFieldValidator(fieldName = "model.descripcion", type = ValidatorType.SIMPLE, expression = "[A-Za-zÑñÁÉÍÓÚáéíóúÜü]([A-Za-zÑñÁÉÍÓÚáéíóúÜü0-9.,/#]|\\s|\\-){0,1000}", key = "descripcionError.max1000") })
	public HttpHeaders create() {
		accion = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCIONSEL);
		evidenciaNegocio.guardarEvidencia(model, accion, fileUpload, fileUploadFileName, getText("rutaArchivos"));
		return new DefaultHttpHeaders("success").setLocationId(model
				.getIdEvidencia());
	}

	public EvidenciaNegocio getEvidenciaNegocio() {
		return evidenciaNegocio;
	}

	public void setEvidenciaNegocio(EvidenciaNegocio evidenciaNegocio) {
		this.evidenciaNegocio = evidenciaNegocio;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setModel(Evidencia model) {
		this.model = model;
	}

	@Override
	public Evidencia getModel() {
		if (model == null) {
			model = new Evidencia();
		}
		return model;
	}

	public List<Evidencia> getList() {
		return list;
	}

	public void setList(List<Evidencia> list) {
		this.list = list;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public AccionNegocio getAccionNegocio() {
		return accionNegocio;
	}

	public void setAccionNegocio(AccionNegocio accionNegocio) {
		this.accionNegocio = accionNegocio;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Integer getIdAccionSel() {
		if (idAccionSel == null) {
			Accion aux;
			aux = (Accion) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.ACCIONSEL);
			idAccionSel = aux.getIdAccion();
		}
		return idAccionSel;
	}

	public void setIdAccionSel(Integer idAccionSel) {
		this.idAccionSel = idAccionSel;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setArchivoEvidencia(File file) {
		this.file = file;
	}

	public void setArchivoEvidenciaContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setArchivoEvidenciaFileName(String filename) {
		this.filename = filename;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
	
}