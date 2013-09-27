package mx.ipn.escom.cdt.besp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named
@RemoteProxy
public class OperacionPlaneacionController extends ActionSupport implements
		ModelDriven<Proyecto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4263022554194532360L;
	private ProyectoNegocio proyectoNegocio;
	private Integer idSel;
	private Integer idSel2;
	private Proyecto proyecto;
	private Logger logger = Logger
			.getLogger(OperacionPlaneacionController.class);
	private Date fechaFin;
	private List<IndicadorFinanciero> list = null;
	private static final int VALOR_OPERACION_ESTADO = 4;
	
	@Override
	public Proyecto getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@SkipValidation
	public HttpHeaders index() {
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.OPERACION_ESTADO, VALOR_OPERACION_ESTADO);
		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String show() {
		ActionContext.getContext().getSession()
		.put(NombreObjetosSesion.OPERACION_ESTADO, VALOR_OPERACION_ESTADO);
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		return "show";
	}

	@RemoteMethod
	public Nodo getNodos(Integer idSel) {
		Nodo raiz;
		logger.trace(idSel);
		proyecto = proyectoNegocio.findById(idSel);
		if(proyecto.getPeriodo().getFechaFin() == null && proyecto.getPeriodo().getDuracion() != null)
		{
			Calendar cal = Calendar.getInstance();
			fechaFin = new Date();
			cal.setTime(proyecto.getPeriodo().getFechaInicio());
			cal.add(Calendar.DATE, proyecto.getPeriodo().getDuracion());
			fechaFin = cal.getTime();
			logger.trace(fechaFin);
			proyecto.getPeriodo().setFechaFin(fechaFin);
		}
		raiz = proyecto;
		proyecto.getNodosHijo();
		return raiz;
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public Integer getIdSel() {
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<IndicadorFinanciero> getList() {
		return list;
	}

	public void setList(List<IndicadorFinanciero> list) {
		this.list = list;
	}

	public Integer getIdSel2() {
		idSel2 = getIdSel();
		return idSel2;
	}

	public void setIdSel2(Integer idSel2) {
		this.idSel2 = idSel2;
	}
	
	
}