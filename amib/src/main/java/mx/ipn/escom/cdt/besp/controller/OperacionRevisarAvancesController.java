package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.*;
import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName",
		"operacion-revisar-avances/${#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@ACCION].idProyecto}" }) })
@RemoteProxy
public class OperacionRevisarAvancesController extends ActionSupport implements
		ModelDriven<Proyecto> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8043444734389256938L;
	private static final int VECES_COSTO_TOTAL = 100;
	private static final int VALOR_OPERACION_ESTADO = 3;
	private ProyectoNegocio proyectoNegocio;
	private AvanceFinancieroNegocio avanceFinancieroNegocio;
	private Integer idSel;
	private Logger logger = Logger
			.getLogger(OperacionPlaneacionController.class);
	private List<IndicadorFinanciero> list = null;
	private Proyecto proyecto;
	private double totalEjercido;
	private double porcentajePonderado;

	@SkipValidation
	public HttpHeaders index() {
		int x, y, z;
		// //////////////Avance Total Ponderado////////////////
		int a = 0;
		List<Accion> accPonderadas;
		List<Indicador> pondera;
		porcentajePonderado = 0;
		accPonderadas = proyecto.getAcciones();
		for (a = 0; a < accPonderadas.size(); a++) {
			// recorre las acciones y cada accion solo tiene un indicador
			pondera = accPonderadas.get(0).getIndicadores();
			pondera.get(0)
					.getUnidad().getTipoUnidad();
			// si el indicador es ponderado
			if (pondera.get(0).getUnidad().getIdTipoUnidad() == TipoUnidad.PONDERADO) {
				porcentajePonderado += (accPonderadas.get(0).getIndicadores()
						.get(0).getAvance() / accPonderadas.get(0)
						.getIndicadores().get(0).getMeta())
						* accPonderadas.get(0).getIndicadores().get(0)
								.getPeso();
			}
		}
		ActionContext.getContext().getSession()
				.put("porcentajePonderado", porcentajePonderado);
		x = 0;
		y = 0;
		z = list.size();
		for (x = 0; x < z; x++) {
			y += list.get(x).getMontoErogado();
		}
		totalEjercido = (y / proyecto.getCostoTotal()) * VECES_COSTO_TOTAL;
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.OPERACION_ESTADO, VALOR_OPERACION_ESTADO);
		ActionContext.getContext().getSession()
				.put("totalEjercido", totalEjercido);

		return new DefaultHttpHeaders("index").disableCaching();
	}

	public String show() {
		int x, y, z;
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.OPERACION_ESTADO, VALOR_OPERACION_ESTADO);
		proyecto = proyectoNegocio.findById(idSel);
		list = proyecto.getIndicadoresFinancieros();
		// //////////////Avance Total Ponderado////////////////
		int a = 0;
		List<Accion> accPonderadas;
		List<Indicador> pondera;
		porcentajePonderado = 0;
		accPonderadas = proyecto.getAcciones();
		for (a = 0; a < accPonderadas.size(); a++) {
			// recorre las acciones y cada accion solo tiene un indicador
			pondera = accPonderadas.get(0).getIndicadores();
			if(!pondera.isEmpty()){
			pondera.get(0)
					.getUnidad().getTipoUnidad();
			// si el indicador es ponderado
			if (pondera.get(0).getUnidad().getIdTipoUnidad() == TipoUnidad.PONDERADO) {
				porcentajePonderado += (accPonderadas.get(0).getIndicadores()
						.get(0).getAvance() / accPonderadas.get(0)
						.getIndicadores().get(0).getMeta())
						* accPonderadas.get(0).getIndicadores().get(0)
								.getPeso();
			}
			}
		}
		ActionContext.getContext().getSession()
				.put("porcentajePonderado", porcentajePonderado);
		x = 0;
		y = 0;
		z = list.size();
		for (x = 0; x < z; x++) {
			y += list.get(x).getMontoErogado();
		}
		totalEjercido = (y / proyecto.getCostoTotal()) * VECES_COSTO_TOTAL;
		ActionContext.getContext().getSession()
				.put("totalEjercido", totalEjercido);
		return "show";
	}

	@RemoteMethod
	public Nodo getNodos(Integer idSel) {
		Nodo raiz;
		
		logger.trace(idSel);
		proyecto = proyectoNegocio.findById(idSel);

		raiz = proyecto;
		proyecto.getNodosHijo();
		return raiz;
	}

	@Override
	public Proyecto getModel() {
		return null;
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

	public List<IndicadorFinanciero> getList() {
		return list;
	}

	public void setList(List<IndicadorFinanciero> list) {
		this.list = list;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public AvanceFinancieroNegocio getAvanceFinancieroNegocio() {
		return avanceFinancieroNegocio;
	}

	public void setAvanceFinancieroNegocio(
			AvanceFinancieroNegocio avanceFinancieroNegocio) {
		this.avanceFinancieroNegocio = avanceFinancieroNegocio;
	}

	public double getTotalEjercido() {
		int x, y, z;
		list = proyecto.getIndicadoresFinancieros();
		x = 0;
		y = 0;
		z = list.size();
		for (x = 0; x < z; x++) {
			y += list.get(x).getMontoErogado();
		}
		totalEjercido = (y / proyecto.getCostoTotal()) * VECES_COSTO_TOTAL;
		ActionContext.getContext().getSession()
				.put("totalEjercido", totalEjercido);
		return totalEjercido;
	}

	public void setTotalEjercido(double totalEjercido) {
		this.totalEjercido = totalEjercido;
	}

	public double getPorcentajePonderado() {
		// //////////////Avance Total Ponderado////////////////
		int a = 0;
		List<Accion> accPonderadas;
		List<Indicador> pondera;
		porcentajePonderado = 0;
		accPonderadas = proyecto.getAcciones();
		for (a = 0; a < accPonderadas.size(); a++) {
			// recorre las acciones y cada accion solo tiene un indicador
			pondera = accPonderadas.get(0).getIndicadores();
			pondera.get(0)
					.getUnidad().getTipoUnidad();
			// si el indicador es ponderado
			if (pondera.get(0).getUnidad().getIdTipoUnidad() == TipoUnidad.PONDERADO) {
				porcentajePonderado += (accPonderadas.get(0).getIndicadores()
						.get(0).getAvance() / accPonderadas.get(0)
						.getIndicadores().get(0).getMeta())
						* accPonderadas.get(0).getIndicadores().get(0)
								.getPeso();
			}
		}
		ActionContext.getContext().getSession()
				.put("porcentajePonderado", porcentajePonderado);
		return porcentajePonderado;
	}

	public void setPorcentajePonderado(double porcentajePonderado) {
		this.porcentajePonderado = porcentajePonderado;
	}
}