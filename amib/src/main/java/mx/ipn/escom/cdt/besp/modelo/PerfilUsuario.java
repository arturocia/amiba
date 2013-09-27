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
@Table(name="t009_perfil_usuario_cat")
public class PerfilUsuario {
	public static final int ADMINISTRADOR = 1;
	public static final int COORDINADOR = 5;
	public static final int SECRETARIO = 2;
	public static final int GERENTE = 4;
	public static final int DIRECTORGENERAL = 3;
	
	
	private Integer idPerfilUsuario;
	private String nombre;
	private String descripcion;
	private List<Usuario> usuarios;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="id_perfil_usuario")
	public Integer getIdPerfilUsuario() {
		return idPerfilUsuario;
	}
	public void setIdPerfilUsuario(Integer idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}
	@Column(name="nb_perfil_usuario")
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@Column(name="ds_perfil_usuario")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@OneToMany(mappedBy = "perfilUsuario" )
	public List<Usuario> getUsuarios(){
		return usuarios;
		}	
    public void setUsuarios(List<Usuario> usuarios){
		this.usuarios = usuarios;
		}
	
}
