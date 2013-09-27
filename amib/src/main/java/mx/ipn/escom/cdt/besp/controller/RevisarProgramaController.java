package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.RaizAuxiliar;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.ProgramaNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "catalogo-estructura" }) })
@RemoteProxy
public class RevisarProgramaController extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1961670065733485548L;
	private RaizAuxiliar raizAuxiliar;
	private ProgramaNegocio programaNegocio;
	private ProyectoNegocio proyectoNegocio;
	private List<Proyecto> listaProyectos;

	private Logger logger = Logger.getLogger(RevisarProgramaController.class);

	public HttpHeaders index() {
		listaProyectos = proyectoNegocio.getProyectosAAprobar((Usuario) ActionContext
				.getContext().getSession().get(NombreObjetosSesion.USUARIO));
		logger.trace("Fin del index");
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@RemoteMethod
	public Nodo getNodos() {
		List<Programa> programasFiltrados = new ArrayList<Programa>();
		List<Programa> listaProgramas = programaNegocio.findAll();
		
		logger.trace("getNodos....");
		Nodo raiz;
		Usuario usuario;

		WebContext ctx = WebContextFactory.get();
		HttpServletRequest req = ctx.getHttpServletRequest();
		usuario = (Usuario) req.getSession().getAttribute(
				NombreObjetosSesion.USUARIO);

		if (usuario != null
				&& usuario.getIdPerfilUsuario().equals(PerfilUsuario.GERENTE)) {
			for (Programa programa : listaProgramas) {
				if (programa.getIdUsuario().equals(usuario.getIdUsuario())) {
					programasFiltrados.add(programa);
				}
			}
			listaProgramas = programasFiltrados;
		}

		logger.trace("Programas" + listaProgramas);

		logger.trace("Dentro de getNodos");
		raizAuxiliar = new RaizAuxiliar();
		raizAuxiliar.setListaProgramas(listaProgramas);
		raiz = raizAuxiliar;

		return raiz;
	}

	public RaizAuxiliar getRaizAuxiliar() {
		return raizAuxiliar;
	}

	public void setRaizAuxiliar(RaizAuxiliar raizAuxiliar) {
		this.raizAuxiliar = raizAuxiliar;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public List<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<Proyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

}