package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t014_estado_cat")
public class Estado {

	public static final int REGISTRADO = 1;
	public static final int EDICION = 2;
	public static final int REVISION = 3;
	public static final int EJECUCION = 4;
	public static final int CERRADO = 5;
	private Integer idEstado;
	private String nombre;
	private List<Proyecto> proyectos;

	@Transient
	public boolean getEstadoEjecucion(){
		return this.idEstado.equals(EJECUCION)? true: false;
	}
	
	@Transient
	public boolean getEstadoRevision(){
		return this.idEstado.equals(REVISION)? true: false;
	}
	
	@Transient
	public boolean getEstadoCerrado(){
		return this.idEstado.equals(CERRADO)? true: false;
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_estado")
	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	@Column(name = "nb_estado")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(mappedBy = "estado")
	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}
}
