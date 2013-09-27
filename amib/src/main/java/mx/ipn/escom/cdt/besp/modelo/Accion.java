package mx.ipn.escom.cdt.besp.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject(javascript = "Accion")
@Entity
@Table(name = "t018_accion_tab")
public class Accion implements Serializable, Nodo {

	private static final long serialVersionUID = -4193756456651868602L;
	private static final int PORCENTAJE = 100;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_accion")
	private Integer idAccion;

	@Column(name = "id_proyecto")
	private Integer idProyecto;

	@Column(name = "id_periodo")
	private Integer idPeriodo;

	@RemoteProperty
	@Column(name = "nb_accion")
	private String nombre;

	@Column(name = "tx_objetivo")
	private String objetivo;

	@Column(name = "ds_accion")
	private String descripcion;

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_periodo", referencedColumnName = "id_periodo", insertable = false, updatable = false)
	private Periodo periodo;

	@ManyToOne
	@JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
	private Proyecto proyecto;

	@OneToMany(mappedBy = "accion")
	private List<Indicador> indicadores;

	@OneToMany(mappedBy = "accion")
	private List<Evidencia> evidencias;

	public Integer getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Integer getIdPeriodo() {
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public List<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public List<Evidencia> getEvidencias() {
		return evidencias;
	}

	public void setEvidencias(List<Evidencia> evidencias) {
		this.evidencias = evidencias;
	}

	@RemoteProperty
	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijo() {
		return null;
	}

	@RemoteProperty
	@Transient
	@Override
	public Nodo getNodoPadre() {
		return getProyecto();
	}

	@RemoteProperty
	@Transient
	@Override
	public Integer getId() {
		return idAccion;
	}

	@Transient
	@Override
	public void setNodoPadre(Nodo nodo) {
		setProyecto((Proyecto) nodo);
	}

	@Transient
	@Override
	public <N extends Nodo> List<N> getNodosHijoInicializar() {
		return null;
	}

	@Override
	public String toString() {
		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"periodo", "proyecto", "indicadores", "evidencias",
				"estructuraPadre" });
		return reflectionToStringBuilder.toString();
	}

	/**
	 * true si todos los indicadores estan completos y false si al menos uno no
	 * esta completo
	 * 
	 * @return
	 */
	public Boolean getAccionCompleta() {
		List<Indicador> indicadorList;
		indicadorList = indicadores;
		for (Indicador ind : indicadorList) {
			// si algun indicador no esta
			if (!ind.getCompleto())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * Devuelve el porcentaje de avance de la accion
	 * de acuerdo al peso aportado por cada indicador
	 * segun su avance
	 * 
	 */
	@RemoteProperty
	@Transient
	@Override
	public Integer getAvance() {
		Double porcentajeTotal;
		Double pesoTotal = 0.0;
		Double pesoActual = 0.0;
		Indicador indicador;

		for (int i = 0; i < this.indicadores.size(); i++) {
			indicador = this.indicadores.get(i);
			pesoTotal += indicador.getPeso();
			pesoActual += indicador.getPeso()
					* (indicador.getPorcentaje()/PORCENTAJE);						
		}
		porcentajeTotal = (pesoActual * PORCENTAJE) / pesoTotal;		
		return (int) Math.round(porcentajeTotal);
		
	}

	@RemoteProperty
	@Transient
	@Override
	public String getTipoNodo() {

		return Accion.class.getSimpleName();
	}	
}