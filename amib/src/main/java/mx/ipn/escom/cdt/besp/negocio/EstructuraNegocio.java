package mx.ipn.escom.cdt.besp.negocio;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.EstructuraDao;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nivel;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("estructuraNegocio")
public class EstructuraNegocio {

	private EstructuraDao estructuraDao;
	private ProgramaNegocio programaNegocio;
	private PeriodoNegocio periodoNegocio;
	private Logger logger = Logger.getLogger(EstructuraNegocio.class);

	@Transactional
	public List<Estructura> findAll() {
		return estructuraDao.findAll();
	}

	@Transactional
	public Estructura findById(Integer id) {
		return estructuraDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public Estructura save(Estructura entidad) {
		return estructuraDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Estructura update(Estructura entidad) {
		return estructuraDao.update(entidad);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Estructura entidad) {
		estructuraDao.delete(entidad);
	}

	@Transactional
	public List<Estructura> findByExample(Estructura estructura) {
		return estructuraDao.findByExample(estructura);
	}

	@Transactional
	public Boolean existe(Estructura estructura) {
		Estructura estructuraEjemplo = new Estructura();
		List<Estructura> estructuras = null;

		estructuraEjemplo.setNombre(estructura.getNombre());
		estructuraEjemplo.setIdPadre(estructura.getIdPadre());

		estructuras = findByExample(estructuraEjemplo);

		if (estructuras != null && estructuras.size() > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public Boolean estaAsociado(Estructura estructura) {
		return estructura.getProyectos().size() > 0;
	}

	@Transactional
	public void recorrerArbol(Nodo raiz) {
		logger.trace("tomondo como raiz " + raiz);
		if (raiz == null) {
			return;

		}
		for (Nodo nodo : raiz.getNodosHijo()) {
			recorrerArbol(nodo);
		}

	}

	@Transactional
	public Nodo findByIdEnArbol(Integer idNodoAEncontrar, Nodo raiz) {
		Nodo nodoEncontrado = null;
		logger.trace("Comparando " + idNodoAEncontrar + " con " + raiz
				+ " para encontrar estructura");
		if (raiz == null) {
			return null;
		}

		if (idNodoAEncontrar.equals(raiz.getId())
				&& raiz.getClass().equals(Estructura.class)) {
			return raiz;
		}
		if (raiz.getNodosHijo() == null) {
			return null;
		}
		for (Nodo nodo : raiz.getNodosHijo()) {
			nodoEncontrado = findByIdEnArbol(idNodoAEncontrar, nodo);
			if (nodoEncontrado != null) {
				return nodoEncontrado;
			}
		}

		return null;
	}

	/**
	 * Este método valida la regla de negocio 36, la cual enuncia que si vamos
	 * a eliminar una estructura, debemos revisar si tiene proyectos asociados
	 * 
	 * @return
	 */
	@Transactional
	public Boolean validaEstructurasAlineadas(Nodo raiz) {
		Estructura estPrueba = null;
		Boolean alineado;
		if (raiz == null) {
			return false;
		}

		if (raiz.getClass().equals(Estructura.class)) {
			estPrueba = (Estructura) raiz;
		}

		if (estPrueba != null && estPrueba.getProyectos() != null && estPrueba.getProyectos().size() > 0) {
				return true;
		}

		if (raiz.getNodosHijo() != null) {
			for (Nodo nodo : raiz.getNodosHijo()) {
				alineado = validaEstructurasAlineadas(nodo);
				if (alineado) {
					return alineado;
				}
			}
		}

		return false;
	}

	/**
	 * Obtiene ka ruta para el camino de migajas
	 * 
	 * @param idEstructura
	 * @return
	 */
	@Transactional
	public String getRuta(Integer idEstructura) {
		Estructura estructura;
		String ruta = null;

		logger.trace("La estructura q c busca es " + idEstructura);
		estructura = findById(idEstructura);
		ruta = estructura.getNombre();

		for (; estructura.getEstructuraPadre() != null;) {
			ruta = estructura.getEstructuraPadre().getNombre() + " / " + ruta;
			estructura = estructura.getEstructuraPadre();
		}

		if (estructura.getEstructuraPadre() == null) {
			ruta = estructura.getPrograma().getNombre() + " / " + ruta;
		}

		return ruta;
	}

	/**
	 * Se da por hecho que la posición de la estructura esta puesta
	 * 
	 * @param e
	 * @return Si la profundidad de la estructura no es mayor a la maxima
	 *         profundidad de nivel del programa al que se pretede asociar
	 */
	@Transactional
	public Boolean validarNivel(Estructura e) {
		List<Nivel> aux = e.getPrograma().getNiveles();
		Integer nivelMaximo = aux.get(aux.size() - 1).getPosicion();

		logger.trace("Se compara el nivel de la estructura " + e.getIdNivel()
				+ " con el nivel maximo del programa " + nivelMaximo);
		if (e.getIdNivel() > nivelMaximo) {
			return false;
		}

		return true;
	}

	/**
	 * Valida la RN7 para estructura hacia arriba.
	 * 
	 * @param estructuraAValidar
	 * @param raiz
	 * @return Verdadero si el periodo de <code>estructuraAValidar</code> no
	 *         entra en conflicto con el de sus ancestros.
	 */

	// esta madre es la que tengo que hacer
	@Transactional
	public Boolean validaPeriodo(Nodo estructuraAValidar, Nodo raiz) {
		Boolean valido = true;
		Nodo nodoSuperior;
		Integer tipoPeriodoAValidar;
		Integer tipoPeriodoAncestro = Periodo.PERIODO_INDEFINIDO;

		if (((Estructura) estructuraAValidar).getIdPadre() == null) {
			nodoSuperior = programaNegocio.findByIdEnArbol(estructuraAValidar
					.getNodoPadre().getId(), raiz);
		} else {
			nodoSuperior = findByIdEnArbol(estructuraAValidar.getNodoPadre()
					.getId(), raiz);
		}

		tipoPeriodoAValidar = estructuraAValidar.getPeriodo().getTipoPeriodo();

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
		if (nodoSuperior!=null && nodoSuperior.getPeriodo() != null) {
			valido = periodoNegocio.determinaPeriodoValido(
					nodoSuperior.getPeriodo(), estructuraAValidar.getPeriodo());
		}
		return valido;
	}

	/**
	 * Valida la RN33 para estructura
	 * 
	 * @param estructura
	 * @return Si el nombre de la <code>estructura</code> es unico entre sus
	 *         hermanos. Se asume lo siguiente:
	 *         <ul>
	 *         <li>Que la propiedad <code>idPadre</code> tiene el id de la
	 *         estructura padre o nulo si su padre es el programa.
	 *         <li>Que tiene la propiedad <code>programa</code>.
	 *         <li>Que tiene la propiedad <code>estructuraPadre</code>.
	 *         </ul>
	 * 
	 */
	@Transactional
	public Boolean validaNombreUnico(Estructura estructura) {
		List<Estructura> estructurasHermanas;
		estructurasHermanas = estructura.getIdPadre() == null ? estructura
				.getPrograma().getEstructuras() : estructura
				.getEstructuraPadre().getEstructuras();
		for (Estructura estructuraHermana : estructurasHermanas) {
			if (estructura.getNombre().equals(estructuraHermana.getNombre())
					&& !estructuraHermana.getIdEstructura().equals(
							estructura.getIdEstructura())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Elimina la estructura y todos sus descendientes
	 * 
	 * @param estructura
	 */
	@Transactional
	public void eliminarEstructura(Estructura estructura) {
		logger.trace("Eliminando " + estructura);
		for (Estructura estructuraHijo : estructura.getEstructuras()) {
			eliminarEstructura(estructuraHijo);
		}
		delete(estructura);
	}

	/**
	 * Se valida la regla de negocio 7, en los descendientes del nodo
	 * <code>nodo</code>
	 * 
	 * @param nodo
	 * @return <code>true</code> si el periodo es valido
	 */
	public Boolean validaPeriodoHaciaAbajo(Nodo nodo) {

		if (nodo.getNodosHijo() == null) {
			return true;
		}
		for (Nodo nodoHijo : nodo.getNodosHijo()) {
			if (!periodoNegocio.determinaPeriodoValido(nodo.getPeriodo(),
					nodoHijo.getPeriodo())) {
				return false;
			}
			if (!validaPeriodoHaciaAbajo(nodoHijo)) {
				return false;
			}
		}
		return true;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	public EstructuraDao getEstructuraDao() {
		return estructuraDao;
	}

	public void setEstructuraDao(EstructuraDao estructuraDao) {
		this.estructuraDao = estructuraDao;
	}

	public PeriodoNegocio getPeriodoNegocio() {
		return periodoNegocio;
	}

	public void setPeriodoNegocio(PeriodoNegocio periodoNegocio) {
		this.periodoNegocio = periodoNegocio;
	}

}
