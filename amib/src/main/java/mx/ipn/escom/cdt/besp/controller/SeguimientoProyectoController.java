package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * Clase que gestiona a los proyectos que se lleva seguimiento
 * 
 * 8/07/2013
 * 
 * @author Pedro
 *
 */

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName", "operacion-bitacora" }) })
public class SeguimientoProyectoController extends ActionSupport implements ModelDriven<Proyecto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idSel;
	private Proyecto model;
	private Usuario usuario;
	private List<Proyecto> listaProyectos;
	private List<Usuario> listaSubordinados;
	private ProyectoNegocio proyectoNegocio;
	
	public HttpHeaders index() {
		listaProyectos=new ArrayList<Proyecto>();
		usuario=(Usuario) ActionContext.getContext().getSession().get(NombreObjetosSesion.USUARIO);
		listaSubordinados=usuario.getSuperior();
		for (Usuario lider : listaSubordinados) {
			listaProyectos.addAll(lider.getProyectosEnEjecucion());
		}
		return new DefaultHttpHeaders("index").disableCaching();
	}
	
	
	public String operacionBitacora(){
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.PROYECTO, model);
		return "success";
	}
	@Override
	public Proyecto getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(Proyecto model) {
		this.model = model;
	}

	public List<Proyecto> getListaProyectos() {
		return listaProyectos;
	}

	public void setListaProyectos(List<Proyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaSubordinados() {
		return listaSubordinados;
	}

	public void setListaSubordinados(List<Usuario> listaSubordinados) {
		this.listaSubordinados = listaSubordinados;
	}


	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
		if (idSel != null) {
			model = proyectoNegocio.findById(idSel);
		}
	}
}
