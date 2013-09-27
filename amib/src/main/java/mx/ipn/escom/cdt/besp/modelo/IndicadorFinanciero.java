package mx.ipn.escom.cdt.besp.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t023_indicador_financiero")
public class IndicadorFinanciero {

	private Integer idIndicadorFinanciero;
	private String nombreFuente;
	private Date fechaSolicitado;
	private Float montoSolicitado;
	private Date fechaAprobado;
	private Float montoAprobado;
	private Integer idProyecto;

	private Proyecto proyecto;
	private List<AvanceFinanciero> avancesFinancieros;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_indicador_financiero")
	public Integer getIdIndicadorFinanciero() {
		return idIndicadorFinanciero;
	}

	public void setIdIndicadorFinanciero(Integer idIndicadorFinanciero) {
		this.idIndicadorFinanciero = idIndicadorFinanciero;
	}

	@RemoteProperty
	@Column(name = "nb_fuente")
	public String getNombreFuente() {
		return nombreFuente;
	}

	public void setNombreFuente(String nombreFuente) {
		this.nombreFuente = nombreFuente;
	}

	@RemoteProperty
	@Column(name = "fh_solicitado")
	public Date getFechaSolicitado() {
		return fechaSolicitado;
	}

	public void setFechaSolicitado(Date fechaSolicitado) {
		this.fechaSolicitado = fechaSolicitado;
	}

	@RemoteProperty
	@Column(name = "nu_monto_solicitado")
	public Float getMontoSolicitado() {
		return montoSolicitado;
	}

	public void setMontoSolicitado(Float montoSolicitado) {
		this.montoSolicitado = montoSolicitado;
	}

	@RemoteProperty
	@Column(name = "fh_aprobado")
	public Date getFechaAprobado() {
		return fechaAprobado;
	}

	public void setFechaAprobado(Date fechaAprobado) {
		this.fechaAprobado = fechaAprobado;
	}

	@RemoteProperty
	@Column(name = "nu_monto_aprobado")
	public Float getMontoAprobado() {
		return montoAprobado;
	}

	public void setMontoAprobado(Float montoAprobado) {
		this.montoAprobado = montoAprobado;
	}

	@RemoteProperty
	@Column(name = "id_proyecto")
	public Integer getIdProyecto() {
		return idProyecto;
	}	
	
	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false) })
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@OrderBy("fechaAvance")
	@OneToMany(mappedBy = "indicadorFinanciero")
	public List<AvanceFinanciero> getAvancesFinancieros() {
		return avancesFinancieros;
	}

	public void setAvancesFinancieros(List<AvanceFinanciero> avancesFinancieros) {
		this.avancesFinancieros = avancesFinancieros;
	}

	/**
	 * Calcula el monto erogado, es decir, suma los avances financieros de este
	 * indicador financiero.
	 * 
	 * @return Monto erogado
	 */
	@Transient
	public Float getMontoErogado() {
		Float montoErogado;
		montoErogado = (float) 0.0;
		if (getAvancesFinancieros() != null) {
			for (AvanceFinanciero avanceFinanciero : getAvancesFinancieros()) {
				montoErogado += (float) (avanceFinanciero.getMonto() != null ? avanceFinanciero
						.getMonto().floatValue() : 0.0f);
			}
		}
		return montoErogado;
	}

	/**
	 * Calcula el monto por erogar, es decir, la resta del monto erogado al
	 * monto disponible.
	 * 
	 * @return Monto por erogar
	 */
	@Transient
	public Float getMontoPorErogar() {
		Float montoPorErogar;
		montoPorErogar = (float) 0.0;
		if (getMontoAprobado() != null) {
			montoPorErogar += getMontoAprobado() - getMontoErogado();
		}
		return montoPorErogar;
	}
	
}