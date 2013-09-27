package mx.ipn.escom.cdt.besp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.Hibernate;

@DataTransferObject(javascript = "Proyecto")
@Entity
@Table(name = "t005_proyecto_tab")
public class Proyecto implements Serializable, Nodo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3122107817658948263L;
	private static final int PORCENTAJE = 100 ;
	private Integer idProyecto;
	private Integer idResponsable;
	private Integer idEstado;
	private Integer idPeriodo;
	private String siglas;
	private String nombre;
	private String resumen;
	private String objetivoGeneral;
	private Date fechaRegistro;
	private Date fechaModificacion;
	private Float costoTotal;

	private Periodo periodo;
	private Usuario responsable;
	private Estado estado;
	private Programa programaPrincipal;

	private Nodo nodoPadre;

	private List<Evidencia> evidencias;
	private List<ProyectoTema> proyectoTemaTransversal;
	private List<ProyectoEje> proyectoEjeTematico;
	private List<ProyectoEstructura> proyectoEstructuras;
	private List<Estructura> estructuras;
	private List<Accion> acciones;
	private List<Bitacora> regbitacora;
	private List<IndicadorFinanciero> indicadoresFinancieros;
	private List<AvanceFinanciero> avancesFinancieros;

	private List<EjeTematico> ejeTematicos;
	private List<TemaTransversal> temaTransversales;

	@RemoteProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_proyecto")
	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"responsable", "estado", "proyectoTemaTransversal",
				"proyectoEjeTematico", "proyectoEstructuras", "estructuras",
				"acciones", "regbitacora", "indicadoresFinancieros",
				"avancesFinancieros" });
		return reflectionToStringBuilder.toString();
	}

	@RemoteProperty
	@Column(name = "tx_siglas")
	public String getSiglas() {
		return siglas;
	}

	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}

	@RemoteProperty
	@Column(name = "nb_proyecto")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RemoteProperty
	@Column(name = "tx_resumen")
	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	@RemoteProperty
	@Column(name = "tx_objetivo_general")
	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	@RemoteProperty
	@Column(name = "fh_registro")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@RemoteProperty
	@Column(name = "id_responsable")
	public Integer getIdResponsable() {
		return idResponsable;
	}

	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}

	@RemoteProperty
	@Column(name = "nu_costo_total")
	public Float getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Float costoTotal) {
		this.costoTotal = costoTotal;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_responsable", referencedColumnName = "id_usuario", insertable = false, updatable = false) })
	public Usuario getResponsable() {
		return responsable;
	}

	public void setResponsable(Usuario responsable) {
		this.responsable = responsable;
	}

	@RemoteProperty
	@Column(name = "id_estado")
	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_estado", referencedColumnName = "id_estado", insertable = false, updatable = false) })
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@RemoteProperty
	@Column(name = "id_periodo")
	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	@RemoteProperty
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo", insertable = false, updatable = false) })
	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<ProyectoTema> getProyectoTema() {
		return proyectoTemaTransversal;
	}

	public void setProyectoTema(List<ProyectoTema> proyectoTemaTransversal) {
		this.proyectoTemaTransversal = proyectoTemaTransversal;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<ProyectoEje> getProyectoEje() {
		return proyectoEjeTematico;
	}

	public void setProyectoEje(List<ProyectoEje> proyectoEjeTematico) {
		this.proyectoEjeTematico = proyectoEjeTematico;
	}

	@ManyToMany(mappedBy = "proyectos")
	public List<Estructura> getEstructuras() {
		return estructuras;
	}

	public void setEstructuras(List<Estructura> estructuras) {
		this.estructuras = estructuras;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<Bitacora> getRegbitacora() {
		return regbitacora;
	}

	public void setRegbitacora(List<Bitacora> regbitacora) {
		this.regbitacora = regbitacora;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<IndicadorFinanciero> getIndicadoresFinancieros() {
		return indicadoresFinancieros;
	}

	public void setIndicadoresFinancieros(
			List<IndicadorFinanciero> indicadoresFinancieros) {
		this.indicadoresFinancieros = indicadoresFinancieros;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<AvanceFinanciero> getAvancesFinancieros() {
		return avancesFinancieros;
	}

	public void setAvancesFinancieros(List<AvanceFinanciero> avancesFinancieros) {
		this.avancesFinancieros = avancesFinancieros;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}

	@Column(name = "fh_modificacion")
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	@RemoteProperty
	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijo() {
		return getNodosHijoInicializar();
	}

	@RemoteProperty
	@Transient
	@Override
	public Nodo getNodoPadre() {
		return nodoPadre;
	}

	@RemoteProperty
	@Transient
	@Override
	public Integer getId() {
		return idProyecto;
	}

	@Transient
	@Override
	public void setNodoPadre(Nodo nodo) {
		nodoPadre = nodo;
	}

	@Transient
	@SuppressWarnings("unchecked")
	// por el cast de lista de Acciones a lista de Nodos
	@Override
	public <N extends Nodo> List<N> getNodosHijoInicializar() {
		if (acciones == null) {
			Hibernate.initialize(acciones);
		}
		return (List<N>) acciones;
	}

	@ManyToMany(mappedBy = "proyectos")
	public List<EjeTematico> getEjeTematicos() {
		return ejeTematicos;
	}

	public void setEjeTematicos(List<EjeTematico> ejeTematicos) {
		this.ejeTematicos = ejeTematicos;
	}

	@ManyToMany(mappedBy = "proyectos")
	public List<TemaTransversal> getTemaTransversales() {
		return temaTransversales;
	}

	public void setTemaTransversales(List<TemaTransversal> temaTransversales) {
		this.temaTransversales = temaTransversales;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<ProyectoEstructura> getProyectoEstructuras() {
		return proyectoEstructuras;
	}

	public void setProyectoEstructuras(
			List<ProyectoEstructura> proyectoEstructuras) {
		this.proyectoEstructuras = proyectoEstructuras;
	}

	/**
	 * 
	 * Devuelve el porcentaje de avance de las acciones del proyecto
	 * 
	 */
	@RemoteProperty
	@Transient
	@Override
	public Integer getAvance() {
		Double porcentaje;
		Double porcentajeAcciones = 0.0;
		Double porcentajeTotal = (double) (this.getAcciones().size() * PORCENTAJE);

		if (this.getAcciones().size() != 0) {
			Accion accion;

			for (int i = 0; i < this.getAcciones().size(); i++) {
				accion = this.getAcciones().get(i);
				porcentajeAcciones += accion.getAvance();
			}
			porcentaje = (porcentajeAcciones * PORCENTAJE) / porcentajeTotal;
		} else {
			porcentaje = 0.0;
		}
		return (int) Math.round(porcentaje);

	}

	@RemoteProperty
	@Transient
	@Override
	public String getTipoNodo() {

		return Proyecto.class.getSimpleName();
	}

	/**
	 * Suma los montos por erogar de cada indicador financiero y regresa esa
	 * suma.
	 * 
	 * @return El presupuesto erogado
	 */
	@Transient
	public Float getPresupuestoErogado() {
		Float presupuestoErogado = null;
		presupuestoErogado = (float) 0.0;
		if (getIndicadoresFinancieros() != null) {
			for (IndicadorFinanciero indicadorFinanciero : getIndicadoresFinancieros()) {
				presupuestoErogado += indicadorFinanciero.getMontoErogado();
			}
		}
		return presupuestoErogado;
	}

	/**
	 * Suma los montos por erogar de cada indicador financiero y regresa esa
	 * suma.
	 * 
	 * @return El presupuesto disponible.
	 */
	@Transient
	public Float getPresupuestoDisponible() {
		Float presupuestoDisponible;
		presupuestoDisponible = (float) 0.0;
		if (getIndicadoresFinancieros() != null) {
			for (IndicadorFinanciero indicadorFinanciero : getIndicadoresFinancieros()) {
				presupuestoDisponible += indicadorFinanciero
						.getMontoPorErogar();
			}
		}
		return presupuestoDisponible;
	}

	/**
	 * Se obtiene el programa principal de un proyecto
	 * 
	 * @return Se retorna el programa principal del proyecto en cuestion
	 * 
	 */
	@Transient
	public Programa getProgramaPrincipal() {
		// Creo una lista para obtener todas las estructuras del programa
		List<Estructura> estructurasuno;

		// Obtengo las estructuras del programa
		estructurasuno = this.getEstructuras();

		// Si el tamaño de la estructura es igual a uno
		if (estructurasuno.size() == 1) {
			// El programa principal se encuentra en el primer indice de la
			// estructura
			programaPrincipal = estructurasuno.get(0).getPrograma();
		}
		// Si el tamaño de la estructura es igual a 2
		else if (estructurasuno.size() == 2) {
			// Si el programa que esta en el primer indice de la estructura es
			// sectorial
			if (estructurasuno.get(0).getPrograma().getSectorial()) {
				// El programa principal se encuentra en el segundo indice de la
				// estructura
				programaPrincipal = estructurasuno.get(1).getPrograma();
			}
			// Si el programa que esta en el segundo indice de la estructura es
			// sectorial
			else if (estructurasuno.get(1).getPrograma().getSectorial()) {
				// El programa principal se encuentra en el primer indice de la
				// estructura
				programaPrincipal = estructurasuno.get(0).getPrograma();
			}
		}
		// Se retorna el programa principal
		return programaPrincipal;
	}

	public void setProgramaPrincipal(Programa programaPrincipal) {
		this.programaPrincipal = programaPrincipal;
	}

	/**
	 * Se obtiene la cantidad de restricciones atendidas del proyecto
	 * 
	 * @return Cantidad de restricciones atendidas del proyecto en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesAtendidas() {
		// Creo listas para obtener las restricciones, registros y mensajes de
		// restriccion de un proyecto
		List<Bitacora> restriccionesProyecto = new ArrayList<Bitacora>();
		List<Bitacora> mensajesRestriccion;
		List<Bitacora> registrosBitacora;
		Integer restriccionesAtendidas;

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo todos los registros en la bitacora que pertenecen al proyecto
		registrosBitacora = this.getRegbitacora();

		// Por cada registro en la bitacora
		for (Bitacora registro : registrosBitacora) {
			// Si el tipo de registro es el de una restriccion
			if (registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)) {
				// Lo agrego a una lista temporal para obtener todas las
				// restricciones de un proyecto
				restriccionesProyecto.add(registro);
			}
		}

		// Por cada restriccion que agrege a la lista temporal
		for (Bitacora restriccion : restriccionesProyecto) {
			// Creo una lista temporal para almacenar los mensajes
			// correspondientes a la restriccion
			mensajesRestriccion = new ArrayList<Bitacora>();
			// Agrego la restriccion para tomarla en cuenta
			mensajesRestriccion.add(restriccion);

			// Por cada registro en los registros en la bitacora que pertenecen
			// al proyecto
			for (Bitacora registro : registrosBitacora) {
				// Si el tipo de registro es diferente a una restriccion y Si el registro de referencia es el id de la restriccion
				if (!registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)&& !registro.getIdTipoRegistro().equals(TipoRegistro.OBSERVACION) && registro.getIdRegistroReferencia().equals(restriccion.getIdRegistro())) {
						// Lo agrego a la lista temporal de los mensajes
						// correspondientes a la restriccion
						mensajesRestriccion.add(registro);
				}
			}

			// Si el ultimo mensaje agregado a la restriccion es una atencion
			// inmediata o atencion turnada
			if (mensajesRestriccion.get(mensajesRestriccion.size() - 1)
					.getIdTipoRegistro()
					.equals(TipoRegistro.ATENCION_INMEDIATA)
					|| mensajesRestriccion.get(mensajesRestriccion.size() - 1)
							.getIdTipoRegistro()
							.equals(TipoRegistro.ATENCION_TURNADA)) {
				// La cantidad de restricciones atendidas aumenta
				cantidad++;
			}
		}

		// Retornamos la cantidad de restricciones atendidas
		restriccionesAtendidas = cantidad;
		return restriccionesAtendidas;
	}

	/**
	 * Se obtiene la cantidad de restricciones turnadas del proyecto
	 * 
	 * @return Cantidad de restricciones turnadas del proyecto en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesTurnadas() {
		// Creo listas para obtener las restricciones, registros y mensajes de
		// restriccion de un proyecto
		List<Bitacora> restriccionesProyecto = new ArrayList<Bitacora>();
		List<Bitacora> mensajesRestriccion;
		List<Bitacora> registrosBitacora;
		Integer restriccionesTurnadas;

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo todos los registros en la bitacora que pertenecen al proyecto
		registrosBitacora = this.getRegbitacora();

		// Por cada registro en la bitacora
		for (Bitacora registro : registrosBitacora) {
			// Si el tipo de registro es el de una restriccion
			if (registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)) {
				// Lo agrego a una lista temporal para obtener todas las
				// restricciones de un proyecto
				restriccionesProyecto.add(registro);
			}
		}

		// Por cada restriccion que agrege a la lista temporal
		for (Bitacora restriccion : restriccionesProyecto) {
			// Creo una lista temporal para almacenar los mensajes
			// correspondientes a la restriccion
			mensajesRestriccion = new ArrayList<Bitacora>();
			// Agrego la restriccion para tomarla en cuenta
			mensajesRestriccion.add(restriccion);

			// Por cada registro en los registros en la bitacora que pertenecen
			// al proyecto
			for (Bitacora registro : registrosBitacora) {
				// Si el tipo de registro es diferente a una restriccion y Si el registro de referencia es el id de la restriccion
				if (!registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION) && !registro.getIdTipoRegistro().equals(TipoRegistro.OBSERVACION) && registro.getIdRegistroReferencia().equals(restriccion.getIdRegistro())) {
						// Lo agrego a la lista temporal de los mensajes
						// correspondientes a la restriccion
						mensajesRestriccion.add(registro);
				}
			}

			// Si el ultimo mensaje agregado a la restriccion es una turnada
			if (mensajesRestriccion.get(mensajesRestriccion.size() - 1)
					.getIdTipoRegistro().equals(TipoRegistro.DERIVACION)) {
				// La cantidad de restricciones turnadas aumenta
				cantidad++;
			}
		}

		// Retornamos la cantidad de restricciones turnadas
		restriccionesTurnadas = cantidad;
		return restriccionesTurnadas;
	}

	/**
	 * Se obtiene la cantidad de restricciones no atendidas del proyecto
	 * 
	 * @return Cantidad de restricciones no atendidas del proyecto en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesNoAtendidas() {
		// Creo listas para obtener las restricciones, registros y mensajes de
		// restriccion de un proyecto
		List<Bitacora> restriccionesProyecto = new ArrayList<Bitacora>();
		List<Bitacora> mensajesRestriccion;
		List<Bitacora> registrosBitacora;
		Integer restriccionesNoAtendidas;

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo todos los registros en la bitacora que pertenecen al proyecto
		registrosBitacora = this.getRegbitacora();

		// Por cada registro en la bitacora
		for (Bitacora registro : registrosBitacora) {
			// Si el tipo de registro es el de una restriccion
			if (registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)) {
				// Lo agrego a una lista temporal para obtener todas las
				// restricciones de un proyecto
				restriccionesProyecto.add(registro);
			}
		}

		// Por cada restriccion que agrege a la lista temporal
		for (Bitacora restriccion : restriccionesProyecto) {
			// Creo una lista temporal para almacenar los mensajes
			// correspondientes a la restriccion
			mensajesRestriccion = new ArrayList<Bitacora>();
			// Agrego la restriccion para tomarla en cuenta
			mensajesRestriccion.add(restriccion);

			// Por cada registro en los registros en la bitacora que pertenecen
			// al proyecto
			for (Bitacora registro : registrosBitacora) {
				// Si el tipo de registro es diferente a una restriccion y el registro de referencia es el id de la restriccion
				if (!registro.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION) && !registro.getIdTipoRegistro().equals(TipoRegistro.OBSERVACION) && registro.getIdRegistroReferencia().equals(restriccion.getIdRegistro())) {
						// Lo agrego a la lista temporal de los mensajes
						// correspondientes a la restriccion
						mensajesRestriccion.add(registro);
				}
			}

			// Si el ultimo mensaje agregado a la restriccion es una restriccion
			// o una atencion dle director
			if (mensajesRestriccion.get(mensajesRestriccion.size() - 1)
					.getIdTipoRegistro().equals(TipoRegistro.RESTRICCION)
					|| mensajesRestriccion.get(mensajesRestriccion.size() - 1)
							.getIdTipoRegistro()
							.equals(TipoRegistro.ATENCION_DIRECTOR)) {
				// La cantidad de restricciones no atendidas aumenta
				cantidad++;
			}
		}

		// Retornamos la cantidad de restricciones no atendidas
		restriccionesNoAtendidas = cantidad;
		return restriccionesNoAtendidas;
	}

	/**
	 * Se obtiene la cantidad de restricciones no atendidas del proyecto
	 * 
	 * @return Cantidad de restricciones no atendidas del proyecto en cuestion
	 */
	@Transient
	public Integer getObservaciones() {
		// Creo listas para obtener las restricciones, registros y mensajes de
		// restriccion de un proyecto
		List<Bitacora> observacionesProyecto = new ArrayList<Bitacora>();
		List<Bitacora> registrosBitacora;
		Integer observaciones;

		// Obtengo todos los registros en la bitacora que pertenecen al proyecto
		registrosBitacora = this.getRegbitacora();

		// Por cada registro en la bitacora
		for (Bitacora registro : registrosBitacora) {
			// Si el tipo de registro es el de una restriccion
			if (registro.getIdTipoRegistro().equals(TipoRegistro.OBSERVACION)) {
				// Lo agrego a una lista temporal para obtener todas las
				// restricciones de un proyecto
				observacionesProyecto.add(registro);
			}
		}

		// Retornamos la cantidad de restricciones no atendidas
		observaciones = observacionesProyecto.size();
		return observaciones;
	}

	@OneToMany(mappedBy = "proyecto")
	public List<Evidencia> getEvidencias() {
		return evidencias;
	}

	public void setEvidencias(List<Evidencia> evidencias) {
		this.evidencias = evidencias;
	}

	@Transient
	@RemoteProperty
	public Boolean getEstadoEjecucion() {
		return (Integer.valueOf(Estado.EJECUCION)).equals(idEstado);
	}

	@Transient
	@RemoteProperty
	public Boolean getEstadoRevision() {
		return (Integer.valueOf(Estado.REVISION)).equals(idEstado);
	}

	@Transient
	@RemoteProperty
	public Boolean getEstadoCerrado() {
		return (Integer.valueOf(Estado.CERRADO)).equals(idEstado);
	}
}