package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;



@Entity
@Table(name = "t007_tema_transversal_cat")
public class TemaTransversal {
	private Integer idTema;
	private String nombre;
	private String descripcion;
	private List<ProyectoTema> proyectoTema;
	private List<Proyecto> proyectos;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_tema")
	public Integer getIdTema() {
		return idTema;
	}

	public void setIdTema(Integer idTema) {
		this.idTema = idTema;
	}

	@Column(name = "nb_tema_transversal")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "ds_tema_transversal")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(mappedBy = "temaTransversal")
	public List<ProyectoTema> getProyectoTema() {
		return proyectoTema;
	}

	public void setProyectoTema(List<ProyectoTema> proyectoTema) {
		this.proyectoTema = proyectoTema;
	}

	@ManyToMany
	@JoinTable(name = "t025_proyecto_tema_tab", joinColumns = { @JoinColumn(name = "id_tema") }, inverseJoinColumns = { @JoinColumn(name = "id_proyecto") })
	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}
	
	@Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

	@Override
	public boolean equals(Object obj) {

		TemaTransversal tematransg;
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TemaTransversal)) {
			return false;
		}

		tematransg = (TemaTransversal) obj;
		if (idTema == null) {
			return false;
		}
		if(tematransg.idTema == null){
			return false;
		}
		
		return idTema.equals(tematransg.idTema);
	}

}
