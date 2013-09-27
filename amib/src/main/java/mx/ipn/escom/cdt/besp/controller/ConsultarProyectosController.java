package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Area;
import mx.ipn.escom.cdt.besp.modelo.EjeTematico;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.AreaNegocio;
import mx.ipn.escom.cdt.besp.negocio.EjeTematicoNegocio;
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
import com.opensymphony.xwork2.ModelDriven;

@Named
@Results({
	@Result(name = "success", type = "redirectAction", params = {
			"actionName",
	"catalogo-indicador?idAccionSel=%{model.idAccion}" }),
	@Result(name = "combos", type = "json", params = { "includeProperties",
	"listUnidad.*" }) })
public class ConsultarProyectosController extends ActionSupport implements
ModelDriven<Proyecto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1159376627591347272L;


	private Logger logger = Logger.getLogger(ConsultarProyectosController.class);

	private List<Area> listAreas;
	private List<TemaTransversal> listTemas;
	private List<EjeTematico> listEjes;
	private List<Proyecto> listProyectos;
	private List<Programa> listProgramas;
	private List<Proyecto> listProyecto;
	private AreaNegocio areaNegocio;
	private TemaTransversalNegocio temaTransversalNegocio;
	private EjeTematicoNegocio ejeTematicoNegocio;
	private ProgramaNegocio programaNegocio;
	private ProyectoNegocio proyectoNegocio;
	private Proyecto model = null;	 
	
	private Integer idSel = null;
	private Usuario usuarioLogeado;
	private List<Integer> idProgramas;
	private List<Integer> idEjes;
	private List<Integer> idAreas;
	private List<Integer> idTemas;


	@SkipValidation
	public String buscar() {
		logger.trace("la lista de programas es" + idProgramas);
		listProyectos = proyectoNegocio.getProyectos(idProgramas, idEjes,
				idTemas, idAreas);

		return "index";
	}
	@SkipValidation
	public HttpHeaders index() {		 
		usuarioLogeado=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
		
		listProgramas = programaNegocio.findAll();
		
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

	public String show()
	{
		if(idSel!=null){

			this.model=proyectoNegocio.findById(idSel);
		}
		return "show";
	}

	@Override
	public Proyecto getModel() {
		// TODO Auto-generated method stub
		return this.model;
	}
	public void setModel(Proyecto model) {
		this.model = model;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}	
	@SuppressWarnings("unchecked")
	// por el cast de string a lista areas
	public List<Area> getListAreas() {
		if (listAreas == null)
		{
			listAreas = (List<Area>) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.AREAS);
		}
		return listAreas;
	}

	public void setListAreas(List<Area> listAreas) {
		this.listAreas = listAreas;
	}
	@SuppressWarnings("unchecked")
	// por el cast de string a lista temas
	public List<TemaTransversal> getListTemas() {
		if (listTemas == null)
		{
			listTemas = (List<TemaTransversal>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.TEMAS);
		}
		return listTemas;
	}

	public void setListTemas(List<TemaTransversal> listTemas) {
		this.listTemas = listTemas;
	}
	@SuppressWarnings("unchecked")
	// por el cast de string a lista ejes
	public List<EjeTematico> getListEjes() {
		if (listEjes == null)
		{
			listEjes = (List<EjeTematico>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.EJES);
		}
		return listEjes;
	}

	public void setListEjes(List<EjeTematico> listEjes) {
		this.listEjes = listEjes;
	}

	public List<Proyecto> getListProyectos() {		
				return listProyectos;
	}

	public void setListProyectos(List<Proyecto> listProyectos) {
		this.listProyectos = listProyectos;
	}

	public List<Programa> getListProgramas() {
		if (listProgramas == null)
		{
			listProgramas = (List<Programa>) ActionContext.getContext()
					.getSession().get(NombreObjetosSesion.PROGRAMAS);
		}
		return listProgramas;
	}

	public void setListProgramas(List<Programa> listProgramas) {
		this.listProgramas = listProgramas;
	}	

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public Usuario getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(Usuario usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	

	public List<Proyecto> getListProyecto() {
		return listProyecto;
	}

	public void setListProyecto(List<Proyecto> listProyecto) {
		this.listProyecto = listProyecto;
	}

	public AreaNegocio getAreaNegocio() {
		return areaNegocio;
	}

	public void setAreaNegocio(AreaNegocio areaNegocio) {
		this.areaNegocio = areaNegocio;
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

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}
	public List<Integer> getIdProgramas() {
		return idProgramas;
	}
	public void setIdProgramas(List<Integer> idProgramas) {
		this.idProgramas = idProgramas;
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
	
	

}
