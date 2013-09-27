package mx.ipn.escom.cdt.besp.controller;

import javax.inject.Named;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import mx.ipn.escom.cdt.besp.modelo.Proyecto;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named

public class ProyectosDisponiblesController extends ActionSupport implements ModelDriven<Proyecto> {

	private static final long serialVersionUID = -2202835566207918324L;

	@SkipValidation
	public HttpHeaders index() {
		return new DefaultHttpHeaders("index").disableCaching();
	}
	
	
	@Override
	public Proyecto getModel() {

		return null;
	}

}
