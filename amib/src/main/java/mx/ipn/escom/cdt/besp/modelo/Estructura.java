package mx.ipn.escom.cdt.besp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.struts2.json.annotations.JSON;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject(javascript = "Estructura")
@Entity
@Table(name = "t003_estructura_tab")
public class Estructura implements Serializable, Nodo {
	private static final int PORCENTAJE = 100;
	private static final long serialVersionUID = -7118540776386616724L;
	private Integer idEstructura;
	private Integer idPadre;
	private String nombre;
	private String descripcion;
	private Integer idPrograma;
	private Integer idPeriodo;
	private Integer idNivel;
	private Estructura estructuraPadre;
	private Programa programa;
	private Periodo periodo;
	private Nivel nivel;
	private List<Estructura> estructuras = null;
	private List<Proyecto> proyectos;
	private List<Nodo> nodosHijo;

	@RemoteProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_estructura")
	public Integer getIdEstructura() {
		return idEstructura;
	}

	public void setIdEstructura(Integer idEstructura) {
		this.idEstructura = idEstructura;
	}

	@RemoteProperty
	@Column(name = "ds_estructura")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@RemoteProperty
	@Column(name = "nb_estructura")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@RemoteProperty
	@Column(name = "id_padre")
	public Integer getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(Integer idPadre) {
		this.idPadre = idPadre;
	}

	@JSON(serialize = false, deserialize = false)
	@OneToMany(mappedBy = "estructuraPadre", fetch = FetchType.LAZY)
	public List<Estructura> getEstructuras() {
		return estructuras;
	}

	public void setEstructuras(List<Estructura> estructuras) {
		this.estructuras = estructuras;
	}

	@ManyToOne
	@JoinColumn(name = "id_padre", referencedColumnName = "id_estructura", insertable = false, updatable = false)
	public Estructura getEstructuraPadre() {
		return estructuraPadre;
	}

	public void setEstructuraPadre(Estructura estructuraPadre) {
		this.estructuraPadre = estructuraPadre;
	}

	@RemoteProperty
	@Column(name = "id_programa")
	public Integer getIdPrograma() {
		return idPrograma;
	}

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

	@ManyToOne
	@JoinColumn(name = "id_programa", referencedColumnName = "id_programa", insertable = false, updatable = false)
	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	@JSON(serialize = false, deserialize = false)
	@ManyToMany
	@JoinTable(name = "t015_proyecto_estructura_tab", inverseJoinColumns = { @JoinColumn(name = "id_proyecto", insertable = false, updatable = false) }, joinColumns = { @JoinColumn(name = "id_estructura", updatable = false, insertable = false) })
	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	@RemoteProperty
	@Column(name = "nu_posicion")
	public Integer getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
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

	@RemoteProperty
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "nu_posicion", referencedColumnName = "nu_posicion", insertable = false, updatable = false),
			@JoinColumn(name = "id_programa", referencedColumnName = "id_programa", insertable = false, updatable = false) })
	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	@RemoteProperty
	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijo() {
		return getNodosHijoInicializar();
	}

	@Transient
	@Override
	public Nodo getNodoPadre() {
		return getEstructuraPadre() != null ? getEstructuraPadre()
				: getPrograma();
	}

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"programa", "proyectos", "nivel", "estructuras",
				"estructuraPadre" });
		return reflectionToStringBuilder.toString();
	}

	@RemoteProperty
	@Transient
	@Override
	public Integer getId() {
		return getIdEstructura();
	}

	@Override
	public void setNodoPadre(Nodo nodo) {
		if (nodo.getClass().equals(Programa.class)) {
			setPrograma((Programa) nodo);
		}
		if (nodo.getClass().equals(Estructura.class)) {
			setEstructuraPadre((Estructura) nodo);
		}
	}

	@JSON(serialize = false, deserialize = false)
	@SuppressWarnings("unchecked")
	// Por el cast de lista de estructuras a lista de nodos
	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijoInicializar() {
		if (nodosHijo == null) {
			nodosHijo = new ArrayList<Nodo>();
			getEstructuras();
			if (estructuras != null) {
				nodosHijo.addAll((List<N>) estructuras);
			}
			if (proyectos != null) {
				nodosHijo.addAll((Collection<? extends N>) proyectos);
			}
		}
		return (List<N>) nodosHijo;
	}

	public void setNodosHijo(List<Nodo> nodosHijo) {
		this.nodosHijo = nodosHijo;
	}
	
	/**
	 * 
	 * Devuelve la suma total de los porcentajes de avance de todos los
	 * proyectos de la estructura
	 * 
	 */
	@Transient
	public Long getPorcentajeProyectos() {
		Double porcentajeProyectos = 0.0;
		Proyecto proyecto;

		for (int i = 0; i < this.getProyectos().size(); i++) {
			proyecto = this.getProyectos().get(i);
			porcentajeProyectos += proyecto.getAvance();
		}
		return Math.round(porcentajeProyectos);
	}

	/**
	 * Revisa si la estructura contiene estructuras y si estas a su vez
	 * contienen mas estructuras, en ese caso obtiene el porcentaje de avance de
	 * cada una, hasta que encuentre proyectos, al encontrar proyectos obtiene
	 * el porcentaje total de estos
	 * 
	 */
	@Transient
	public Long getPorcentajeEstructuras() {
		Long porcentaje = (long) 0;
		Estructura estructura;

		if (!this.getEstructuras().isEmpty()) {

			for (int i = 0; i < this.getEstructuras().size(); i++) {
				estructura = this.getEstructuras().get(i);
				porcentaje += estructura.getPorcentajeEstructuras();
			}
			if (!this.getProyectos().isEmpty()) {
				porcentaje += this.getPorcentajeProyectos();
			}

		} else if (!this.getProyectos().isEmpty()) {
			porcentaje = this.getPorcentajeProyectos();
		} else {
			porcentaje = (long) 0;
		}

		return porcentaje;
	}

	/**
	 * Obtiene el porcentaje total de las estructuras y proyectos contenidos en
	 * la estructura
	 * 
	 */
	@RemoteProperty
	@Transient
	@Override
	public Integer getAvance() {
		Double porcentaje = 0.0;
		Double porcentajeTotal = (double) ((this.getEstructuras().size() * PORCENTAJE) + (this
				.getProyectos().size() * PORCENTAJE));
		porcentaje = ((this.getPorcentajeEstructuras() * PORCENTAJE) / porcentajeTotal);
		return (int) Math.round(porcentaje);

	}

	@RemoteProperty
	@Transient
	@Override
	public String getTipoNodo() {

		return Estructura.class.getSimpleName();
	}

	/**
	 * Se obtiene la cantidad de restricciones atendidas de la estructura
	 * 
	 * @return Cantidad de restricciones atendidas de la estructura en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesAtendidas() {
		// Creo una lista temporal para obtener todos los proyectos del programa
		List<Proyecto> proyectosuno = new ArrayList<Proyecto>();

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo todos los proyectos de esta estructura
		proyectosuno.addAll(this.getProyectos());

		// Por cada proyecto
		for (Proyecto proyecto : proyectosuno) {
			// Obtengo la cantidad de restricciones atendidas
			cantidad += proyecto.getRestriccionesAtendidas();
		}

		// Retorno la cantidad de restricciones atendidas
		return cantidad;
	}

	/**
	 * Se obtiene la cantidad de restricciones turnadas de la estructura
	 * 
	 * @return Cantidad de restricciones turnadas de la estructura en cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesTurnadas() {
		// Creo una lista temporal para obtener todos los proyectos del programa
		List<Proyecto> proyectosdos = new ArrayList<Proyecto>();

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo todos los proyectos de esta estructura
		proyectosdos.addAll(this.getProyectos());

		// Por cada proyecto
		for (Proyecto proyecto : proyectosdos) {
			// Obtengo la cantidad de restricciones turnadas
			cantidad += proyecto.getRestriccionesTurnadas();
		}

		// Retorno la cantidad de restricciones atendidas
		return cantidad;
	}

	/**
	 * Se obtiene la cantidad de restricciones no atendidas de la estructura
	 * 
	 * @return Cantidad de restricciones no atendidas de la estructura en
	 *         cuestion
	 */
	@RemoteProperty
	@Transient
	public Integer getRestriccionesNoAtendidas() {
		// Creo una lista temporal para obtener todos los proyectos del programa
		List<Proyecto> proyectostres = new ArrayList<Proyecto>();

		// Creo una variable que me ayudara a obtener la cantidad de
		// restricciones atendidas
		Integer cantidad = 0;

		// Obtengo todos los proyectos de esta estructura
		proyectostres.addAll(this.getProyectos());

		// Por cada proyecto
		for (Proyecto proyecto : proyectostres) {
			// Obtengo la cantidad de restricciones no atendidas
			cantidad += proyecto.getRestriccionesNoAtendidas();
		}

		// Retorno la catidad de restricciones no atendidas
		return cantidad;
	}
}