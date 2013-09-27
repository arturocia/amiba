package mx.ipn.escom.cdt.besp.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.PeriodoDao;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("periodoNegocio")
public class PeriodoNegocio {

	private Logger logger = Logger.getLogger(PeriodoNegocio.class);
	private PeriodoDao periodoDao;

	@Transactional
	public List<Periodo> findAll() {
		return periodoDao.findAll();
	}

	@Transactional
	public Periodo findById(Integer id) {
		return periodoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Periodo save(Periodo entidad) {
		return periodoDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void delete(Periodo entidad) {
		periodoDao.delete(entidad);
	}

	@Transactional
	public List<Periodo> findByExample(Periodo periodo) {
		return periodoDao.findByExample(periodo);
	}

	/**
	 * 
	 * @param periodo
	 * @return Si el periodo esta bien formado de acuerdo a la regla de negocio
	 *         32 de periodo bien definido
	 */
	@Transactional
	public Boolean validaBienFormado(Periodo periodo) {
		logger.trace("El periodo que se va a validar " + periodo);

		if (periodo.getFechaFin() != null
				&& periodo.getFechaInicio() != null
				&& (periodo.getFechaFin().getTime() >= periodo.getFechaInicio()
						.getTime())) {
			return true;
		}

		if (periodo.getFechaFin() == null
				&& periodo.getFechaInicio() == null
				&& (periodo.getDuracion() == null || periodo.getDuracion()
						.equals(0))) {
			
			return true;
		}

		if (periodo.getDuracion() != null && !periodo.getDuracion().equals(0)
				&& periodo.getFechaFin() == null) {
			
			return true;
		}

		return false;

	}

	/**
	 * 
	 * @param periodoAncestro
	 * @param periodoAValidar
	 * @return Si el periodo <code>periodoAValidar</code> con respecto a
	 *         <code>periodoAncestro</code> es valido
	 */
	public Boolean determinaPeriodoValido(Periodo periodoAncestro,
			Periodo periodoAValidar) {

		Boolean valido = false;
		Date fechaFinPeriodoAValidar;
		Date fechaFinPeriodoAncestro;
		Integer tipoPeriodoAValidar;
		Integer tipoPeriodoAncestro = Periodo.PERIODO_INDEFINIDO;
		Calendar calendario;

		tipoPeriodoAValidar = periodoAValidar.getTipoPeriodo();
		tipoPeriodoAncestro = periodoAncestro.getTipoPeriodo();
		logger.trace("El periodo a validar es " + periodoAValidar
				+ " el periodo ancestra " + periodoAncestro);
		logger.trace("El tipo de periodo a valida " + tipoPeriodoAValidar
				+ " y el tipo de periodo ancestro " + tipoPeriodoAncestro);
		if (tipoPeriodoAncestro.equals(Periodo.PERIODO_INVALIDO)
				|| tipoPeriodoAValidar.equals(Periodo.PERIODO_INVALIDO)) {
			return false;
		}

		switch (tipoPeriodoAncestro) {
		case Periodo.PERIODO_INDEFINIDO:
			valido = true;
			break;
		case Periodo.PERIODO_RELATIVO:
			switch (tipoPeriodoAValidar) {
			case Periodo.PERIODO_RELATIVO:
				if (periodoAValidar.getDuracion() > periodoAncestro
						.getDuracion()) {
					valido = false;
				} else {
					valido = true;
				}
				break;
			case Periodo.PERIODO_DEFINIDO:
				valido = false;
				break;
			default:
				valido = false;
				break;
			}
			break;
		case Periodo.PERIODO_DEFINIDO:
			switch (tipoPeriodoAValidar) {
			case Periodo.PERIODO_RELATIVO:
				valido = false;
				break;
			case Periodo.PERIODO_DEFINIDO:
				if (periodoAncestro.getFechaFin() != null) {
					fechaFinPeriodoAncestro = periodoAncestro.getFechaFin();
				} else {
					calendario = GregorianCalendar.getInstance();
					calendario.setTime(periodoAncestro.getFechaInicio());
					calendario.add(GregorianCalendar.DATE,
							periodoAncestro.getDuracion());
					fechaFinPeriodoAncestro = calendario.getTime();
				}

				if (periodoAValidar.getFechaFin() != null) {
					logger.trace("La fecha de fin esta provista");
					fechaFinPeriodoAValidar = periodoAValidar.getFechaFin();
				} else {
					logger.trace("Calculando la fecha fin "
							+ periodoAValidar.getDuracion());
					calendario = GregorianCalendar.getInstance();
					calendario.setTime(periodoAValidar.getFechaInicio());
					calendario.add(GregorianCalendar.DATE,
							periodoAValidar.getDuracion());
					fechaFinPeriodoAValidar = calendario.getTime();
					logger.trace("La fecha final quedo "
							+ fechaFinPeriodoAValidar);
				}
				logger.trace("La fecha de fin " + fechaFinPeriodoAValidar
						+ " y la fecha fin ancestro " + fechaFinPeriodoAncestro);

				if (periodoAncestro.getFechaInicio().getTime() > periodoAValidar
						.getFechaInicio().getTime()
						|| fechaFinPeriodoAncestro.getTime() < fechaFinPeriodoAValidar
								.getTime()) {
					valido = false;
				} else {
					valido = true;
				}

				break;
			default:
				valido = true;
				break;
			}
			break;
		default:
			valido = false;
			break;
		}

		return valido;
	}

	/**
	 * Valida la RN7 para estructura hacia arriba. Se da por hecho que
	 * <code>nodoAValidar</code> tiene la referencia del objeto padre.
	 * 
	 * @param nodoAValidar
	 * @return Verdadero si el periodo de <code>nodoAValidar</code> no entra en
	 *         conflicto con el de sus ancestros.
	 */

	// esta madre es la que tengo que hacer (Arturo)
	@Transactional
	public Boolean validaPeriodo(Nodo nodoAValidar) {
		Boolean valido = true;
		Nodo nodoSuperior;
		Integer tipoPeriodoAValidar;
		Integer tipoPeriodoAncestro = Periodo.PERIODO_INDEFINIDO;

		nodoSuperior = nodoAValidar.getNodoPadre();

		tipoPeriodoAValidar = nodoAValidar.getPeriodo().getTipoPeriodo();

		if (tipoPeriodoAValidar.equals(Periodo.PERIODO_INDEFINIDO)) {
			return true;
		}

		while (nodoSuperior != null && nodoSuperior.getPeriodo() != null) {
			logger.trace("El nodo superior que se esta evaluando "
					+ nodoSuperior);
			tipoPeriodoAncestro = nodoSuperior.getPeriodo().getTipoPeriodo();

			if (tipoPeriodoAncestro.equals(Periodo.PERIODO_DEFINIDO)
					|| tipoPeriodoAncestro.equals(Periodo.PERIODO_RELATIVO)) {
				break;
			}

			nodoSuperior = nodoSuperior.getNodoPadre();
		}
		logger.trace("El primer ancestro mas restrictivo " + nodoSuperior);
		if (nodoSuperior != null && nodoSuperior.getPeriodo() != null) {
			valido = determinaPeriodoValido(nodoSuperior.getPeriodo(),
					nodoAValidar.getPeriodo());
		}
		return valido;
	}

	/**
	 * 3/julio/13 Karen
	 * Metodo que devuelve true o false en caso de que algun hijo tenga fechas
	 */
	public Boolean tieneHijosConFecha(Nodo nodoAValidar){
		Boolean bandera = false;
		List<Nodo> listaHijos = nodoAValidar.getNodosHijo();
		for (Nodo nodo : listaHijos) {
			if(nodo.getPeriodo().getFechaInicio()!=null && nodo.getPeriodo().getFechaFin()!=null){
				bandera = true;
				break;
			}else{
				bandera = false;
			}
		}
		return bandera;
	}
	
	/**
	 * 5/julio/13 Karen
	 * Método que obtiene la fecha inicial menor de todos los hijos 
	 */
	public Date obtieneFechaInicialMenorHijos(Nodo nodoAValidar){
		List<Nodo> listaHijos = nodoAValidar.getNodosHijo();
		Date fechaInicioMenor = null;
		
		if(listaHijos!=null && listaHijos.size()>0){
			for (Nodo nodo : listaHijos) {
				if(nodo.getPeriodo().getFechaInicio()!=null){
					if(fechaInicioMenor!=null){
						if(fechaInicioMenor.after(nodo.getPeriodo().getFechaInicio())){
							fechaInicioMenor = nodo.getPeriodo().getFechaInicio();
						}
					}else{
						fechaInicioMenor = nodo.getPeriodo().getFechaInicio();
					}
				}
			}
		}
		return fechaInicioMenor;
	}
	
	/**
	 * 5/julio/13 Karen
	 * Método que obtiene la fecha final mayor de todos los hijos 
	 */
	public Date obtieneFechaFinalMayorHijos(Nodo nodoAValidar){
		List<Nodo> listaHijos = nodoAValidar.getNodosHijo();
		Date fechaFinalMayor = null;
		
		if(listaHijos!=null && listaHijos.size()>0){
			for (Nodo nodo : listaHijos) {
				if(nodo.getPeriodo().getFechaFin()!=null){
					if(fechaFinalMayor!=null){
						if(fechaFinalMayor.before(nodo.getPeriodo().getFechaFin())){
							fechaFinalMayor = nodo.getPeriodo().getFechaFin();
						}
					}else{
						fechaFinalMayor = nodo.getPeriodo().getFechaFin();
					}
				}
			}
		}
		return fechaFinalMayor;
	}
	
	/**
	 * Hace lo mismo que {@link PeriodoNegocio#validaPeriodo(Nodo)} pero
	 * exclusivamente para nodos de tipo {@link Proyecto}. Se da por hecho que
	 * la lista de estructuras padres tiene las referencias a las estructuras
	 * asociadas
	 * 
	 * @param nodoAValidar
	 * @return
	 */
	@Transactional
	public Boolean validaPeriodoEstructura(Proyecto nodoAValidar) {
		Boolean valido = true;
		Nodo nodoSuperior;
		Integer tipoPeriodoAValidar;
		Integer tipoPeriodoAncestro = Periodo.PERIODO_INDEFINIDO;

		for (Nodo estructura : nodoAValidar.getEstructuras()) {

			nodoSuperior = estructura;

			tipoPeriodoAValidar = nodoAValidar.getPeriodo().getTipoPeriodo();

			if (tipoPeriodoAValidar.equals(Periodo.PERIODO_INDEFINIDO)) {
				//no siempre
				
				return true;
			}

			while (nodoSuperior != null && nodoSuperior.getPeriodo() != null) {
				logger.trace("El nodo superior que se esta evaluando "
						+ nodoSuperior);
				tipoPeriodoAncestro = nodoSuperior.getPeriodo()
						.getTipoPeriodo();

				if (tipoPeriodoAncestro.equals(Periodo.PERIODO_DEFINIDO)
						|| tipoPeriodoAncestro.equals(Periodo.PERIODO_RELATIVO)) {
					break;
				}

				nodoSuperior = nodoSuperior.getNodoPadre();
			}
			logger.trace("El primer ancestro mas restrictivo " + nodoSuperior);
			if (nodoSuperior != null && nodoSuperior.getPeriodo() != null) {
				valido = determinaPeriodoValido(nodoSuperior.getPeriodo(),
						nodoAValidar.getPeriodo());
			}
			if (!valido) {
				break;
			}
		}
		return valido;
	}

	@Transactional
	public Nodo validaNoIndefinidoRelativo(Nodo nodo) {
		Nodo nodoPrueba;
		nodoPrueba = nodo.getNodoPadre();
		while (nodoPrueba != null && nodoPrueba.getNodoPadre() != null) {

			if (nodoPrueba.getPeriodo().getTipoPeriodo()
					.equals(Periodo.PERIODO_DEFINIDO)
					|| nodoPrueba.getPeriodo().getTipoPeriodo()
							.equals(Periodo.PERIODO_RELATIVO)) {
				break;
			}
			nodoPrueba = nodoPrueba.getNodoPadre();
		}
		return nodoPrueba;
	}

	public PeriodoDao getEstadoDao() {
		return periodoDao;
	}

	public void setPeriodoDao(PeriodoDao periodoDao) {
		this.periodoDao = periodoDao;
	}
	
	public Nodo getNodoPadre(Nodo nodo){
		Nodo nodoPadre=nodo;
		if(nodo.getPeriodo().getFechaInicio() ==null && nodo.getPeriodo().getFechaFin()==null){
			if(nodo.getNodoPadre()!=null){
				nodoPadre=nodo.getNodoPadre();
				return getNodoPadre(nodoPadre);
			}else{
				return nodo;
			}
		}else{
			return nodo;
		}
	}

	@SuppressWarnings("unused")
	public Nodo getNodoPadreConPeriodoRestrictivo(Nodo nodo) {
		Nodo nodoCopia = nodo;
		// cuando el nodo tenga un padre entra en este caso;
		if ((nodo = nodo.getNodoPadre()) != null) {
			if (isRelativoOrDefinido(nodo)) { // comprobamos si ese padre es
												// definido o relativo
				return nodo;
			} else {
				return getNodoPadreConPeriodoRestrictivo(nodo);
			}
		} else {// si no tiene un padre se comprueba que sea un Proyecto o
				// Programa
			if (nodoCopia instanceof Proyecto) {
				logger.trace("El nodo es un Proyecto");
				List<Estructura> estructurasPadre = ((Proyecto) nodoCopia)
						.getEstructuras();
				Nodo padreX = null;
				Nodo padreY = null;
								
				logger.trace("Numero padres" + estructurasPadre.size());
				switch (estructurasPadre.size()) {
				case 0:
					logger.trace("case 0 Return null");
					return null;
				case 1:
				
					logger.trace("Caso con un nodo padre");
					nodoCopia.setNodoPadre(((Proyecto) nodoCopia)
							.getEstructuras().get(0));
					padreX = getNodoPadreConPeriodoRestrictivo(nodoCopia);
					logger.trace("Caso 1: return " + padreX);
					return padreX;

				case 2:
					logger.trace("Caso con dos nodos padre");
					
					logger.trace("-------------comprobando lado X-------------");
					nodoCopia.setNodoPadre(((Proyecto) nodoCopia)
							.getEstructuras().get(0));
					padreX = getNodoPadreConPeriodoRestrictivo(nodoCopia);
					
					logger.trace("-------------comprobando lado Y-------------");
					nodoCopia.setNodoPadre(((Proyecto) nodoCopia)
							.getEstructuras().get(1));
					padreY = getNodoPadreConPeriodoRestrictivo(nodoCopia);

					
					logger.trace("padreX::: " + padreX);
					logger.trace("padreY::: " + padreY);
					if (padreX != null && padreY != null) {
						logger.trace("case 2 Return nodoRestrictivo(): "
								+ getNodoMasRestrictivo(padreX, padreY));
						return getNodoMasRestrictivo(padreX, padreY);
					} else if (padreX != null && padreY == null) {
						logger.trace("case 2 Return padreX: " + padreX);
						return padreX;
					} else if (padreY != null && padreX == null) {
						logger.trace("case 2 Return padreY: " + padreY);
						return padreY;
					} else {
						logger.trace("case 2 Return null");
						return null;
					}
				}
			} else {
				if (nodoCopia instanceof Programa) {
					return isRelativoOrDefinido(nodoCopia) ? nodoCopia : nodoCopia;
				}
			}
		}
		return null;
	}

	public boolean isRelativoOrDefinido(Nodo nodo) {
		Periodo periodo = nodo.getPeriodo();

		if (periodo.getTipoPeriodo().equals(Periodo.PERIODO_RELATIVO)
				|| periodo.getTipoPeriodo().equals(Periodo.PERIODO_DEFINIDO)) {
			return true;
		}
		return false;
	}
	
	public boolean isDefinido(Nodo nodo){
		if(nodo.getPeriodo().getTipoPeriodo().equals(Integer.valueOf(Periodo.PERIODO_DEFINIDO))){
			return true;
		}
		return false;
	}

	public Nodo getNodoMasRestrictivo(Nodo nodoX, Nodo nodoY) {
		boolean esRelativoX = nodoX.getPeriodo().getTipoPeriodo()
				.equals(Periodo.PERIODO_RELATIVO) ? true : false;
		boolean esRelativoY = nodoY.getPeriodo().getTipoPeriodo()
				.equals(Periodo.PERIODO_RELATIVO) ? true : false;
		boolean esDefinidoX = nodoX.getPeriodo().getTipoPeriodo()
				.equals(Periodo.PERIODO_DEFINIDO) ? true : false;
		boolean esDefinidoY = nodoY.getPeriodo().getTipoPeriodo()
				.equals(Periodo.PERIODO_DEFINIDO) ? true : false;
		boolean esIndefinidoX= nodoX.getPeriodo().getTipoPeriodo()
				.equals(Periodo.PERIODO_INDEFINIDO) ? true:false;
		boolean esIndefinidoY= nodoY.getPeriodo().getTipoPeriodo()
				.equals(Periodo.PERIODO_INDEFINIDO) ? true:false;

		Integer duracionX = null;
		Integer duracionY = null;

		if (esRelativoX || esDefinidoX) {
			duracionX = nodoX.getPeriodo().getDuracionCalculado();
		}
		if (esRelativoY || esDefinidoY) {
			duracionY = nodoY.getPeriodo().getDuracionCalculado();
		}

		if (esRelativoX && esRelativoY) {
			if (duracionX.equals(duracionY)) {
				return nodoX;
			} else if (duracionX < duracionY) {
				return nodoX;
			} else {
				return nodoY;
			}
		}

		if (esDefinidoX && esDefinidoY) {
			Long fechaInicioX = nodoX.getPeriodo().getFechaInicio().getTime();
			Long fechaInicioY = nodoY.getPeriodo().getFechaInicio().getTime();
			Long fechaFinX = nodoX.getPeriodo().getFechaFinCalculada()
					.getTime();
			Long fechaFinY = nodoY.getPeriodo().getFechaFinCalculada()
					.getTime();

			if (fechaInicioX.equals(fechaInicioY) && fechaFinX.equals(fechaFinY)) {
				// periodos iguales
				return nodoX;
			}
			if (fechaFinX <= fechaInicioY || fechaFinY <= fechaInicioX) {
				// periodos sin interseccion
				return new Proyecto();
			}
			if (fechaInicioX < fechaInicioY && fechaFinX < fechaFinY) {
				// periodo interseccion: X termina primero que Y
				Periodo periodo = new Periodo();
				Proyecto proyecto = new Proyecto();

				periodo.setFechaInicio(nodoY.getPeriodo().getFechaInicio());
				periodo.setFechaFin(nodoX.getPeriodo().getFechaFinCalculada());
				proyecto.setPeriodo(periodo);
				return proyecto;
			}
			if (fechaInicioY < fechaInicioX && fechaFinY < fechaFinX) {
				// periodo interseccion: Y termina primero que X
				Periodo periodo = new Periodo();
				Proyecto proyecto = new Proyecto();

				periodo.setFechaInicio(nodoX.getPeriodo().getFechaInicio());
				periodo.setFechaFin(nodoY.getPeriodo().getFechaFinCalculada());
				proyecto.setPeriodo(periodo);
				return proyecto;
			}
			if (fechaInicioX < fechaInicioY && fechaFinX > fechaFinY) {
				// periodo interseccion: Y subconjunto de X
				return nodoY;
			}
			if (fechaInicioY < fechaInicioX && fechaFinY > fechaFinX) {
				// periodo interseccion: X subconjunto de Y
				return nodoX;
			}
		}

		if (duracionX != null && duracionY != null) {
			if (duracionX < duracionY) {
				return nodoX;
			} else if (duracionY < duracionX) {
				return nodoY;
			} else {
				if (esRelativoX) {
					return nodoX;
				} else {
					return nodoY;
				}
			}
		}
		if(esIndefinidoX || esIndefinidoY){
			if(esIndefinidoX && esDefinidoY){
				return nodoY;
			}
		}
		
		return null;
	}
} 