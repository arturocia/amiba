package mx.ipn.escom.cdt.besp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Indicador;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.IndicadorNegocio;
import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Named
@Results({ @Result(name = "success", type = "redirectAction", params = {
		"actionName",
		"operacion-revisar-avances/${#session[@mx.ipn.escom.cdt.besp.util.NombreObjetosSesion@ACCION].idProyecto}" }) })
public class ReportarAvanceProyectoController extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7618738499474344054L;
	private Logger logger = Logger
			.getLogger(ReportarAvanceProyectoController.class);
	private IndicadorNegocio indicadorNegocio;
	private AccionNegocio accionNegocio;

	private Integer idAccionSel;
	private Accion accionSel;
	private List<Indicador> list;
	private List<Integer> idIndicadoresModificados;
	private Integer idProyecto;
	private Map<Integer, Integer> listaAvancesMap;
	private Integer idSel;

	public String index() {

		accionSel = (Accion) ActionContext.getContext().getSession()
				.get(NombreObjetosSesion.ACCION);
		accionSel = (Accion) ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.ACCION, NombreObjetosSesion.ACCION);
		if (accionSel != null) {
			accionSel = accionNegocio.findById(this.accionSel.getIdAccion());
			idProyecto = accionSel.getIdProyecto();
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCION, accionSel);
			list = accionSel.getIndicadores();
		}
		if (idAccionSel != null) {
			accionSel = accionNegocio.findById(idAccionSel);
			ActionContext.getContext().getSession()
					.put(NombreObjetosSesion.ACCION, accionSel);
			idProyecto = accionSel.getIdProyecto();
			list = accionSel.getIndicadores();
		}

		listaAvancesMap = new HashMap<Integer, Integer>();

		for (Indicador indicador : list) {
			listaAvancesMap.put(indicador.getIdIndicador(),
					indicador.getAvance());
		}

		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.INDICADORES, list);
		ActionContext.getContext().getSession()
				.put(NombreObjetosSesion.AVANCES_ORIGINALES, listaAvancesMap);
		return "index";
	}

	public void validateUpdate() {
		List<Indicador> indicadoresAModificar;

		indicadoresAModificar = new ArrayList<Indicador>();
		logger.trace("valor is¿dsel" + idSel);
		if (idIndicadoresModificados == null) {
			idIndicadoresModificados = new ArrayList<Integer>();
		}
		for (Indicador indicador : getList()) {
			if (indicador != null
					&& idIndicadoresModificados.contains(indicador
							.getIdIndicador())) {
				indicadoresAModificar.add(indicador);
			}
		}

		if (!indicadorNegocio.validaAvances(indicadoresAModificar)) {
			addActionError(getText("validaAvances"));
		}

		if (!indicadorNegocio.validaAvancesValorMaximo(indicadoresAModificar)) {
			addActionError(getText("validaAvancesValorMaximo"));
		}
		logger.trace("En validate update");
		logger.trace("Las selecciones son " + idIndicadoresModificados);
		logger.trace("Los indicadores " + list);
	}

	@Validations(expressions = { @ExpressionValidator(expression = "!idIndicadoresModificados.isEmpty", key = "reportarUnIndicador") })
	public String update() {
		Date fechaActual = new Date();
		for (Indicador indicador : list) {
			if (idIndicadoresModificados.contains(indicador.getIdIndicador())) {
				logger.trace("Se modificara " + indicador);
				indicador.setFechaUltimoReporte(fechaActual);
				indicadorNegocio.update(indicador);
			}
		}

		return "success";
	}

	@SuppressWarnings("unchecked")
	// Por la conversión de object a lista de indicadores
	public List<Indicador> getList() {
		if (list == null) {
			list = (List<Indicador>) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.INDICADORES);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getListaAvancesMap() {
		if (listaAvancesMap == null) {
			listaAvancesMap = (Map<Integer, Integer>) ActionContext
					.getContext().getSession()
					.get(NombreObjetosSesion.AVANCES_ORIGINALES);
		}
		return listaAvancesMap;
	}

	public IndicadorNegocio getIndicadorNegocio() {
		return indicadorNegocio;
	}

	public void setIndicadorNegocio(IndicadorNegocio indicadorNegocio) {
		this.indicadorNegocio = indicadorNegocio;
	}

	public void setList(List<Indicador> list) {
		this.list = list;
	}

	public AccionNegocio getAccionNegocio() {
		return accionNegocio;
	}

	public void setAccionNegocio(AccionNegocio accionNegocio) {
		this.accionNegocio = accionNegocio;
	}

	public Integer getIdAccionSel() {
		return idAccionSel;
	}

	public void setIdAccionSel(Integer idAccionSel) {
		this.idAccionSel = idAccionSel;
	}

	public Accion getAccionSel() {
		return accionSel;
	}

	public void setAccionSel(Accion accionSel) {
		this.accionSel = accionSel;
	}

	public List<Integer> getIdIndicadoresModificados() {
		return idIndicadoresModificados;
	}

	public void setIdIndicadoresModificados(
			List<Integer> idIndicadoresModificados) {
		this.idIndicadoresModificados = idIndicadoresModificados;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public void setListaAvancesMap(Map<Integer, Integer> listaAvancesMap) {
		this.listaAvancesMap = listaAvancesMap;
	}

	public Integer getIdSel() {
		if (idSel == null) {
			Accion accAux;
			accAux = (Accion) ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.ACCION);
			idSel = accAux.getIdProyecto();
		}
		return idSel;
	}

	public void setIdSel(Integer idSel) {
		this.idSel = idSel;
	}
}