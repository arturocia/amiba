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
@Table(name = "t026_proyecto_eje_tab")
public class ProyectoEje implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9187039295415947617L;
	private Integer idEje;
	private Integer idProyecto;
	private Proyecto proyecto;
	private EjeTematico ejeTematico;
	private ProyectoEjeId proyectoEjeId;

	public ProyectoEje() {
	}

	public ProyectoEje(ProyectoEjeId proyectoEjeId) {
		this.proyectoEjeId = proyectoEjeId;
	}

	public ProyectoEje(Integer idEje, Integer idProyecto) {
		proyectoEjeId = new ProyectoEjeId();
		proyectoEjeId.setIdEje(idEje);
		proyectoEjeId.setIdProyecto(idProyecto);
	}

	@EmbeddedId
	public ProyectoEjeId getProyectoEjeId() {
		return proyectoEjeId;
	}

	public void setProyectoEjeId(ProyectoEjeId proyectoEjeId) {
		this.proyectoEjeId = proyectoEjeId;
	}
	
	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "proyecto",
				"ejeTematico");
	}

	@Column(name = "id_eje", insertable = false, updatable = false)
	public Integer getIdEje() {
		return idEje;
	}

	public void setIdEje(Integer idEje) {
		this.idEje = idEje;
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
	@JoinColumn(name = "id_eje", referencedColumnName = "id_eje", insertable = false, updatable = false)
	public EjeTematico getEjeTematico() {
		return ejeTematico;
	}

	public void setEjeTematico(EjeTematico ejeTematico) {
		this.ejeTematico = ejeTematico;
	}

}
