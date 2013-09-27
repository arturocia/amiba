package mx.ipn.escom.cdt.besp.modelo;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t022_evidencia_tab")
public class Evidencia {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_evidencia")
	private Integer idEvidencia;

	@Column(name = "id_accion")
	private Integer idAccion;

	@Column(name = "st_estudio")
	private Boolean estudio;
	
	@Column(name = "id_proyecto")
	private Integer idProyecto;

	@Column(name = "bl_archivo")
	private byte[] archivo;
	
	@Column(name = "fh_evidencia")
	private Date fecha;

	@Column(name = "nb_evidencia")
	private String nombre;

	@Column(name = "ds_evidencia")
	private String descripcion;

	@Column(name = "tx_clave_documental")
	private String claveDocumental;
	
	@Column(name = "nb_archivo_fisico")
	private String nombreArchivoFisico;


	
	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_accion", referencedColumnName = "id_accion", insertable = false, updatable = false)
	private Accion accion;

	@RemoteProperty
	@ManyToOne
	@JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false)
	private Proyecto proyecto;
	
	@Transient
	@Override
	public String toString() {
		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);
		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"proyecto","accion","archivo" });
		return reflectionToStringBuilder.toString();
	}

	public Integer getIdEvidencia() {
		return idEvidencia;
	}

	public void setIdEvidencia(Integer idEvidencia) {
		this.idEvidencia = idEvidencia;
	}

	public void setEstudio(Boolean estudio) {
		this.estudio = estudio;
	}

	public Boolean getEstudio() {
		return estudio;
	}

	public Integer getIdAccion() {
		return idAccion;
	}

	public void setIdAccion(Integer idAccion) {
		this.idAccion = idAccion;
	}

	public Integer getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		if(archivo != null)
		{
			this.archivo = Arrays.copyOf(archivo,archivo.length);
		}
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getClaveDocumental() {
		return claveDocumental;
	}

	public void setClaveDocumental(String claveDocumental) {
		this.claveDocumental = claveDocumental;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
	}
	

	public String getNombreArchivoFisico() {
		return nombreArchivoFisico;
	}

	public void setNombreArchivoFisico(String nombreArchivoFisico) {
		this.nombreArchivoFisico = nombreArchivoFisico;
	}

}
