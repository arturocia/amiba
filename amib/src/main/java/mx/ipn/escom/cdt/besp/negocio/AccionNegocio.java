package mx.ipn.escom.cdt.besp.negocio;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.AccionDao;
import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Indicador;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("accionNegocio")
public class AccionNegocio {

	private AccionDao accionDao;

	@Transactional
	public List<Accion> findAll() {
		return accionDao.findAll();
	}

	@Transactional
	public Accion findById(Integer id) {
		return accionDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Accion save(Accion entidad) {
		return accionDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public Accion update(Accion entidad) {
		return accionDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Accion entidad) {
		accionDao.delete(entidad);
	}

	public AccionDao getEstadoDao() {
		return accionDao;
	}

	public void setaccionDao(AccionDao accionDao) {
		this.accionDao = accionDao;
	}

	@Transactional
	public List<Accion> findByExample(Accion accion) {
		return accionDao.findByExample(accion);
	}

	/**
	 * Este método valida la regla de negocio 56 para acciones
	 * @param idAccion
	 * @return true si la accion tiene avances
	 * false si la accion no tiene avances
	 */
	@Transactional
	public Boolean validarAccionSinAvances(Integer idAccion){
		Accion accion;
		List<Indicador> indicadores;
		accion=findById(idAccion);
		indicadores = accion.getIndicadores();
		
		for(Indicador indicador : indicadores){
			if(indicador.getAvance()!=null && !indicador.getAvance().equals(0)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Este metodo valida la regla de negocio 33 para accion (obviamente,
	 * estamos en AccionNegocio)
	 * 
	 * @param accion
	 * @return falso si encuenta coincidencias entre una accion y sus hermanos
	 *         true si no encuentra coincidencias
	 */
	@Transactional
	public Boolean validaNombreUnicoAccion(Accion accion) {
		List<Accion> accionesHermanas;
		accionesHermanas = accion.getProyecto().getAcciones();

		for (Accion accionHermana : accionesHermanas) {
			if (accion.getNombre().matches(accionHermana.getNombre())
					&& (!accionHermana.getIdAccion().equals(
							accion.getIdAccion()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Este metodo valida la regla de negocio 7 para accion
	 * 
	 * @param la
	 *            accion a modificar/eliminar
	 * @return false si el periodo no es valido, true si el periodo es valido
	 */
	@Transactional
	public Boolean validaPeriodoAccion(Accion acc) {

		Calendar fechaAuxFin = GregorianCalendar.getInstance();
		Calendar fechaAux = GregorianCalendar.getInstance();
		Calendar fechaAux2 = GregorianCalendar.getInstance();

		if (acc.getProyecto().getPeriodo().getFechaInicio() != null
				&& acc.getProyecto().getPeriodo().getFechaFin() != null) {
			if (acc.getPeriodo().getFechaInicio() != null
					&& acc.getPeriodo().getFechaFin() != null) {
				if (acc.getPeriodo()
						.getFechaInicio()
						.compareTo(
								acc.getProyecto().getPeriodo().getFechaInicio()) < 0) {
					return false;
				}
				if (acc.getPeriodo()
						.getFechaFin()
						.compareTo(acc.getProyecto().getPeriodo().getFechaFin()) > 0) {
					return false;
				}
			}
			if (acc.getPeriodo().getFechaInicio() != null
					&& acc.getPeriodo().getDuracion() != null) {
				fechaAuxFin.setTime(acc.getPeriodo().getFechaInicio());
				fechaAux.setTime(acc.getProyecto().getPeriodo().getFechaFin());
				fechaAuxFin.roll(GregorianCalendar.DATE, acc.getPeriodo()
						.getDuracion());
				if (acc.getPeriodo()
						.getFechaInicio()
						.compareTo(
								acc.getProyecto().getPeriodo().getFechaInicio()) < 0) {
					return false;
				}
				if (fechaAux.compareTo(fechaAuxFin) > 0) {
					return false;
				}
			}

		}
		if (acc.getProyecto().getPeriodo().getFechaInicio() != null
				&& acc.getProyecto().getPeriodo().getDuracion() != null) {
			fechaAuxFin
					.setTime(acc.getProyecto().getPeriodo().getFechaInicio());
			fechaAuxFin.roll(GregorianCalendar.DATE, acc.getProyecto()
					.getPeriodo().getDuracion());

			if (acc.getPeriodo().getFechaInicio() != null
					&& acc.getPeriodo().getFechaFin() != null) {

				if (acc.getPeriodo()
						.getFechaInicio()
						.compareTo(
								acc.getProyecto().getPeriodo().getFechaInicio()) < 0) {
					return false;
				}
				fechaAux.setTime(acc.getPeriodo().getFechaFin());
				if (fechaAux.compareTo(fechaAuxFin) > 0) {
					return false;
				}
			}
			if (acc.getPeriodo().getFechaInicio() != null
					&& acc.getPeriodo().getDuracion() != null) {
				fechaAux2.setTime(acc.getPeriodo().getFechaInicio());
				fechaAux2.roll(GregorianCalendar.DATE, acc.getPeriodo()
						.getDuracion());
				if (acc.getPeriodo()
						.getFechaInicio()
						.compareTo(
								acc.getProyecto().getPeriodo().getFechaInicio()) < 0) {
					return false;
				}
				if (fechaAux2.compareTo(fechaAuxFin) > 0) {
					return false;
				}
			}
		}
		if (acc.getProyecto().getPeriodo().getDuracion() != null) {
			if (acc.getPeriodo().getFechaInicio() != null
					&& acc.getPeriodo().getFechaFin() != null) {
				return false;
			}
			if (acc.getPeriodo().getFechaInicio() != null) {
				return false;
			}
		}

		return true;
	}

}
