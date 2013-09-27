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
@Table(name = "t006_eje_tematico_cat")
public class EjeTematico {
	private Integer idEje;
	private String nombre;
	private String descripcion;
	private List<ProyectoEje> proyectoEje;
	private List<Proyecto> proyectos;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_eje")
	public Integer getIdEje() {
		return idEje;
	}

	public void setIdEje(Integer idEje) {
		this.idEje = idEje;
	}

	@Column(name = "nb_eje_tematico")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "ds_eje_tematico")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(mappedBy = "ejeTematico")
	public List<ProyectoEje> getProyectoEje() {
		return proyectoEje;
	}

	public void setProyectoEje(List<ProyectoEje> proyectoEje) {
		this.proyectoEje = proyectoEje;
	}

	@ManyToMany
	@JoinTable(name="t026_proyecto_eje_tab",
	joinColumns = {
			@JoinColumn(name="id_eje")},
			inverseJoinColumns={
			@JoinColumn(name="id_proyecto")
	})
	
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

		EjeTematico ejeTematico;
				
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EjeTematico)) {
			return false;
		}

		ejeTematico = (EjeTematico) obj;
		if (idEje == null) {
			return false;
		}
		if(ejeTematico.idEje == null){
			return false;
		}
		
		
		return idEje.equals(ejeTematico.idEje);
		
	}

	
	

}