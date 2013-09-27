package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
* ColoniaId generated by hbm2java
*/
@Embeddable
public class ProyectoTemaId implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7524062839971038885L;
	private Integer idTema = null;
	private Integer idProyecto = null;

	public ProyectoTemaId() {
	}

	public ProyectoTemaId(Integer idTema, Integer idProyecto) {
		this.idTema = idTema;
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

	@Column(name = "id_tema", nullable = false)
	public Integer getIdTema() {
		return this.idTema;
	}

	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}

	@Column(name = "id_proyecto", nullable = false)
	public Integer getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

}