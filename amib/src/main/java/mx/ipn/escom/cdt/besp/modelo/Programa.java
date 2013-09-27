package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;
import org.hibernate.annotations.Where;

@DataTransferObject(javascript = "Programa")
@Entity
@Table(name = "t001_programa_1n_tab")
public class Programa implements Nodo {

	private Integer idPrograma;
	private Integer idPeriodo;
	private String siglas;
	private String resumen;
	private String nombre;
	private Integer idUsuario = null;
	private Periodo periodo;
	private Usuario usuario;
	private List<Nivel> niveles;
	private List<Estructura> estructuras;
	private Boolean sectorial;
	private List<Estructura> estructurasTodas;
	private static final int PORCENTAJE = 100;
	private static final int DIAS_ANIO = 365;
	private static final int MES_EXACTO = 30;
	private static final int SEMANA_EXACTA = 7;

	@RemoteProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_programa")
	public Integer getIdPrograma() {
		return idPrograma;
	}

	@RemoteProperty
	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	@Column(name = "id_periodo")
	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
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
	@Column(name = "tx_resumen")
	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	@RemoteProperty
	@Column(name = "nb_programa_1n")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RemoteProperty
	@Column(name = "st_programa_sectorial")
	public Boolean getSectorial() {
		return sectorial;
	}

	public void setSectorial(Boolean sectorial) {
		this.sectorial = sectorial;
	}

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo", insertable = false, updatable = false)
	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@Column(name = "id_usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@OrderBy("posicion")
	@OneToMany(mappedBy = "programa")
	public List<Nivel> getNiveles() {
		return niveles;
	}

	public void setNiveles(List<Nivel> niveles) {
		this.niveles = niveles;
	}

	@Where(clause = "nu_posicion=1")
	@OneToMany(mappedBy = "programa")
	public List<Estructura> getEstructuras() {
		return estructuras;
	}

	public void setEstructuras(List<Estructura> estructuras) {
		this.estructuras = estructuras;
	}

	@OneToMany(mappedBy = "programa")
	public List<Estructura> getEstructurasTodas() {
		return estructurasTodas;

	}

	public void setEstructurasTodas(List<Estructura> estructurasTodas) {
		this.estructurasTodas = estructurasTodas;
	}

	@RemoteProperty
	@SuppressWarnings("unchecked")
	// Por el cast de lista de estructuras a lista de nodos
	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijo() {
		return (List<N>) estructuras;
	}

	@Transient
	@Override
	public Nodo getNodoPadre() {
		return null;
	}

	@Transient
	@Override
	public String toString() {
		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);
		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"niveles", "estructuras", "usuario", "estructurasTodas" });
		return reflectionToStringBuilder.toString();
	}

	@RemoteProperty
	@Transient
	@Override
	public Integer getId() {
		return getIdPrograma();
	}

	@Override
	public void setNodoPadre(Nodo nodo) {
		setUsuario((Usuario) nodo);
	}

	@SuppressWarnings("unchecked")
	// Por el cast de lista de estructuras a lista de nodos
	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijoInicializar() {
		return (List<N>) getEstructuras();
	}

	/**
	 * 
	 * Devuelve la suma total de los porcentajes de avance de todas las
	 * estructuras
	 * 
	 */
	@RemoteProperty
	@Transient
	@Override
	public Integer getAvance() {
		Double porcentaje;
		Double porcentajeEstructuras = 0.0;
		Double porcentajeTotal = (double) (this.getEstructuras().size() * PORCENTAJE);

		if (this.getEstructuras().size() != 0) {
			Estructura estructura;

			for (int i = 0; i < this.getEstructuras().size(); i++) {
				estructura = this.getEstructuras().get(i);
				porcentajeEstructuras += estructura.getAvance();
			}
			porcentaje = (porcentajeEstructuras * PORCENTAJE) / porcentajeTotal;
		} else {
			porcentaje = 0.0;
		}
		return (int) Math.round(porcentaje);
	}

	@RemoteProperty
	@Transient
	@Override
	public String getTipoNodo() {

		return Programa.class.getSimpleName();
	}

	/**
	 * Se obtiene la cantidad de restricciones atendidas del programa
	 * 
	 * @return Cantidad de restricciones atendidas del proyecto en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesAtendidas() {
		// Creo una lista temporal para obtener todas las estructuras
		List<Estructura> estructurasuno;

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo las estructuras del programa
		estructurasuno = this.getEstructurasTodas();

		// Por cada estructura
		for (Estructura estructura : estructurasuno) {
			// Obtengo la cantidad de restricciones atendidas
			cantidad += estructura.getRestriccionesAtendidas();
		}

		// Retorno la cantidad de restricciones atendidas
		return cantidad;
	}

	/**
	 * Se obtiene la cantidad de restricciones turnadas del programa
	 * 
	 * @return Cantidad de restricciones turnadas del programa en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesTurnadas() {
		// Creo una lista temporal para obtener todas las estructuras
		List<Estructura> estructurasdos;

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo las estructuras del programa
		estructurasdos = this.getEstructurasTodas();

		// Por cada estructura
		for (Estructura estructura : estructurasdos) {
			// Obtengo la cantidad de restricciones turnadas
			cantidad += estructura.getRestriccionesTurnadas();
		}

		// Retorno la cantidad de restricciones turnadas
		return cantidad;
	}

	/**
	 * Se obtiene la cantidad de restricciones no atendidas del programa
	 * 
	 * @return Cantidad de restricciones no atendidas del programa en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesNoAtendidas() {
		// Creo una lista temporal para obtener todas las estructuras
		List<Estructura> estructurastres;

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo las estructuras del programa
		estructurastres = this.getEstructurasTodas();

		// Por cada estructura
		for (Estructura estructura : estructurastres) {
			// Obtengo la cantidad de restricciones no atendidas
			cantidad += estructura.getRestriccionesNoAtendidas();
		}

		// Retorno la cantidad de restricciones no atendidas
		return cantidad;
	}

	@Transient
	@RemoteProperty
	public String getUnidadMedidaDuracion() {
		// todo: LOGIGA DE CONVERSION

		// si la duracion son años exactos
		if (this.periodo.getDuracion() % DIAS_ANIO == 0) {
			return this.periodo.getDuracion() / DIAS_ANIO + " año(s)";
		}
		// si la duracion son meses exactos
		if (this.periodo.getDuracion() % MES_EXACTO == 0) {
			return this.periodo.getDuracion() + " mes(es)";
		}
		// si la duracion son semanas exactos
		if (this.periodo.getDuracion() % SEMANA_EXACTA == 0) {
			return this.periodo.getDuracion() / SEMANA_EXACTA + " semana(s)";
		}
		// si la duracion no son años, semanas o meses exactos
		if (this.periodo.getDuracion() % DIAS_ANIO != 0
				&& this.periodo.getDuracion() % SEMANA_EXACTA != 0
				&& this.periodo.getDuracion() % MES_EXACTO != 0) {
			return this.periodo.getDuracion() + " día(s)";
		}
		return "";
	}
}
