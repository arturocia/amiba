package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
@Entity
@Table(name = "t020_tipo_unidad_cat")
public class TipoUnidad {
	public static final int PONDERADO = 2;
	public static final int NUMERICO = 1;
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_tipo_unidad")
	private Integer idTipoUnidad;

	@Column(name = "nb_tipo_unidad")
	private String nombre;
	
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

}
