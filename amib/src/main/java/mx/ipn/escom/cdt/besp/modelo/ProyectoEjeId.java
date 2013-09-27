package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * ColoniaId generated by hbm2java
 */
@Embeddable
public class ProyectoEjeId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7524062839971038885L;
	private Integer idEje = null;
	private Integer idProyecto = null;

	public ProyectoEjeId() {
	}

	public ProyectoEjeId(Integer idEje, Integer idProyecto) {
		this.idEje = idEje;
		this.idProyecto = idProyecto;
	}
	
	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "");
	}

	@Column(name = "id_eje", nullable = false)
	public Integer getIdEje() {
		return this.idEje;
	}

	public void setIdEje(Integer idEje) {
		this.idEje = idEje;
	}

	@Column(name = "id_proyecto", nullable = false)
	public Integer getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

}