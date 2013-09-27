package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.directwebremoting.annotations.RemoteProperty;

@Entity
@Table(name = "t025_proyecto_tema_tab")
public class ProyectoTema implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7879109521501043474L;
	private Integer idTema;
	private Integer idProyecto;
	private Proyecto proyecto;
	private TemaTransversal temaTransversal;
	private ProyectoTemaId proyectoTemaId;

	public ProyectoTema() {
	}

	public ProyectoTema(ProyectoTemaId proyectoTemaId) {
		this.proyectoTemaId = proyectoTemaId;
	}

	public ProyectoTema(Integer idTema, Integer idProyecto) {
		proyectoTemaId = new ProyectoTemaId();
		proyectoTemaId.setIdTema(idTema);
		proyectoTemaId.setIdProyecto(idProyecto);
	}

	@EmbeddedId
	public ProyectoTemaId getProyectoTemaId() {
		return proyectoTemaId;
	}

	public void setProyectoTemaId(ProyectoTemaId proyectoTemaId) {
		this.proyectoTemaId = proyectoTemaId;
	}
	
	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "proyecto",
				"temaTransversal");
	}

	@Column(name = "id_tema", insertable = false, updatable = false)
	public Integer getIdTema() {
		return idTema;
	}

	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}

	@Column(name = "id_proyecto", insertable = false, updatable = false)
	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_tema", referencedColumnName = "id_tema", insertable = false, updatable = false)
	public TemaTransversal getTemaTransversal() {
		return temaTransversal;
	}

	public void setTemaTransversal(TemaTransversal temaTransversal) {
		this.temaTransversal = temaTransversal;
	}

}
