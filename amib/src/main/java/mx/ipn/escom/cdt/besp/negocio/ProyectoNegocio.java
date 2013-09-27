package mx.ipn.escom.cdt.besp.negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import mx.ipn.escom.cdt.besp.dao.PeriodoDao;
import mx.ipn.escom.cdt.besp.dao.ProyectoDao;
import mx.ipn.escom.cdt.besp.dao.ProyectoEjeDao;
import mx.ipn.escom.cdt.besp.dao.ProyectoEstructuraDao;
import mx.ipn.escom.cdt.besp.dao.ProyectoTemaDao;
import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.AvanceFinanciero;
import mx.ipn.escom.cdt.besp.modelo.EjeTematico;
import mx.ipn.escom.cdt.besp.modelo.Estado;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Indicador;
import mx.ipn.escom.cdt.besp.modelo.IndicadorFinanciero;
import mx.ipn.escom.cdt.besp.modelo.PerfilUsuario;
import mx.ipn.escom.cdt.besp.modelo.Programa;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.modelo.ProyectoEje;
import mx.ipn.escom.cdt.besp.modelo.ProyectoEstructura;
import mx.ipn.escom.cdt.besp.modelo.ProyectoTema;
import mx.ipn.escom.cdt.besp.modelo.TemaTransversal;
import mx.ipn.escom.cdt.besp.modelo.Usuario;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Named("proyectoNegocio")
public class ProyectoNegocio {

	private ProyectoDao proyectoDao;

	private ProyectoEstructuraDao proyectoEstructuraDao;
	private ProyectoEjeDao proyectoEjeDao;
	private ProyectoTemaDao proyectoTemaDao;
	private PeriodoDao periodoDao;

	private Usuario usuario;

	private UsuarioNegocio usuarioNegocio;
	private ProgramaNegocio programaNegocio;

	private TemaTransversalNegocio temaTransversalNegocio;
	private EjeTematicoNegocio ejeTematicoNegocio;

	private EstructuraNegocio estructuraNegocio;
	
	private static final int PESO_MAXIMO = 100;

	private Logger logger = Logger.getLogger(ProyectoNegocio.class);

	@Transactional
	public List<Proyecto> findAll() {
		return proyectoDao.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	public Proyecto save(Proyecto entidad) {
		return proyectoDao.save(entidad);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Proyecto update(Proyecto entity){
		return proyectoDao.update(entity);
	}

	/**
	 * 
	 * Los métodos utilizado por el controller de Gestionar proyectos
	 * Preregistrados: -findById -delete -findByExample
	 */
	@Transactional
	public Proyecto findById(Integer id) {
		return proyectoDao.findById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public void delete(Proyecto entidad) {
		proyectoDao.delete(entidad);
	}

	@Transactional
	public List<Proyecto> findByExample(Proyecto proyecto) {
		return proyectoDao.findByExample(proyecto);
	}

	/**
	 * Este método valida que el costo total sea mayor a cero
	 * 
	 * @param proyecto
	 * @return false si el costo total es menor a cero true si el monto
	 *         solicitado es mayor a cero
	 */

	@Transactional
	public Boolean validaMayorAcero(Proyecto proyecto) {
		if (proyecto.getCostoTotal() < 0) {
			return false;
		}
		return true;
	}

	@Transactional
	public Boolean existe(String nombre) {
		Proyecto proyectoEjemplo = new Proyecto();
		List<Proyecto> proyectos = null;
		proyectoEjemplo.setNombre(nombre);
		proyectos = findByExample(proyectoEjemplo);
		if (proyectos != null && proyectos.size() > 0) {
			return true;
		}
		return false;
	}

	@Transactional
	public List<Proyecto> getProyectos(List<Integer> idProgramas,
			List<Integer> idEjes, List<Integer> idTemas, List<Integer> idAreas) {
		List<Proyecto> proyectos = this.proyectoDao.getProyectos(idProgramas,
				idEjes, idTemas, idAreas);
		return proyectos;
	}

	@Transactional
	public List<Proyecto> getProyectosAAprobar(Usuario coordinador) {
		List<Proyecto> proyectos = null;
		proyectos = new ArrayList<Proyecto>();
		for (Usuario lider : coordinador.getSuperior()) {
			proyectos.addAll(lider.getProyectosEnRevision());
		}
		return proyectos;
	}

	public ProyectoDao getProyectoDao() {
		return proyectoDao;
	}

	public void setProyectoDao(ProyectoDao proyectoDao) {
		this.proyectoDao = proyectoDao;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}

	public TemaTransversalNegocio getTemaTransversalNegocio() {
		return temaTransversalNegocio;
	}

	public void setTemaTransversalNegocio(
			TemaTransversalNegocio temaTransversalNegocio) {
		this.temaTransversalNegocio = temaTransversalNegocio;
	}

	public EjeTematicoNegocio getEjeTematicoNegocio() {
		return ejeTematicoNegocio;
	}

	public void setEjeTematicoNegocio(EjeTematicoNegocio ejeTematicoNegocio) {
		this.ejeTematicoNegocio = ejeTematicoNegocio;
	}

	public EstructuraNegocio getEstructuraNegocio() {
		return estructuraNegocio;
	}

	public void setEstructuraNegocio(EstructuraNegocio estructuraNegocio) {
		this.estructuraNegocio = estructuraNegocio;
	}

	public ProyectoEstructuraDao getProyectoEstructuraDao() {
		return proyectoEstructuraDao;
	}

	public void setProyectoEstructuraDao(
			ProyectoEstructuraDao proyectoEstructuraDao) {
		this.proyectoEstructuraDao = proyectoEstructuraDao;
	}

	public ProyectoEjeDao getProyectoEjeDao() {
		return proyectoEjeDao;
	}

	public void setProyectoEjeDao(ProyectoEjeDao proyectoEjeDao) {
		this.proyectoEjeDao = proyectoEjeDao;
	}

	public ProyectoTemaDao getProyectoTemaDao() {
		return proyectoTemaDao;
	}

	public void setProyectoTemaDao(ProyectoTemaDao proyectoTemaDao) {
		this.proyectoTemaDao = proyectoTemaDao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setPeriodoDao(PeriodoDao periodoDao) {
		this.periodoDao = periodoDao;
	}

	public PeriodoDao getPeriodoDao() {
		return periodoDao;
	}

	public ProgramaNegocio getProgramaNegocio() {
		return programaNegocio;
	}

	public void setProgramaNegocio(ProgramaNegocio programaNegocio) {
		this.programaNegocio = programaNegocio;
	}

	/**
	 * Valida la RN74
	 * 
	 * @return falso si alguna de las acciones del proyecto no tiene indicadores
	 *         completos; verdadero si todas las acciones del proyecto estan
	 *         completas
	 */

	public Boolean validaAccionesCompletas(Proyecto proy) {

		List<Accion> listaAcc = proy.getAcciones();
		for (Accion acc : listaAcc) {
			if (!acc.getAccionCompleta()) {
				return false;
			}
		}
		return true;
	}

	/***
	 * Busca proyectos por ID de estado
	 * 
	 * @param id_estado
	 * @return <b>List<Proyecto></b> Lista de proyectos que tienen como id
	 *         <b>id_estado</b>
	 */
	@Transactional
	public List<Proyecto> buscarProyecto(Integer idEstado) {
		Proyecto muestra = new Proyecto();
		muestra.setIdEstado(idEstado);

		return findByExample(muestra);
	}

	@Transactional
	public Proyecto asociarResponsable(Proyecto proyecto, Usuario usuario) {

		proyecto.setIdEstado(Estado.EDICION);
		proyecto.setIdResponsable(usuario.getIdUsuario());

		save(proyecto);
		return proyecto;
	}

	/**
	 * 
	 * @param proyecto
	 * @return Validacion RN43 Alineacion de proyecto -Con una EP1N de programa
	 *         sectorial -Con una EP1N de algun programa1N -Con una EP1N de
	 *         programa sectorial y programa1N
	 */
	@Transactional
	public boolean validaProyectoAlineado(Proyecto proyecto) {
		List<Estructura> estructuras;
		estructuras = proyecto.getEstructuras();
		Boolean ep1n1;
		Boolean ep1n2;
		logger.trace("Ya que el amor, es musica ligera");
		if (estructuras.size() == 1) {
			if (estructuras.get(0).getPrograma().getSectorial()) {
				logger.trace("alineado con programa sectorial");
			} else {
				logger.trace("alineado con un programa 1n");
			}
			return true;

		} else if (estructuras.size() == 2) {
			ep1n1 = estructuras.get(0).getPrograma().getSectorial();
			ep1n2 = estructuras.get(1).getPrograma().getSectorial();
			if ((ep1n1 && !ep1n2)
					|| (!ep1n1 && ep1n2)) {
				logger.trace("alineado con programa sectorial y un programa 1n");
				return true;
			} else {
				logger.trace("no se puede alinear");
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Se asume que proyecto tiene sus estructuras relacionadas
	 * 
	 * @param proyecto
	 * @return validacion RN33 valida si el nombre del proyecto no se repite con
	 *         algun proyecto hermano
	 * @Transactional public Boolean validaNombreUnico(Proyecto proyecto) {
	 *                List<Estructura> estructurasPadre; estructurasPadre =
	 *                proyecto.getEstructuras(); List<Proyecto>
	 *                proyectosHermanos = null;
	 * 
	 * 
	 * 
	 * 
	 * 
	 *                for (int n = 0; n < estructurasPadre.size(); n++) { if
	 *                (proyectosHermanos == null) { proyectosHermanos =
	 *                estructurasPadre.get(n).getProyectos(); } else {
	 *                proyectosHermanos
	 *                .addAll(estructurasPadre.get(n).getProyectos()); } }
	 * 
	 *                for (int n = 0; n < proyectosHermanos.size(); n++) { if
	 *                (proyecto.getNombre().matches(
	 *                proyectosHermanos.get(n).getNombre()) &&
	 *                !proyectosHermanos.get(n).getIdProyecto()
	 *                .equals(proyecto.getIdProyecto())) {
	 * 
	 *                /** Verifica si dos proyectos son hijos del mismo padre o
	 *                de los mismos padres, en caso de que no tengan el mismo
	 *                numero de padres da por hecho que no son hermanos. Si los
	 *                proyectos no son hermanos devuelve "true" de lo contrario
	 *                devuelve un "false"
	 * 
	 *                if (proyectosHermanos.get(n).getEstructuras().size() ==
	 *                estructurasPadre .size()) { for (int j = 0; j <
	 *                proyectosHermanos.get(n) .getEstructuras().size(); j++) {
	 *                for (int k = 0; k < estructurasPadre.size(); k++) { if
	 *                (proyectosHermanos.get(n).getEstructuras() .get(k).getId()
	 *                == estructurasPadre.get(k) .getId()) { return false; } } }
	 *                } else { return true; } } } return true;
	 * 
	 *                }
	 */

	/**
	 * 
	 * @param proyecto
	 * @return obtiene las estructuras padre, de ellas obtiene todos los
	 *         proyectos y de todos los proyectos verifica los nombres con el
	 *         actual, si hay alguno igual regresa false si no hay ninguno igual
	 *         regresa true
	 */
	@Transactional
	public Boolean validaNombreUnico(Proyecto proyecto) {

		List<Estructura> estructurasPadre;
		estructurasPadre = proyecto.getEstructuras();
		List<Proyecto> proyectosHermanos = new ArrayList<Proyecto>();

		for (Estructura estrucEjemplo : estructurasPadre) {
			proyectosHermanos.addAll(estrucEjemplo.getProyectos());
		}

		for (Proyecto proyEjemplo : proyectosHermanos) {
			if (proyEjemplo.getNombre().equals(proyecto.getNombre())
					&& !(proyecto.getId().equals(proyEjemplo.getId()))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Valida RN40
	 * 
	 * @param proyecto
	 * @return true en caso de que las siglas del proyecto no coincidan con las
	 *         siglas de un proyecto alineado con el mismo elemento (Proyectos
	 *         hermanos); Se da por hecho que el proyecto existe y pertenece a
	 *         un programa. Esto obedece a la regla de negocio 40 de proyecto.
	 */
	@Transactional
	public boolean validaSiglasUnicas(Proyecto proyecto) {
		List<Estructura> estructurasPadre;
		estructurasPadre = proyecto.getEstructuras();
		List<Proyecto> proyectosHermanos = null;

		for (Estructura estructura : estructurasPadre) {
			if (proyectosHermanos == null) {
				proyectosHermanos = estructura.getProyectos();
			} else {
				proyectosHermanos.addAll(estructura.getProyectos());
			}
		}

		for (Proyecto proyectos : proyectosHermanos) {
			if (proyectos.getSiglas().matches(proyecto.getSiglas())
					&& !proyectos.getIdProyecto().equals(
							proyecto.getIdProyecto())) {
				/**
				 * Verifica si dos proyectos son hijos del mismo padre o de los
				 * mismos padres, en caso de que no tengan el mismo numero de
				 * padres da por hecho que no son hermanos. Si los proyectos no
				 * son hermanos devuelve "true" de lo contrario devuelve un
				 * "false"
				 */
				if (proyectos.getEstructuras().size() == estructurasPadre
						.size()) {
					for (int j = 0; j < proyectos.getEstructuras().size(); j++) {
						for (int k = 0; k < estructurasPadre.size(); k++) {
							if (proyectos.getEstructuras().get(j).getId().equals(estructurasPadre
									.get(k).getId())) {
								return false;
							}
						}
					}
				} else {
					return true;
				}
			}
		}
		return true;
	}

	/**
	 * maradó maradó!!!!!!!
	 * 
	 * @param proyecto
	 * @return la lista de temas transversales que no estan asociados al
	 *         proyecto <code>proyecto</code>
	 */
	public List<TemaTransversal> traerTemas(Proyecto proyecto) {
		List<TemaTransversal> temaTransversales;
		List<TemaTransversal> temaTransversal;
		List<TemaTransversal> temaTercero;
		temaTransversal = null;
		temaTransversal = temaTransversalNegocio.findAll();
		temaTransversales = proyecto.getTemaTransversales();
		temaTercero = new ArrayList<TemaTransversal>();

		for (TemaTransversal transversal : temaTransversal) {
			if (!temaTransversales.contains(transversal)) {
				logger.trace("guardando " + transversal.getIdTema());
				temaTercero.add(transversal);
			}
		}

		return temaTercero;
	}

	public List<Proyecto> proyectosUsuario(int idUsuario) {

		List<Proyecto> listaProyectos;

		usuario = usuarioNegocio.findById(idUsuario);

		listaProyectos = usuario.getProyectos();

		return listaProyectos;
	}

	@Transactional
	public Proyecto alinearProyecto(Proyecto proyecto, Integer idEstructura,
			Integer idEstructuraSectorial, List<Integer> idEjes,
			List<Integer> idTemas) {

		Proyecto proyectoAlineado = null;
		ProyectoEstructura proyectoEstructura = null;
		ProyectoEje proyectoEje;
		ProyectoTema proyectoTema;
		Estructura estructura;
		List<ProyectoEstructura> proyectoEstructurasOriginales;
		List<ProyectoEje> proyectoEjesOriginales;
		List<ProyectoTema> proyectoTemasOriginales;
		List<Integer> idEjesOriginales;
		List<Integer> idTemasOriginales;

		proyectoEstructurasOriginales = proyecto.getProyectoEstructuras();
		proyectoEjesOriginales = proyecto.getProyectoEje();
		proyectoTemasOriginales = proyecto.getProyectoTema();

		if (proyectoEstructurasOriginales == null) {
			proyectoEstructurasOriginales = new ArrayList<ProyectoEstructura>();
		}
		if (proyectoEjesOriginales == null) {
			proyectoEjesOriginales = new ArrayList<ProyectoEje>();
		}
		if (proyectoTemasOriginales == null) {
			proyectoTemasOriginales = new ArrayList<ProyectoTema>();
		}
		idEjesOriginales = new ArrayList<Integer>();
		idTemasOriginales = new ArrayList<Integer>();

		for (ProyectoEstructura proyectoEstructuraOriginal : proyectoEstructurasOriginales) {
			if (!proyectoEstructuraOriginal.getId().getIdEstructura()
					.equals(idEstructura)
					&& !proyectoEstructuraOriginal.getId().getIdEstructura()
							.equals(idEstructuraSectorial)) {
				proyectoEstructuraDao.delete(proyectoEstructuraOriginal);
			}
		}

		for (ProyectoTema proyectoTemaOriginal : proyectoTemasOriginales) {
			idTemasOriginales.add(proyectoTemaOriginal.getProyectoTemaId()
					.getIdTema());
			if (!idTemas.contains(proyectoTemaOriginal.getProyectoTemaId()
					.getIdTema())) {
				proyectoTemaDao.delete(proyectoTemaOriginal);
			}
		}

		for (ProyectoEje proyectoEjeOriginal : proyectoEjesOriginales) {
			idEjesOriginales.add(proyectoEjeOriginal.getProyectoEjeId()
					.getIdEje());
			if (!idEjes.contains(proyectoEjeOriginal.getProyectoEjeId()
					.getIdEje())) {
				proyectoEjeDao.delete(proyectoEjeOriginal);
			}
		}

		proyecto.setIdEstado(Estado.EDICION);
		proyecto.setEstructuras(null);
		periodoDao.save(proyecto.getPeriodo());
		proyectoAlineado = update(proyecto);

		if (idEstructura != null) {
			estructura = estructuraNegocio.findById(idEstructura);
			proyectoEstructura = new ProyectoEstructura(
					estructura.getIdPrograma(), estructura.getIdEstructura(),
					proyecto.getIdProyecto());
			logger.trace("El proyecto estructura del que se trata "
					+ proyectoEstructura);

			if (!proyectoEstructurasOriginales.contains(proyectoEstructura)) {
				logger.trace("Guardando la relacion proyecto estructura");
				proyectoEstructuraDao.save(proyectoEstructura);
			}
		}

		if (idEstructuraSectorial != null) {
			estructura = estructuraNegocio.findById(idEstructuraSectorial);
			proyectoEstructura = new ProyectoEstructura(
					estructura.getIdPrograma(), estructura.getIdEstructura(),
					proyecto.getIdProyecto());
			logger.trace("El proyecto estructura sectorial del que se trata "
					+ proyectoEstructura);
			if (!proyectoEstructurasOriginales.contains(proyectoEstructura)) {
				logger.trace("Guardando la relacion proyecto estructura sectorial");
				proyectoEstructuraDao.save(proyectoEstructura);
			}
		}

		for (Integer idTema : idTemas) {
			if (!idTemasOriginales.contains(idTema)) {
				proyectoTema = new ProyectoTema(idTema, proyecto.getId());
				proyectoTemaDao.save(proyectoTema);
			}
		}

		for (Integer idEje : idEjes) {
			if (!idEjesOriginales.contains(idEje)) {
				proyectoEje = new ProyectoEje(idEje, proyecto.getId());
				proyectoEjeDao.save(proyectoEje);
			}
		}

		return proyectoAlineado;

	}

	/**
	 * rey pele
	 * 
	 * @param proyecto
	 * @return la lista de ejes tematicos que no estan asociados al proyecto
	 *         <code>proyecto</code>
	 */

	public List<EjeTematico> traerEjes(Proyecto proyecto) {
		List<EjeTematico> ejeTematicos;
		List<EjeTematico> ejeTematicos2;
		List<EjeTematico> ejeTematicos3;
		ejeTematicos2 = null;
		ejeTematicos2 = ejeTematicoNegocio.findAll();
		ejeTematicos = proyecto.getEjeTematicos();
		ejeTematicos3 = new ArrayList<EjeTematico>();

		for (EjeTematico ejeTematico : ejeTematicos2) {
			if (!ejeTematicos.contains(ejeTematico)) {
				ejeTematicos3.add(ejeTematico);
			}
		}

		return ejeTematicos3;
	}

	/**
	 * 
	 * @param proyecto
	 * @return En la posición 0 el camino de migajas del programa sectorial y en
	 *         la 1 el de otro programa. Un valor nulo en cualquiera de las
	 *         posiciones indica que hay alineación.
	 */
	@Transactional
	public List<String> generarCaminoMigajasProyecto(Proyecto proyecto) {
		String caminoMigajas = null;
		List<String> caminosMigajas = null;
		List<Estructura> estructuras;

		estructuras = proyecto.getEstructuras();
		caminosMigajas = new ArrayList<String>();
		caminosMigajas.add("");
		caminosMigajas.add("");
		logger.trace("Tiene " + proyecto.getEstructuras() + " padres");
		for (Estructura estructura : estructuras) {
			logger.trace("Uno de los padres es " + estructura);
			logger.trace("El programa es " + estructura.getPrograma());
			caminoMigajas = estructuraNegocio.getRuta(estructura.getId());
			logger.trace("Su camino de migajas es " + caminoMigajas);
			logger.trace("El programa es sectorial "
					+ estructura.getPrograma().getSectorial());
			if (estructura.getPrograma().getSectorial()) {
				logger.trace("El camino de migajas es " + caminosMigajas);
				logger.trace("Tienen " + caminosMigajas.size());
				caminosMigajas.set(0, caminoMigajas);
				logger.trace("Guardando en programa sectorial ");
			} else {
				caminosMigajas.set(1, caminoMigajas);
				logger.trace("Guardando en programa normal");
			}
		}
		logger.trace("este es mi amigo el rex" + caminosMigajas);
		return caminosMigajas;
	}

	/**
	 * localiza los proyectos relacionados de los coordinadores que pertenecen
	 * al area del Gerente firmado como <code>usuario</code>
	 * 
	 * @param usuario
	 * @return
	 */
	public List<Proyecto> traerProyectos(Usuario usuario) {
		List<Proyecto> proyectos = null;
		List<Usuario> usuarios = null;
		Usuario usuarioEjemplo = null;
		usuarioEjemplo = new Usuario();
		usuarioEjemplo.setIdArea(usuario.getIdArea());
		usuarioEjemplo.setIdPerfilUsuario(PerfilUsuario.COORDINADOR);
		usuarios = usuarioNegocio.findByExample(usuarioEjemplo);
		proyectos = new ArrayList<Proyecto>();
		for (Usuario usuario2 : usuarios) {
			proyectos.addAll(usuario2.getProyectos());
		}
		return proyectos;

	}

	/**
	 * Validación RN59 envio de proyecto para aprobación
	 * 
	 * @param proyecto
	 * @return True si -el proyecto esta bien alineado -contiene al menos una
	 *         accion -cada accion contiene al menos un indicador -la suma de
	 *         los indicadores de la accion es menor que 100 en caso de fallar
	 *         alguna validacion regresa FALSE
	 * 
	 */
	public Boolean validaEnvioAprobacion(Proyecto proyecto) {
		List<Accion> acciones = proyecto.getAcciones();
		List<Indicador> indicadores;
		int peso;

		if (validaProyectoAlineado(proyecto)) {

			if (!acciones.isEmpty()) {
				for (int i = 0; i < acciones.size(); i++) {
					indicadores = acciones.get(i).getIndicadores();
					if (indicadores.isEmpty()) {
						return false;

					} else {
						peso = 0;

						for (int j = 0; j < indicadores.size(); j++) {
							peso += indicadores.get(j).getPeso();
							if (peso > PESO_MAXIMO) {
								return false;
							}
						}
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Validación RN74 Presupuesto Ejercido
	 * 
	 * @param proyecto
	 * @return True si -el presupuesto asignado a este proyecto ha sito ejercido
	 *         en su totalidad en caso de que exista presupuesto regresa FALSE
	 */
	public Boolean validaPresupuestoEjercido(Proyecto proyecto) {
		List<IndicadorFinanciero> indicadores;
		logger.trace("*************En mi validacion**********");
		List<AvanceFinanciero> avanceFinancieroList;
		float suma = 0;
		indicadores = proyecto.getIndicadoresFinancieros();
		// sacar de cada lista los avances financieros usando indi y meterla en
		// avanceFinancieroList
		for (IndicadorFinanciero indi : indicadores) {
			avanceFinancieroList = indi.getAvancesFinancieros();
			// TODO revisar que siempre debe haber un monto aprobado
			if (avanceFinancieroList != null && avanceFinancieroList.size() > 0) {

				for (AvanceFinanciero avanceFinanciero2 : avanceFinancieroList) {
					suma += avanceFinanciero2.getMonto();
				}
				if (suma < indi.getMontoAprobado()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 
	 * @param ID
	 *            Usuario
	 * @return Una lista de proyectos que tienen observaciones y/o restricciones
	 *         pendientes.
	 */
	@Transactional
	public List<Proyecto> getProyectosConRestricciones(Integer idUsuario) {
		List<Proyecto> proyectosConPendientes = new ArrayList<Proyecto>();
		List<Proyecto> proyectosUsuario = new ArrayList<Proyecto>();
		List<Programa> programas;
		List<Estructura> estructuras;
		Usuario usuariouno;

		usuariouno = usuarioNegocio.findById(idUsuario);

		switch (usuariouno.getIdPerfilUsuario()) {
		case PerfilUsuario.COORDINADOR:
			proyectosUsuario = usuariouno.getProyectosOrdenadosFechaModificacion();
			break;

		case PerfilUsuario.GERENTE:
			programas = usuariouno.getProgramas();
			for (Programa programa : programas) {
				estructuras = new ArrayList<Estructura>();
				estructuras = programa.getEstructurasTodas();
				for (Estructura estructura : estructuras) {
					proyectosUsuario.addAll(estructura.getProyectos());
				}
			}
			break;

		case PerfilUsuario.SECRETARIO:
			programas = programaNegocio.findAll();
			for (Programa programa : programas) {
				estructuras = new ArrayList<Estructura>();
				estructuras = programa.getEstructurasTodas();
				for (Estructura estructura : estructuras) {
					proyectosUsuario.addAll(estructura.getProyectos());
				}
			}
			break;
		}

		for (Proyecto proyecto : proyectosUsuario) {
			switch (usuariouno.getIdPerfilUsuario()) {
			case PerfilUsuario.COORDINADOR:
				if (proyecto.getRestriccionesNoAtendidas() > 0
						|| proyecto.getObservaciones() > 0) {
					proyectosConPendientes.add(proyecto);
				}
				break;

			case PerfilUsuario.GERENTE:
				if (proyecto.getRestriccionesNoAtendidas() > 0
						|| proyecto.getRestriccionesTurnadas() > 0) {
					proyectosConPendientes.add(proyecto);
				}
				break;

			case PerfilUsuario.SECRETARIO:
				if (proyecto.getRestriccionesNoAtendidas() > 0) {
					proyectosConPendientes.add(proyecto);
				}
				break;
			default:
				break;
			}
		}

		return proyectosConPendientes;

	}

	/**
	 * 
	 * @param ID
	 *            Usuario
	 * @return Una lista de los proyectos pendientes que tiene el usuario
	 */
	@Transactional
	public List<Proyecto> getProyectosPendientes(Integer idUsuario) {
		List<Proyecto> proyectosPendientes = new ArrayList<Proyecto>();
		List<Proyecto> proyectosUsuario = new ArrayList<Proyecto>();
		List<Programa> programas;
		List<Estructura> estructuras;
		Usuario usuariodos;

		usuariodos = usuarioNegocio.findById(idUsuario);

		switch (usuariodos.getIdPerfilUsuario()) {
		case PerfilUsuario.COORDINADOR:
			proyectosUsuario = usuariodos.getProyectos();
			break;

		case PerfilUsuario.GERENTE:
			programas = usuariodos.getProgramas();
			for (Programa programa : programas) {
				estructuras = programa.getEstructurasTodas();
				for (Estructura estructura : estructuras) {
					proyectosUsuario.addAll(estructura.getProyectos());
				}
			}
			break;
		}

		for (Proyecto proyecto : proyectosUsuario) {
			switch (usuariodos.getIdPerfilUsuario()) {
			case PerfilUsuario.COORDINADOR:
				if (proyecto.getIdEstado().equals(Estado.REGISTRADO)) {
					proyectosPendientes.add(proyecto);
				}
				break;

			case PerfilUsuario.GERENTE:
				if (proyecto.getIdEstado().equals(Estado.REVISION)) {
					proyectosPendientes.add(proyecto);
				}
				break;
			default:
				break;
			}
		}

		Collections.sort(proyectosPendientes, new Comparator<Proyecto>() {
			public int compare(Proyecto p1, Proyecto p2) {
				if (p1.getIdEstado().equals(Estado.REGISTRADO)) {
					return p1.getFechaRegistro().compareTo(
							p2.getFechaRegistro());
				} else {
					return p1.getFechaModificacion().compareTo(
							p2.getFechaModificacion());
				}
			}
		});

		Collections.reverse(proyectosPendientes);

		return proyectosPendientes;
	}
}