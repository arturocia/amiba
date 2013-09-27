package mx.ipn.escom.cdt.besp.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t019_indicador_tab")
public class Indicador {
	private static final double PORCENTAJE_MAXIMO = 100d;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_indicador")
	private Integer idIndicador;

	@Column(name = "id_accion")
	private Integer idAccion;

	@Column(name = "id_proyecto")
	private Integer idProyecto;

	@Column(name = "id_unidad")
	private Integer idUnidad;

	@Column(name = "ds_indicador")
	private String descripcion;

	@Column(name = "nu_meta")
	private Integer meta;

	@Column(name = "nu_peso")
	private Integer peso;

	@Column(name = "fh_ultimo_reporte")
	private Date fechaUltimoReporte;

	@Column(name = "nu_avance")
	private Integer avance;

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_accion", referencedColumnName = "id_accion", insertable = false, updatable = false)
	private Accion accion;

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
	private Proyecto proyecto;

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_unidad", referencedColumnName = "id_unidad", insertable = false, updatable = false)
	private Unidad unidad;

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] { "accion",
				"proyecto", "unidad" });
		return reflectionToStringBuilder.toString();
	}

	public Integer getIdIndicador() {
		return idIndicador;
	}

	public void setIdIndicador(Integer idIndicador) {
		this.idIndicador = idIndicador;
	}

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

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Date getFechaUltimoReporte() {
		return fechaUltimoReporte;
	}

	public void setFechaUltimoReporte(Date fechaUltimoReporte) {
		this.fechaUltimoReporte = fechaUltimoReporte;
	}

	public Integer getAvance() {
		return avance;
	}

	public void setAvance(Integer avance) {
		this.avance = avance;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	/**
	 * 
	 * Parte de la Validación de RN74, el metodo donde se usa éste esta en
	 * CerrarProyectoController
	 * 
	 * @return
	 */
	public Boolean getCompleto() {
		if (this.avance!=null && this.avance.equals(this.meta)){
			return true;
		}
		return false;
	}


	/**
	 * 
	 * calcula el porcentaje de avance segun la meta
	 */
	@Transient
	public Long getPorcentaje() {
		Double porcentaje;		
		if(this.avance!=null){
			porcentaje=((PORCENTAJE_MAXIMO/(double)this.meta)*(double)this.avance);
		}else{
			porcentaje=0.0;
		}
		return Math.round(porcentaje);
	}
}