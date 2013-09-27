package mx.ipn.escom.cdt.besp.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t002_nivel_cat")
public class Nivel implements Serializable {

	private static final long serialVersionUID = 1509755164195904036L;
	private Integer idPrograma;
	private Integer idnivel;
	private String nombre;
	private String descripcion;
	private Integer posicion;
	private Programa programa;
	private List<Estructura> estructuras;

	@RemoteProperty
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_nivel")
	public Integer getIdNivel() {
		return idnivel;
	}

	public void setIdNivel(Integer idnivel) {
		this.idnivel = idnivel;
	}

	@Column(name = "id_programa")
	public Integer getIdPrograma() {
		return idPrograma;
	}

	public void setIdPrograma(Integer idPrograma) {
		this.idPrograma = idPrograma;
	}

	@RemoteProperty
	@Column(name = "nb_nivel")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "ds_nivel")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "nu_posicion")
	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_programa", referencedColumnName = "id_programa", insertable = false, updatable = false) })
	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;

	}

	@OneToMany(mappedBy = "nivel")
	public List<Estructura> getEstructuras() {
		return estructuras;
	}

	public void setEstructuras(List<Estructura> estructuras) {
		this.estructuras = estructuras;
	}

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"programa", "estructuras" });
		return reflectionToStringBuilder.toString();
	}
}