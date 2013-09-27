package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t011_tipo_contacto_cat")
public class TipoContacto {
	
	public static final int TELEFONO=1;
	public static final int CORREO=2;
	private Integer idTipoContacto;
	private String nombre;
	private String descripcion;
	private List<Contacto> contactos;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column	(name="id_tipo_contacto")
	public Integer getIdTipoContacto() {
		return idTipoContacto;
	}

	public void setIdTipoContacto(Integer idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}
	
	@Column(name="nb_tipo_contacto")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name="ds_tipo_contacto")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@OneToMany(mappedBy = "tipoContacto")
	public List<Contacto> getContactos() {
		return contactos;
	}
  
	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	
	
	
	
}
