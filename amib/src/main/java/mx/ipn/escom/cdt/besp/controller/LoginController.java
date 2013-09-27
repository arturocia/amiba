package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Named
@Results({
		// Cuando hay error
		@Result(name = "error", location = "login.jsp"),
		// Cuando hay error
		@Result(name = "fullLog", location = "blok.jsp"),
		@Result(name = "perfil_administrador", type = "redirectAction", params = {
				"actionName", "catalogo-usuario" }),
		@Result(name = "perfil_coordinador", location = "blank.jsp"),
		
		@Result(name = "perfil_director", location = "blank.jsp"), 

		@Result(name = "perfil_secretario", location = "blank.jsp"), 
		
		@Result(name = "perfil_gerente", location="blank.jsp")
		
		})
public class LoginController extends ActionSupport implements
		ServletRequestAware {

	private static final long serialVersionUID = -7475211274962357078L;
	private static final int SEGUNDOS = 60;
	private static final int VECES_SEGUNDOS = 1000;
	private static final int MAXIMO_MINIMO = 5;
	private static final int MAXIMO_LISTA_LOG = 3;
	private Logger logger = Logger.getLogger(LoginController.class);
	private String userId;
	private String password;
	private Usuario usuarioSel;
	private UsuarioNegocio service;
	private int perfilUsuario;
	private HttpServletRequest request;
	private ProyectoNegocio proyectoNegocio;
	private List<Proyecto> listProyectos;


	@Override
	public String execute(){

		usuarioSel = new Usuario();
		List<Usuario> usuarios = null;
		usuarioSel.setLogin(userId);
		usuarioSel.setPsw(password);
		usuarioSel.setActivado(true);

		try {
			usuarios = service.findByExample(usuarioSel);
			logger.trace("Se encontraron " + usuarios
					+ " usuarios al loguearse");
			if (usuarios.isEmpty()) {
				addActionError("Usuario y/o contraseña incorrectos");
				return validaIntentos(request);
			}

			else {
				clearActionErrors();
				ActionContext.getContext().getSession()
						.put(NombreObjetosSesion.USUARIO, usuarios.get(0));
				switch (usuarios.get(0).getIdPerfilUsuario()) {
				case PerfilUsuario.ADMINISTRADOR:
					perfilUsuario = PerfilUsuario.ADMINISTRADOR;
					return "perfil_administrador";
				case PerfilUsuario.COORDINADOR:
					perfilUsuario = PerfilUsuario.COORDINADOR;
					listProyectos = proyectoNegocio.proyectosUsuario(usuarios.get(0).getIdUsuario());
					ActionContext.getContext().getSession().put(NombreObjetosSesion.PROYECTOS_ASOCIADOS, listProyectos);
					return "perfil_coordinador";
				case PerfilUsuario.DIRECTORGENERAL:
					perfilUsuario = PerfilUsuario.DIRECTORGENERAL;
					return "perfil_director";
				case PerfilUsuario.GERENTE:
					perfilUsuario = PerfilUsuario.GERENTE;
					return "perfil_gerente";
				case PerfilUsuario.SECRETARIO:
					perfilUsuario = PerfilUsuario.SECRETARIO;
					return "perfil_secretario";
				}
				return "nada";
			}

		} catch (Exception e) {
			logger.error("Se produjo algún error con la base de datos");
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "error";
		}
	}

	@SuppressWarnings("unchecked")
	// por el casting a lista de Date
	public String validaIntentos(HttpServletRequest req) {

		List<Date> listaLog;
		Date actual;
		listaLog = (List<Date>) ActionContext
				.getContext()
				.getSession()
				.get(NombreObjetosSesion.PREFIJO_LISTA_ACCESO_USUARIO
						+ req.getSession().getId());
		long min;

		if (listaLog == null) {
			logger.trace("obtuvo lista vacia");
			listaLog = new ArrayList<Date>();
			listaLog.add(new Date());
			ActionContext
					.getContext()
					.getSession()
					.put(NombreObjetosSesion.PREFIJO_LISTA_ACCESO_USUARIO
							+ req.getSession().getId(), listaLog);
			return "error";
		} else {
			logger.trace("obtuvo lista con algo");
			actual = new Date();

			for (int i = 0; i < listaLog.size(); i++) {
				// quita los atrasados
				min = (actual.getTime() - listaLog.get(0).getTime())
						/ (VECES_SEGUNDOS * SEGUNDOS);
				if (min > MAXIMO_MINIMO) {
					listaLog.remove(0);
				}
			}
			// termina de quitar los atrasados
			if (listaLog.size() < MAXIMO_LISTA_LOG) {
				listaLog.add(new Date());
				return "error";
			} else {
				return "fullLog";
			}
		}

	}

	public LoginController(UsuarioNegocio loginService) {
		service = loginService;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String create(){
		return execute();
	}

	public String index(){
		usuarioSel = (Usuario) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.USUARIO);
		return create();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setLoginService(UsuarioNegocio s) {
		service = s;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public int getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(int perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}
}