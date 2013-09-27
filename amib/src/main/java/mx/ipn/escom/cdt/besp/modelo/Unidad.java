package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t021_unidad_tab")
public class Unidad {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_unidad")
	private Integer idUnidad;

	@Column(name = "id_tipo_unidad")
	private Integer idTipoUnidad;

	@Column(name = "nb_unidad")
	private String nombre;

	@Column(name = "ds_unidad")
	private String descripcion;

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_tipo_unidad", referencedColumnName = "id_tipo_unidad", insertable = false, updatable = false)
	private TipoUnidad tipoUnidad;

	public Integer getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}

	public Integer getIdTipoUnidad() {
		return idTipoUnidad;
	}

	public void setIdTipoUnidad(Integer idTipoUnidad) {
		this.idTipoUnidad = idTipoUnidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@JSON(serialize = false, deserialize = false)
	public TipoUnidad getTipoUnidad() {
		return tipoUnidad;
	}

	public void setTipoUnidad(TipoUnidad tipoUnidad) {
		this.tipoUnidad = tipoUnidad;
	}
}
