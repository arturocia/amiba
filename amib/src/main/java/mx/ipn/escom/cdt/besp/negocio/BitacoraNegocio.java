package mx.ipn.escom.cdt.besp.negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.BitacoraDao;
import mx.ipn.escom.cdt.besp.modelo.Bitacora;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.TipoRegistro;

import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("bitacoraNegocio")
public class BitacoraNegocio {

	private BitacoraDao bitacoraDao;
	private ProyectoNegocio proyectoNegocio;
	private ProgramaNegocio programaNegocio;

	@Transactional
	public List<Bitacora> findAll() {
		return bitacoraDao.findAll();
	}

	@Transactional
	public Bitacora findById(Integer id) {
		return bitacoraDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Bitacora save(Bitacora entidad) {
		return bitacoraDao.save(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Bitacora entidad) {
		bitacoraDao.delete(entidad);
	}

	@Transactional
	public List<Bitacora> findByExample(Bitacora bitacora) {
		return bitacoraDao.findByExample(bitacora);
	}

	public int getnumeroDeBitacoras() {
		List<Bitacora> listaBitacoras = findAll();
		if (listaBitacoras == null) {
			return 1;
		} else {
			return listaBitacoras.size() + 1;
		}
	}

	/**
	 * Se obtienen las restricciones pertenecientes al proyecto
	 * 
	 * @param ID
	 *            del proyecto
	 * @return Una lista de tipo bitacora con todas las restricciones del
	 *         proyecto
	 */
	public List<Bitacora> getRestriccionesProyecto(int idProyecto) {
		// Creo listas temporales paara poder obtenes las restricciones y los
		// registros en la bitacora del proyecto
		List<Bitacora> restriccionesProyecto = new ArrayList<Bitacora>();
		List<Bitacora> registrosBitacora;

		// Creo un objeto de tipo proyecto
		Proyecto proyecto;

		// Obtengo los datos del proyecto mediante su ID
		proyecto = proyectoNegocio.findById(idProyecto);

		// Obtengo todos los registros de la bitacora que corresponden al
		// proyecto
		registrosBitacora = proyecto.getRegbitacora();

		// Por cada registro obtenido
		for (Bitacora registro : registrosBitacora) {
			// Si el tipo de registro es iguala una restriccion
			if (registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)
					|| registro.getIdTipoRegistro().equals(
							TipoRegistro.OBSERVACION)) {
				// Agrego el registro a la lista de restricciones
				restriccionesProyecto.add(registro);
			}
		}

		// Retorno la lista que contiene las restricciones del proyecto
		return restriccionesProyecto;
	}

	/**
	 * Se obtienen los mensajes relacionados a la restriccion del proyecto
	 * 
	 * @param ID
	 *            de la restriccion
	 * @return Una lista conteniendo todos los mensajes pertenecientes a la
	 *         restriccion
	 */
	@Transactional
	public List<Bitacora> getMensajesRestriccionBitacora(int idRestriccion) {
		// Creo listas temporales para poder obtener los registros de la
		// bitacora y los mensajes de la restriccion
		List<Bitacora> mensajesRestriccion = new ArrayList<Bitacora>();
		List<Bitacora> registrosBitacora;

		// Obtengo todos los registros de la bitacora
		registrosBitacora = this.findAll();

		// Por cada registro en la bitacora
		for (Bitacora registro : registrosBitacora) {
			// Si el tipo de registro es diferente de una restriccion y el ID
			// del registro de referencia es igual al ID de la restriccion
			if (!registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)
					&& !registro.getIdTipoRegistro().equals(
							TipoRegistro.OBSERVACION)
					&& registro.getIdRegistroReferencia().equals(idRestriccion)) {
				// Agrego el registro en los mensajes de la restriccion
				mensajesRestriccion.add(registro);
			}
		}

		// Retorno la lista que contiene los mensajes de la restriccion
		return mensajesRestriccion;
	}

	/**
	 * Se obtienen los registros de la bitacora y se ordenan por su relevancia
	 * 
	 * @param Una
	 *            lista bidimensional de tipo bitacora con los registros
	 * @return Una lista bidimensioanl de tipo bitacora con los registros
	 *         ordenados
	 */
	public List<List<Bitacora>> ordenaRegistrosBitacora(
			List<List<Bitacora>> registrosBitacoraProyecto) {
		// Creo una lista bidimensional temporal para almacenar los registros
		// ordenados
		List<List<Bitacora>> registrosOrdenadosBitacoraProyecto = new ArrayList<List<Bitacora>>();

		// Creo una lista temporal para poder manejar todos los mensajes de una
		// restriccion
		List<Bitacora> coleccionRestriccion;

		// Creo listas bidimensionales temporales para poder almanecnar las
		// observaciones restricciones atendidas, turnadas y no atendidas
		List<List<Bitacora>> restriccionesNoAtendidas = new ArrayList<List<Bitacora>>();
		List<List<Bitacora>> restriccionesTurnadas = new ArrayList<List<Bitacora>>();
		List<List<Bitacora>> restriccionesAtendidas = new ArrayList<List<Bitacora>>();
		List<List<Bitacora>> observaciones = new ArrayList<List<Bitacora>>();

		// Por cada lista que contiene los mensajes de la restriccion dentro de
		// los registros de la bitacora
		for (List<Bitacora> restriccion : registrosBitacoraProyecto) {
			// Limpio la lista temporal para poder manejar los mesajes de la
			// restriccion
			coleccionRestriccion = new ArrayList<Bitacora>();

			// Por cada mensaje dentro de los mensajes en la restriccion
			for (Bitacora mensaje : restriccion) {
				// Agrego el mensaje a la lista temporal
				coleccionRestriccion.add(mensaje);

				// Si es el ultimo mensaje de la restriccion
				if ((restriccion.size() - 1) == restriccion.indexOf(mensaje)) {
					// Si es una restriccion o atencion del director
					if (mensaje.getIdTipoRegistro().equals(
							TipoRegistro.RESTRICCION)
							|| mensaje.getIdTipoRegistro().equals(
									TipoRegistro.ATENCION_DIRECTOR)) {
						// Agrego la restriccion y sus mensajes en las
						// restricciones no atendidas
						restriccionesNoAtendidas.add(coleccionRestriccion);
					}
					// Si es una restriccion turnada
					if (mensaje.getIdTipoRegistro().equals(
							TipoRegistro.DERIVACION)) {
						// Agrego la restriccion y sus mensajes en las
						// restricciones turnadas
						restriccionesTurnadas.add(coleccionRestriccion);
					}
					// Si es una atencion inmediata o atencion turnada
					if (mensaje.getIdTipoRegistro().equals(
							TipoRegistro.ATENCION_INMEDIATA)
							|| mensaje.getIdTipoRegistro().equals(
									TipoRegistro.ATENCION_TURNADA)) {
						// Agrego la restriccion y sus mensajes en las
						// restricciones no atendidas
						restriccionesAtendidas.add(coleccionRestriccion);
					}
					// Si es una observacion
					if (mensaje.getIdTipoRegistro().equals(
							TipoRegistro.OBSERVACION)) {
						// Agrego la restriccion y sus mensajes en las
						// restricciones no atendidas
						observaciones.add(coleccionRestriccion);
					}
				}
			}
		}

		// En esta parte ordeno todos por fecha
		Collections.sort(observaciones, new Comparator<List<Bitacora>>() {
			public int compare(List<Bitacora> b1, List<Bitacora> b2) {
				return b1.get(b1.size() - 1).getFechaRegistro()
						.compareTo(b2.get(b2.size() - 1).getFechaRegistro());
			}
		});

		Collections.reverse(observaciones);

		Collections.sort(restriccionesNoAtendidas,
				new Comparator<List<Bitacora>>() {
					public int compare(List<Bitacora> b1, List<Bitacora> b2) {
						return b1
								.get(b1.size() - 1)
								.getFechaRegistro()
								.compareTo(
										b2.get(b2.size() - 1)
												.getFechaRegistro());
					}
				});

		Collections.reverse(restriccionesNoAtendidas);

		Collections.sort(restriccionesTurnadas,
				new Comparator<List<Bitacora>>() {
					public int compare(List<Bitacora> b1, List<Bitacora> b2) {
						return b1
								.get(b1.size() - 1)
								.getFechaRegistro()
								.compareTo(
										b2.get(b2.size() - 1)
												.getFechaRegistro());
					}
				});

		Collections.reverse(restriccionesTurnadas);

		Collections.sort(restriccionesAtendidas,
				new Comparator<List<Bitacora>>() {
					public int compare(List<Bitacora> b1, List<Bitacora> b2) {
						return b1
								.get(b1.size() - 1)
								.getFechaRegistro()
								.compareTo(
										b2.get(b2.size() - 1)
												.getFechaRegistro());
					}
				});

		Collections.reverse(restriccionesAtendidas);

		// En esta parte voy agregando las restricciones y sus contenidos por
		// relevancia

		// Por cada restriccion no atendida
		for (List<Bitacora> observacion : observaciones) {
			// La agrego a la lista de registros ordenados
			registrosOrdenadosBitacoraProyecto.add(observacion);
		}

		// Por cada restriccion no atendida
		for (List<Bitacora> restriccion : restriccionesNoAtendidas) {
			// La agrego a la lista de registros ordenados
			registrosOrdenadosBitacoraProyecto.add(restriccion);
		}

		// Porcada restriccion turnada
		for (List<Bitacora> restriccion : restriccionesTurnadas) {
			// La agrego a la lista de registros ordenados
			registrosOrdenadosBitacoraProyecto.add(restriccion);
		}

		// Por cada restriccion atendida
		for (List<Bitacora> restriccion : restriccionesAtendidas) {
			// La agrego a la lista de registros ordenados
			registrosOrdenadosBitacoraProyecto.add(restriccion);
		}

		// Retorno la lista bidimensional con todas las restricciones ordenadas
		// por relevancia
		return registrosOrdenadosBitacoraProyecto;
	}

	/**
	 * Se obtienen los registros de la bitacora por proyecto
	 * 
	 * @param ID
	 *            del proyecto
	 * @return Una lista bidimensional con todos los registros del proyecto
	 */
	@Transactional
	public List<List<Bitacora>> getRegistrosBitacoraProyecto(int idProyecto) {
		// Creo listas bidimensionales temporales para almacenar los registros
		// del proyecto y despues ordenarlos
		List<List<Bitacora>> registrosBitacoraProyecto = new ArrayList<List<Bitacora>>();
		List<List<Bitacora>> registrosOrdenadosBitacoraProyecto;

		// Creo listas temporales para obtener las restricciones, mensajes y
		// datos de las restricciones del proyecto
		List<Bitacora> restriccionesProyecto;
		List<Bitacora> mensajesRestriccion;
		List<Bitacora> coleccionRestriccion;

		// Obtengo las restricciones del proyecto
		restriccionesProyecto = this.getRestriccionesProyecto(idProyecto);

		// Por cada restriccion en el proyecto
		for (Bitacora restriccion : restriccionesProyecto) {
			// Limpio la lista temporal donde agrego los mensajes de cada
			// restriccion
			coleccionRestriccion = new ArrayList<Bitacora>();

			// Obtengo los mensajes de la restriccion del proyecto
			mensajesRestriccion = this
					.getMensajesRestriccionBitacora(restriccion.getIdRegistro());

			// Agrego la restriccion a la lista donde se contienen los mensajes
			// de la restriccion
			coleccionRestriccion.add(restriccion);

			// Por cada mensaje en los mensajes de la restriccion
			for (Bitacora mensaje : mensajesRestriccion) {
				// Lo agrego a la lista donde se contienen los mensajes de la
				// restriccion
				coleccionRestriccion.add(mensaje);
			}

			// Agrego la lista a los registros de la bitacora del proyecto
			registrosBitacoraProyecto.add(coleccionRestriccion);

		}
		// Ordeno los registros de la bitacora del proyecto
		registrosOrdenadosBitacoraProyecto = this
				.ordenaRegistrosBitacora(registrosBitacoraProyecto);

		// Retotno la lista bidimensional con todos los registros del proyecto
		return registrosOrdenadosBitacoraProyecto;
	}

	/**
	 * Se obtienen todas las restricciones de los programas pertenecientes a un
	 * programa
	 * 
	 * @param ID
	 *            del programa
	 * @return Se retorna una lista bidimensional con todos los registros de la
	 *         bitacora del programa
	 */
	@Transactional
	public List<List<Bitacora>> getRegistrosBitacoraPrograma(int idPrograma) {
		// Creo un objeto de tipo programa
		Programa programa;

		// Creo una lista temporal para poder manejar las estructuras y los
		// proyectos
		List<Estructura> estructuras;
		List<Proyecto> proyectos = new ArrayList<Proyecto>();

		// Creo listas temporales para poder manejar los registros en bitacora
		// de los proyectos y del programa
		List<List<Bitacora>> registrosBitacoraPrograma = new ArrayList<List<Bitacora>>();
		List<List<Bitacora>> registrosBitacoraProyecto;

		// Obtengo el programa mediante su ID
		programa = programaNegocio.findById(idPrograma);

		// Obtengo todas las estructuras del programa
		estructuras = programa.getEstructurasTodas();

		// Por cada estructura de todas las estructuras obtenidas
		for (Estructura estructura : estructuras) {
			// Agrego todos sus proyectos a la lista de proyectos
			proyectos.addAll(estructura.getProyectos());
		}

		// Por cada proyecto obtenido
		for (Proyecto proyecto : proyectos) {
			// Obtengo sus registros que tiene en la bitacora
			registrosBitacoraProyecto = this
					.getRegistrosBitacoraProyecto(proyecto.getIdProyecto());

			// Por cada restriccion dentro de los registros
			for (List<Bitacora> restriccion : registrosBitacoraProyecto) {
				// Agrego la restriccion a los registros de la bitacora
				registrosBitacoraPrograma.add(restriccion);
			}
		}

		// Retorno todos los registros del programa dentro de la bitacora ya
		// ordenados
		return this.ordenaRegistrosBitacora(registrosBitacoraPrograma);
	}

	public ProyectoNegocio getProyectoNegocio() {
		return proyectoNegocio;
	}

	public void setProyectoNegocio(ProyectoNegocio proyectoNegocio) {
		this.proyectoNegocio = proyectoNegocio;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public BitacoraDao getBitacoraDao() {
		return bitacoraDao;
	}

	public void setBitacoraDao(BitacoraDao bitacoraDao) {
		this.bitacoraDao = bitacoraDao;
	}

}
