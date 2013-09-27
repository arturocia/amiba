package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "t015_proyecto_estructura_tab")
public class ProyectoEstructura implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7352337195466065697L;
	@EmbeddedId
	private ProyectoEstructuraId id;
	@Column(name = "id_programa", nullable = false)
	private Integer idPrograma = null;
	@ManyToOne
	@JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
	private Proyecto proyecto;

	public ProyectoEstructura() {
	}

	public ProyectoEstructura(Integer idPrograma, Integer idEstructura,
			Integer idProyecto) {
		id = new ProyectoEstructuraId();
		id.setIdEstructura(idEstructura);
		id.setIdProyecto(idProyecto);
		this.idPrograma=idPrograma;
	}

	public ProyectoEstructuraId getId() {
		return id;
	}

	public void setId(ProyectoEstructuraId id) {
		this.id = id;
	}
	
	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "proyecto");
	}

	@Override
	public String toString() {
		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);
		reflectionToStringBuilder
				.setExcludeFieldNames(new String[] { "proyecto" });
		return reflectionToStringBuilder.toString();
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	public Integer getIdPrograma() {
		return idPrograma;
	}

}
