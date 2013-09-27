package mx.ipn.escom.cdt.besp.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import mx.ipn.escom.cdt.besp.modelo.Area;
import mx.ipn.escom.cdt.besp.modelo.EjeTematico;
import mx.ipn.escom.cdt.besp.modelo.Evidencia;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;
import mx.ipn.escom.cdt.besp.negocio.AreaNegocio;
import mx.ipn.escom.cdt.besp.negocio.EjeTematicoNegocio;
import mx.ipn.escom.cdt.besp.negocio.EvidenciaNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProgramaNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.negocio.TemaTransversalNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Results({
		@Result(name = "obtenerEvidencia", type = "stream", params = {
				"contentType", "${contentType}", "contentDisposition",
				"${attachment}" }),
		@Result(name = "error", location = "consulta-general/index.jsp") })
public class ConsultaGeneralController extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -787841484453442249L;
	private List<Area> listAreas;
	private List<TemaTransversal> listTemas;
	private List<EjeTematico> listEjes;
	private List<Proyecto> listProyectos;
	private List<Programa> listProgramas;
	private ProyectoNegocio proyectoNegocio;
	private AreaNegocio areaNegocio;
	private TemaTransversalNegocio temaTransversalNegocio;
	private EjeTematicoNegocio ejeTematicoNegocio;
	private ProgramaNegocio programaNegocio;
	private List<Integer> idProgramas;
	private List<Integer> idEjes;
	private List<Integer> idAreas;
	private List<Integer> idTemas;
	private EvidenciaNegocio evidenciaNegocio;
	private List<Evidencia> listEvidencias = null;
	private Logger logger = Logger.getLogger(ConsultaGeneralController.class);

	private Integer idEvidenciaSel;

	private String contentType;
	private String filename;
	private InputStream inputStream;
	private OutputStream outoutStream;
	private String attachment;
	private String nombreArchivo;
	private String evidenciaNombre;

	@SkipValidation
	public String buscar() {
		logger.trace("la lista de programas es" + idProgramas);
		listEvidencias = evidenciaNegocio.getEvidencias(idProgramas, idEjes,
				idTemas, idAreas);

		if (listEvidencias.isEmpty()) {
			addActionError(getText("registrosError"));
		}

		logger.trace("No evidencias" + listEvidencias.size());
		return "index";
	}

	@SkipValidation
	public String obtenerEvidencia() throws IOException {
		contentType = "application/octet-stream";
		Evidencia evidencia = evidenciaNegocio.findById(idEvidenciaSel);
		//se obtiene nombre del archivo
		String[] me = evidencia.getNombreArchivoFisico().split("/");
		String nombreA = me[me.length - 1];		
		attachment = "attachment;filename=" + nombreA;
		
		try {
			if (idEvidenciaSel != null) {

				String path = getText("rutaArchivos");

				inputStream = new FileInputStream(new File(path
						+ evidencia.getNombreArchivoFisico()));

			}
			return "obtenerEvidencia";
		} catch (FileNotFoundException fnfe) {

			nombreArchivo = nombreA;
			evidenciaNombre = evidencia.getNombre();

			String mensaje = getText("archivoNoEncontrado");
			addActionError(mensaje);		
		}
		return "error";
	}

	@SkipValidation
	public HttpHeaders index() {
		listProgramas = programaNegocio.findAll();
		listProyectos = proyectoNegocio.findAll();
		listEjes = ejeTematicoNegocio.findAll();
		listTemas = temaTransversalNegocio.findAll();
		listAreas = areaNegocio.findAll();

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROGRAMAS, listProgramas);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.PROYECTOS, listProyectos);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.EJES, listEjes);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.TEMAS, listTemas);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.AREAS, listAreas);
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@SuppressWarnings("unchecked")
	// por el cast de string a lista de areas
	public List<Area> getListAreas() {
		if (listAreas == null) {
			listAreas = (List<Area>) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.AREAS);
		}
		return listAreas;
	}

	@SuppressWarnings("unchecked")
	// por el cast de string a lista de temas
	public List<TemaTransversal> getListTemas() {
		if (listTemas == null) {
			listTemas = (List<TemaTransversal>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.TEMAS);
		}
		return listTemas;
	}

	@SuppressWarnings("unchecked")
	// por el cast de string a lista de ejes
	public List<EjeTematico> getListEjes() {
		if (listEjes == null) {
			listEjes = (List<EjeTematico>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.EJES);
		}
		return listEjes;
	}

	@SuppressWarnings("unchecked")
	// por el cast de string a lista proyectos
	public List<Proyecto> getListProyectos() {
		if (listProyectos == null) {
			listProyectos = (List<Proyecto>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.PROYECTOS);
		}
		return listProyectos;
	}

	@SuppressWarnings("unchecked")
	// por el cast de string a lista programas
	public List<Programa> getListProgramas() {
		if (listProgramas == null) {
			listProgramas = (List<Programa>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.PROGRAMAS);
		}
		return listProgramas;
	}

	public List<Integer> getIdProgramas() {
		return idProgramas;
	}

	public void setIdProgramas(List<Integer> idProgramas) {
		this.idProgramas = idProgramas;
	}

	public void setListAreas(List<Area> listAreas) {
		this.listAreas = listAreas;
	}

	public AreaNegocio getAreaNegocio() {
		return areaNegocio;
	}

	public void setAreaNegocio(AreaNegocio areaNegocio) {
		this.areaNegocio = areaNegocio;
	}

	public void setListTemas(List<TemaTransversal> listTemas) {
		this.listTemas = listTemas;
	}

	public void setListEjes(List<EjeTematico> listEjes) {
		this.listEjes = listEjes;
	}

	public TemaTransversalNegocio getTemaTransversalNegocio() {
		return temaTransversalNegocio;
	}

	public void setTemaTransversalNegocio(
			TemaTransversalNegocio temaTransversalNegocio) {
		this.temaTransversalNegocio = temaTransversalNegocio;
	}

	public EjeTematicoNegocio getEjeTematicoNegocio() {
		return ejeTematicoNegocio;
	}

	public void setEjeTematicoNegocio(EjeTematicoNegocio ejeTematicoNegocio) {
		this.ejeTematicoNegocio = ejeTematicoNegocio;
	}

	public void setListProyectos(List<Proyecto> listProyectos) {
		this.listProyectos = listProyectos;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public void setListProgramas(List<Programa> listProgramas) {
		this.listProgramas = listProgramas;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public List<Integer> getIdEjes() {
		return idEjes;
	}

	public void setIdEjes(List<Integer> idEjes) {
		this.idEjes = idEjes;
	}

	public List<Integer> getIdAreas() {
		return idAreas;
	}

	public void setIdAreas(List<Integer> idAreas) {
		this.idAreas = idAreas;
	}

	public List<Integer> getIdTemas() {
		return idTemas;
	}

	public void setIdTemas(List<Integer> idTemas) {
		this.idTemas = idTemas;
	}

	public EvidenciaNegocio getEvidenciaNegocio() {
		return evidenciaNegocio;
	}

	public void setEvidenciaNegocio(EvidenciaNegocio evidenciaNegocio) {
		this.evidenciaNegocio = evidenciaNegocio;
	}

	public List<Evidencia> getListEvidencias() {
		return listEvidencias;
	}

	public void setListEvidencias(List<Evidencia> listEvidencias) {
		this.listEvidencias = listEvidencias;
	}

	public Integer getIdEvidenciaSel() {
		return idEvidenciaSel;
	}

	public void setIdEvidenciaSel(Integer idEvidenciaSel) {
		this.idEvidenciaSel = idEvidenciaSel;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public OutputStream getOutoutStream() {
		return outoutStream;
	}

	public void setOutoutStream(OutputStream outoutStream) {
		this.outoutStream = outoutStream;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getEvidenciaNombre() {
		return evidenciaNombre;
	}

	public void setEvidenciaNombre(String evidenciaNombre) {
		this.evidenciaNombre = evidenciaNombre;
	}

}