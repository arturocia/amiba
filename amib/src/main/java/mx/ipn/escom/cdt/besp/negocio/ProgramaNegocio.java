package mx.ipn.escom.cdt.besp.negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.ProgramaDao;
import mx.ipn.escom.cdt.besp.modelo.Estado;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nivel;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("programaNegocio")
public class ProgramaNegocio {
	private NivelNegocio nivelNegocio = null;
	private PeriodoNegocio periodoNegocio;

	private ProgramaDao programaDao;
	private Logger logger = Logger.getLogger(ProgramaNegocio.class);
	private static final int DIA = 24;
	private static final int HORA = 60;
	private static final int SEGUNDO = 60;
	private static final int DIAS = 1000;
	

	@Transactional
	public List<Programa> findAll() {
		return programaDao.findAll();
	}

	@Transactional
	public Programa findById(Integer id) {
		return programaDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Programa save(Programa entidad) {
		return programaDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Programa entidad) {
		programaDao.delete(entidad);
	}

	@Transactional
	public List<Programa> findByExample(Programa programa) {
		return programaDao.findByExample(programa);
	}

	@Transactional
	public boolean existe(Programa programa) {
		Programa programaEjemplo = new Programa();
		List<Programa> programanombre = null;
		programaEjemplo.setNombre(programa.getNombre());
		programanombre = findByExample(programaEjemplo);
		if (programanombre != null && programanombre.size() > 0) {
			return true;
		}

		Programa programaEjemplos = new Programa();
		List<Programa> programasiglas = null;
		programaEjemplos.setSiglas(programa.getSiglas());
		programasiglas = findByExample(programaEjemplos);
		if (programasiglas != null && programasiglas.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param programa
	 * @return verdadero si el programa es sectorial
	 */
	@Transactional
	public boolean validarSectorial(Programa programa) {
		if (programa.getSectorial())
		{
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param programa
	 * @return verdadero si se logra borrar el programa y los niveles que
	 *         pertenecen al programa si es que tiene.
	 */
	@Transactional
	public boolean borraPrograma(Programa programa) {
		Periodo periodo;
		List<Nivel> nivelesPrograma = programa.getNiveles();
		periodo = programa.getPeriodo();
		if (nivelesPrograma.isEmpty()) {
			delete(programa);
			periodoNegocio.delete(periodo);
			return true;
		}
		if (!nivelesPrograma.isEmpty()) {
			for (Nivel nivel : nivelesPrograma) {
				nivelNegocio.delete(nivel);
			}
			delete(programa);
			periodoNegocio.delete(periodo);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param programa
	 * @return verdadero si el programa tiene un proyecto en estado EJECUCION
	 */
	@Transactional
	public boolean validarProyectos(Programa programa) {
		List<Estructura> estructurasTodas;
		Estructura aux;
		Proyecto proyecto;
		estructurasTodas = programa.getEstructurasTodas();
		for (int i = 0; i < estructurasTodas.size(); i++) {
			aux = estructurasTodas.get(i);
			List<Proyecto> listaProyecto = aux.getProyectos();
			for (int j = 0; j < listaProyecto.size(); j++) {

				proyecto = listaProyecto.get(j);
				if (proyecto.getIdEstado().equals(Estado.EJECUCION)){
					return true;
				}
			}

		}

		return false;
	}

	/**
	 * 
	 * @return verifica si algun programa yta fue marcado comoprograma sectorial
	 */
	@Transactional
	public Boolean esSectorial() {
		List<Programa> programas = findAll();
		for (int i = 0; i < programas.size(); i++)
		{	
			if (programas.get(i).getSectorial()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param programa
	 * @return true en caso de que las fechas de inicio, fechas fin y las
	 *         duraciones de las estructuras de un programa este dentro de la
	 *         fecha inicial, final y duracion del programa.
	 */
	@Transactional
	public boolean evaluaPeriodo(Programa programa) {
		if (programa.getPeriodo() == null) {
			return false;
		}
		// Recabo los datos del programa
		Date fechaInicio = programa.getPeriodo().getFechaInicio();
		Date fechaFin = programa.getPeriodo().getFechaFin();
		int duracion;

		// Se hace una validacion de duracion para ver si contiene algo, si no
		// lo contiene se inicializa en 0
		if (programa.getPeriodo().getDuracion() != null) {
			duracion = programa.getPeriodo().getDuracion();
		} else {
			duracion = 0;
		}

		// Esta parte nos ayuda a checar el estado inicial del programa

		// Variables que nos ayudan durante las validaciones

		Calendar cal = Calendar.getInstance();

		// Checo si el programa no tiene una fecha asignada, duracion y fecha
		// fin

		if (fechaInicio == null && fechaFin == null && duracion == 0) {
			// Si no tiene definido alguno inmediatamente retorno un true
			// haciendo valido el periodo del programa
			return true;
		}

		// Si el programa tiene definido fecha de inicio, duracion o fecha fin
		// comparo con las estructuras

		// Antes recabo los datos necesarios so es que no tengo todos

		// Si solo tengo fecha inicio y fecha fin calculo la duracion
		if (fechaInicio != null && fechaFin != null && duracion == 0) {
			// Se hace el calculo de la duracion
			duracion = (int) ((fechaFin.getTime() - fechaInicio.getTime()) / (DIAS * SEGUNDO * HORA * DIA));
		}

		// Si tengo fecha inicio y duracion calculo fecha fin
		if (fechaInicio != null && fechaFin == null && duracion > 0) {
			cal.setTime(fechaInicio);
			cal.add(Calendar.DATE, duracion);
			fechaFin = cal.getTime();
		}

		// Esta parte nos ayuda a checar el estado calculado del programa

		// En este punto ya tendre al menos lo necesario para hacer las
		// validaciones con las estructuras

		// Creo una lista para recibir las estructuras del programa

		List<Estructura> estructuras = null;

		// Recibo las estructuras del programa

		estructuras = programa.getEstructuras();

		if (estructuras.isEmpty()) {
			return true;
		} else {
			// Checo que las fechas y periodo de las esctructuras esten dentro
			// del periodo del programa

			for (Estructura estructura : estructuras) {
				// Obtengo los datos de la estructura
				Date estructuraFechaInicio = estructura.getPeriodo()
						.getFechaInicio();
				Date estructuraFechaFin = estructura.getPeriodo().getFechaFin();
				int estructuraDuracion;

				// Se hace una validacion de duracion para ver si contiene algo,
				// si no lo contiene se inicializa en 0
				if (estructura.getPeriodo().getDuracion() != null) {
					estructuraDuracion = estructura.getPeriodo().getDuracion();
				} else {
					estructuraDuracion = 0;
				}

				// Si la estructura no tiene fecha inicio, fecha final o
				// duracion retorno true
				if (estructuraFechaInicio == null && estructuraFechaFin == null
						&& estructuraDuracion == 0) {
					// Si no tiene definido alguno inmediatamente retorno un
					// true haciendo valido el periodo del programa
					return true;
				}

				// Si la estructura solo tiene duracion lo comparo con la
				// duracion del programa
				if (estructuraFechaInicio == null && estructuraFechaFin == null
						&& estructuraDuracion > duracion) {
					return false;
				}

				// Si a estructura solo tiene fecha inicio y fecha fin calculo
				// duracion y comparo
				if (estructuraFechaInicio != null && estructuraFechaFin != null
						&& estructuraDuracion == 0) {
					estructuraDuracion = (int) ((estructuraFechaFin.getTime() - estructuraFechaInicio
							.getTime()) / (DIAS * SEGUNDO * HORA * DIA));
					if (estructuraDuracion > duracion) {
						return false;
					}
				}

				// Si la estructura tiene fecha inicio y duracion calculo la
				// fecha final y la comparo con la fecha final del programa

				if (estructuraFechaInicio != null && estructuraFechaFin == null
						&& estructuraDuracion > 0) {
					cal.setTime(estructuraFechaInicio);
					cal.add(Calendar.DATE, estructuraDuracion);
					estructuraFechaFin = cal.getTime();
					// Si la fecha final de la estructura es despues de la fecha
					// del programa entonces retornamos false
					if (fechaFin.compareTo(estructuraFechaFin) < 0) {
						return false;
					}
				}

			}
		}
		// Si al analizar ningun periodo de cada estructura fue mayor retorno un
		// true
		return true;
	}

	@Transactional
	public boolean tieneNiveles(Programa progs) {
		if (progs.getNiveles().size() <= 0) {
			return false;
		}
		return true;
	}

	@Transactional
	public Nodo findByIdEnArbol(Integer idNodoAEncontrar, Nodo raiz) {
		Nodo nodoEncontrado = null;
		logger.trace("Comparando " + idNodoAEncontrar + " con " + raiz);
		if (raiz == null) {
			return null;
		}

		if (idNodoAEncontrar.equals(raiz.getId())
				&& raiz.getClass().equals(Programa.class)) {
			return raiz;
		}
		if (raiz.getNodosHijo() != null) {
			for (Nodo nodo : raiz.getNodosHijo()) {
				nodoEncontrado = findByIdEnArbol(idNodoAEncontrar, nodo);
				if (nodoEncontrado != null) {
					return nodoEncontrado;
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @return La lista de todos los programas registrados, con el programa
	 *         sectorial (si existe) en la primera posición
	 */

	@Transactional
	public List<Programa> getProgramaSectorialYOtros() {
		Programa programaSectorial = null;
		List<Programa> programas;
		List<Programa> programasOrdenados;
		programas = findAll();
		programasOrdenados = new ArrayList<Programa>();

		for (Programa programa : programas) {
			if (programa.getSectorial()) {
				programaSectorial = programa;
			} else {
				programasOrdenados.add(programa);
			}
		}
		if (programaSectorial != null) {
			programasOrdenados.add(0, programaSectorial);
		}
		return programasOrdenados;
	}
	
	public ProgramaDao getProgramaDao() {
		return programaDao;
	}

	public void setProgramaDao(ProgramaDao programaDao) {
		this.programaDao = programaDao;
	}

	public NivelNegocio getNivelNegocio() {
		return nivelNegocio;
	}

	public void setNivelNegocio(NivelNegocio nivelNegocio) {
		this.nivelNegocio = nivelNegocio;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}
}