package mx.ipn.escom.cdt.besp.controller;

import javax.inject.Named;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Namespaces;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@Namespaces({ 
	@Namespace("/") })
@Named
@Results({ @Result(name = "404", location = "404.jsp"),
		@Result(name = "500", location = "500.jsp") })
public class HandlerErrorController extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String notFound() {
		return "404";
	}
}
