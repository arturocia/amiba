package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@NamedQueries({ @NamedQuery(name = "eliminaContacto", query = "delete from Contacto where idContacto= :id") })
@Entity
@Table(name = "t012_contacto_tab")
public class Contacto {
	private Integer idContacto;
	private Integer idTipoContacto;
	private Integer idUsuario;
	private String contacto;
	private String descripcion;
	private TipoContacto tipoContacto;
	private Usuario usuario;
	private Integer principal;

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"tipoContacto", "usuario" });
		return reflectionToStringBuilder.toString();
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_contacto")
	public Integer getIdContacto() {
		return idContacto;
	}

	public void setIdContacto(Integer idContacto) {
		this.idContacto = idContacto;
	}

	@Column(name = "id_usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "id_tipo_contacto")
	public Integer getIdTipoContacto() {
		return idTipoContacto;
	}

	public void setIdTipoContacto(Integer idTipoContacto) {
		this.idTipoContacto = idTipoContacto;
	}

	@Column(name = "tx_valor")
	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	@Column(name = "ds_contacto")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_tipo_contacto", referencedColumnName = "id_tipo_contacto", insertable = false, updatable = false) })
	public TipoContacto getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(TipoContacto tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	// /////
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", insertable = false, updatable = false) })
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "st_principal")
	public Integer getPrincipal() {
		return principal;
	}

	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}

}