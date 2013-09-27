package mx.ipn.escom.cdt.besp.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t024_avance_financiero")
public class AvanceFinanciero {

	private Integer idAvance;
	private Integer idIndicadorFinanciero;
	private Float monto;
	private Date fechaAvance;
	private Integer idProyecto;
	
	private IndicadorFinanciero indicadorFinanciero;
	private Proyecto proyecto;
		
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_avance")
	public Integer getIdAvance() {
		return idAvance;
	}
	public void setIdAvance(Integer idAvance) {
		this.idAvance = idAvance;
	}
	
	@RemoteProperty
	@Column(name = "id_indicador_financiero")
	public Integer getIdIndicadorFinanciero() {
		return idIndicadorFinanciero;
	}
	public void setIdIndicadorFinanciero(Integer idIndicadorFinanciero) {
		this.idIndicadorFinanciero = idIndicadorFinanciero;
	}
	
	@RemoteProperty
	@Column(name = "nu_monto")
	public Float getMonto() {
		return monto;
	}
	public void setMonto(Float monto) {
		this.monto = monto;
	}
	
	@RemoteProperty
	@Column(name = "fh_avance")
	public Date getFechaAvance() {
		return fechaAvance;
	}
	public void setFechaAvance(Date fechaAvance) {
		this.fechaAvance = fechaAvance;
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
	@JoinColumns({ @JoinColumn(name = "id_indicador_financiero", referencedColumnName = "id_indicador_financiero", insertable = false, updatable = false) })
	public IndicadorFinanciero getIndicadorFinanciero() {
		return indicadorFinanciero;
	}
	public void setIndicadorFinanciero(IndicadorFinanciero indicadorFinanciero) {
		this.indicadorFinanciero = indicadorFinanciero;
	}
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false) })		
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	
}
