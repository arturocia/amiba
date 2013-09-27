package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Bitacora;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Named
@Results({
	@Result(name = "success", type = "redirectAction", params = {
			"actionName", "operacion-bitacora/%{idSel}" })})
public class NotificacionesController extends ActionSupport implements ModelDriven<Bitacora> 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bitacora model = null;
	
	private UsuarioNegocio usuarioNegocio;
	private ProyectoNegocio proyectoNegocio;
	
	private List<Proyecto> proyectos, proyectosConPendientes;
	
	public String show() {
		return "show";
	}

	public HttpHeaders index() {
		Usuario usuarioLogeado;
		//Obtengo el tipo de usuario para saber que operacion realizar
		usuarioLogeado = (Usuario) ActionContext.getContext().getSession()
			.get(NombreObjetosSesion.USUARIO);
		usuarioLogeado = usuarioNegocio.findById(usuarioLogeado.getIdUsuario());
		proyectos = new ArrayList<Proyecto>();
		proyectosConPendientes = proyectoNegocio.getProyectosConRestricciones(usuarioLogeado.getIdUsuario());
		proyectos = proyectoNegocio.getProyectosPendientes(usuarioLogeado.getIdUsuario());
		
		//Si el  usuario es coordinador
			/* Proyectos:
			 * Se muestran los proyectos creados
			 * (Alineados y no alineados)
			 * Se muestran los cambios de estado de proyecto
			 * (edicion, rechazado, aprobacion, ejecucion)
			 * 
			 * Restricciones:
			 * Se muestran observaciones que hay en su proyecto
			 * Se muestran las atenciones que ha recibido de cada una de sus restricciones.
			*/
		
		//Si el  usuario es gerente
			/* Proyectos:
			 * Se muestran los proyectos que se han pasado para su aprobacion
			 * 
			 * Restricciones:
			 * Se muestran las restricciones pendientes que hay en los proyectos asociados al
			 * programa que tiene asociado el usuario
			 * Se muestran las restricciones turnadas que han recibido una atencion
			*/
		//Si el  usuario es secretario
			/* Restricciones:
			 * Se muestran las restricciones pendientes que tiene dirigidas el usuario
			*/
		
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public void validateCreate() {
		
	}

	@Validations()
	public HttpHeaders create() {
		return new DefaultHttpHeaders("success");
	}

	public Bitacora getModel() { 
		if (model == null) {
			model = new Bitacora();
		}
		return model;
	}

	public void setModel(Bitacora model) {
		this.model = model;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public List<Proyecto> getProyectosConPendientes() {
		return proyectosConPendientes;
	}

	public void setProyectosConPendientes(List<Proyecto> proyectosConPendientes) {
		this.proyectosConPendientes = proyectosConPendientes;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}
	
	
	
	

}
