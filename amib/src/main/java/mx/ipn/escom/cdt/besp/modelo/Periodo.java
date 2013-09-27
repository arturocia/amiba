package mx.ipn.escom.cdt.besp.modelo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t004_periodo_tab")
public class Periodo {
	public static final int PERIODO_RELATIVO = 1;
	public static final int PERIODO_DEFINIDO = 2;
	public static final int PERIODO_INDEFINIDO = 3;
	public static final int PERIODO_INVALIDO = 4;
	private static final int DURACION = 86400000;
	private Integer idPeriodo;
	private Integer duracion;
	private Date fechaInicio;
	private Date fechaFin;
	
	

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_periodo")
	public Integer getIdPeriodo() {		
		return idPeriodo;
	}

	public void setIdPeriodo(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	@RemoteProperty
	@Column(name = "du_periodo")
	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	@RemoteProperty
	@Column(name = "fh_inicio")
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@RemoteProperty
	@Column(name = "fh_fin")
	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * Determina el tipo del <code>periodo</code>, que puede ser uno de los
	 * siguientes:
	 * 
	 * <ul>
	 * <li> {@link Periodo#PERIODO_INDEFINIDO} El que no tiene ningún atributo
	 * definido.
	 * <li> {@link Periodo#PERIODO_DEFINIDO} El tiene fecha de inicio y fecha fin
	 * o duración.
	 * <li>{@link Periodo#PERIODO_RELATIVO} El que tiene duración solamente.
	 * <li>{@link Periodo#PERIODO_INVALIDO} si los atributos del periodo no
	 * corresponden con ninguna de las configuraciones anteriores.
	 * </ul>
	 * 
	 * 
	 * @param periodo
	 * @return El tipo del periodo.
	 */
	@Transient
	public Integer getTipoPeriodo() {
		Integer tipoPeriodo = null;

		if (getDuracion() != null && !getDuracion().equals(0)) {
			if (getFechaInicio() != null) {
				tipoPeriodo = PERIODO_DEFINIDO;
			} else {
				tipoPeriodo = PERIODO_RELATIVO;
			}
		} else {
			if (getFechaInicio() != null) {
				if (getFechaFin() != null) {
					tipoPeriodo = PERIODO_DEFINIDO;
				} else {
					tipoPeriodo = PERIODO_INVALIDO;
				}
			} else {
				if (getFechaFin() != null) {
					tipoPeriodo = PERIODO_INVALIDO;
				} else {
					tipoPeriodo = PERIODO_INDEFINIDO;
				}
			}
		}

		return tipoPeriodo;
	}

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {});
		return reflectionToStringBuilder.toString();
	}

	@Transient
	@RemoteProperty
	public Date getFechaFinCalculada() {
		if (fechaFin == null && duracion != null) {
			Calendar calendar = Calendar.getInstance();

			if (getFechaInicio() != null) {
				calendar.setTime(getFechaInicio());
				calendar.add(Calendar.DATE, getDuracion());
				return calendar.getTime();
			} else {
				return null;
			}
		}
		return fechaFin;
	}

	@Transient
	@RemoteProperty
	public Integer getDuracionCalculado() {
		if (fechaInicio != null && fechaFin != null) {
			return (int) ((fechaFin.getTime() - fechaInicio.getTime()) / DURACION);
		}
		return duracion;
	}
	
}
