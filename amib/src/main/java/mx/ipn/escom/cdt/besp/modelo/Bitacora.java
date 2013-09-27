package mx.ipn.escom.cdt.besp.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject
@Entity
@Table(name = "t016_bitacora_tab")
public class Bitacora
{

	private Integer idRegistro;
	private Integer idProyecto;
	private Integer idRegistroReferencia;
	private Integer idTipoRegistro;
	private Date fechaRegistro;
	private Integer idRemitente;
	private Integer idDestinatario;
	private String descripcion;
	private Usuario usuarioRemitente, usuarioDestinatario;
	
	private TipoRegistro tipoRegistro;
	private Proyecto proyecto;
	private Bitacora registroReferencia;
	
	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {"tipoRegistro", "proyecto", "registroReferencia"});
		return reflectionToStringBuilder.toString();
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_registro")
	public Integer getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
	}
	
	@RemoteProperty
	@Column(name="id_proyecto")
	public Integer getIdProyecto() {
		return this.idProyecto;
	}
	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}
	
	@RemoteProperty
	@Column(name="id_registro_referencia")
	public Integer getIdRegistroReferencia() {
		return idRegistroReferencia;
	}
	public void setIdRegistroReferencia(Integer idRegistroReferencia) {
		this.idRegistroReferencia = idRegistroReferencia;
	}
	
	@RemoteProperty
	@Column(name="id_tipo_registro")
	public Integer getIdTipoRegistro() {
		return idTipoRegistro;
	}
	public void setIdTipoRegistro(Integer idTipoRegistro) {
		this.idTipoRegistro = idTipoRegistro;
	}
	
	@RemoteProperty
	@Column(name="fh_registro")
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	@RemoteProperty
	@Column(name="id_remitente")
	public Integer getIdRemitente() {
		return idRemitente;
	}
	public void setIdRemitente(Integer idRemitente) {
		this.idRemitente = idRemitente;
	}
	
	@RemoteProperty
	@Column(name="id_destinatario")
	public Integer getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(Integer idDestinatario) {
		this.idDestinatario = idDestinatario;
	}
	
	@RemoteProperty
	@Column(name="descripcion")
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_tipo_registro", referencedColumnName = "id_tipo_registro", insertable = false, updatable = false) })
	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}
	public void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}
	
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", insertable = false, updatable = false) })
	public Proyecto getProyecto() {
		return proyecto;
	}
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	@OneToOne
	@JoinColumn(name = "id_registro_referencia", referencedColumnName = "id_registro", insertable = false, updatable = false)
	public Bitacora getRegistroReferencia() {
		return registroReferencia;
	}
	public void setRegistroReferencia(Bitacora registroReferencia) {
		this.registroReferencia = registroReferencia;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_remitente", referencedColumnName = "id_usuario", insertable = false, updatable = false) })
	public Usuario getUsuarioRemitente() {
		return usuarioRemitente;
	}

	public void setUsuarioRemitente(Usuario usuarioRemitente) {
		this.usuarioRemitente = usuarioRemitente;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_destinatario", referencedColumnName = "id_usuario", insertable = false, updatable = false) })
	public Usuario getUsuarioDestinatario() {
		return usuarioDestinatario;
	}

	public void setUsuarioDestinatario(Usuario usuarioDestinatario) {
		this.usuarioDestinatario = usuarioDestinatario;
	}

}
