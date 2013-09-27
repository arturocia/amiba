package mx.ipn.escom.cdt.besp.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t013_direccion_tab")
public class Direccion {
	private Integer idUsuario;
	private String calle;
	private String direccion;
	private String colonia;
	private Integer cp;
	private String deleg;
	private String edo;
	private String pais;
	
	private Usuario usuario;

	@Id
	@Column(name = "id_usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@OneToOne(mappedBy = "direccion")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "tx_calle")
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}



	@Column(name = "tx_colonia")
	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	@Column(name = "nu_cp")
	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	@Column(name = "tx_deleg")
	public String getDeleg() {
		return deleg;
	}

	public void setDeleg(String deleg) {
		this.deleg = deleg;
	}

	@Column(name = "tx_edo")
	public String getEdo() {
		return edo;
	}

	public void setEdo(String edo) {
		this.edo = edo;
	}

	@Column(name = "tx_pais")
	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
    @Column(name = "tx_direccion")
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

}
