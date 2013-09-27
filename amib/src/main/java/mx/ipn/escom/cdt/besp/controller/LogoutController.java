package mx.ipn.escom.cdt.besp.controller;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;

@Named
@Results({ @Result(name = "login", location = "/login.jsp") })
public class LogoutController {

	private Logger logger = Logger.getLogger(LogoutController.class);

	public String execute() {
		logger.trace("Se termina la sesion");
		ActionContext.getContext().getSession().remove(NombreObjetosSesion.USUARIO);
		ActionContext.getContext().getSession().clear();
		return "login";
	}

	public String index() {
		return execute();
	}

}