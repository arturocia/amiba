package mx.ipn.escom.cdt.besp.controller;

import org.apache.struts2.rest.HttpHeaders;

public class CatalogoGestionarProyectoController {

	public HttpHeaders create() {
		return null;
	}

	public String deleteConfirm() {
		return "deleteConfirm";
	}

	public String destroy() { 
		return "success";
	}
	
	public String edit() {
		return "edit";
	}
	
	public String editNew() {
		return "editNew";
	}

	public String update() {		
		return "success";
	}

	public HttpHeaders index() {
		return null;
	}

}
