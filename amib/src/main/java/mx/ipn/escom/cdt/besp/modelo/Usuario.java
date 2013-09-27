package mx.ipn.escom.cdt.besp.modelo;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.Where;

@NamedQueries({
		@NamedQuery(name = "eliminaUsuario", query = "delete from Usuario where idUsuario= :id"),
		@NamedQuery(name = "eliminaDireccion", query = "delete from Direccion where idUsuario=:id") })
@Entity
@Table(name = "t010_usuario_cat")
public class Usuario {

	private Integer idUsuario;
	private String psw;
	private String login;
	private String nombre;
	private String apPat;
	private String apMat;
	private String cargo;
	private String rfc;
	private Integer idPerfilUsuario;
	private Integer idArea;
	private Boolean activado;
	private Integer empleado;

	private Direccion direccion;
	private PerfilUsuario perfilUsuario;
	private Area area;
	private Usuario empleados;
	private List<Proyecto> proyectos;
	private List<Proyecto> proyectosCreados;
	private List<Proyecto> proyectosOrdenadosFechaModificacion;
	private List<Programa> programas;
	private List<Contacto> contactos;
	private List<Usuario> superior;
	private List<Proyecto> proyectosEnEdicion;
	private List<Proyecto> proyectosEnRevision;
	private List<Proyecto> proyectosEnEjecucion;

	public Usuario() {
	}

	public Usuario(Usuario usuario) {
		this.psw = usuario.getPsw();
		this.login = usuario.getLogin();
		this.nombre = usuario.getNombre();
		this.apPat = usuario.getApPat();
		this.apMat = usuario.getApMat();
		this.cargo = usuario.getCargo();
		this.rfc = usuario.getRfc();
		this.idPerfilUsuario = usuario.getIdPerfilUsuario();
		this.idArea = usuario.getIdArea();
		this.activado = usuario.getActivado();
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_usuario")
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "tx_psw")
	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	@Column(name = "tx_login")
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "nb_usuario")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "tx_ap_pat")
	public String getApPat() {
		return apPat;
	}

	public void setApPat(String apPat) {
		this.apPat = apPat;
	}

	@Column(name = "tx_cargo")
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Column(name = "tx_ap_mat")
	public String getApMat() {
		return apMat;
	}

	public void setApMat(String apMat) {
		this.apMat = apMat;
	}

	@Column(name = "tx_rfc")
	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		if (rfc.equals("")) {
			this.rfc = "RFC";
		}
		else
		{
			this.rfc = rfc;
		}
	}

	@Column(name = "id_perfil_usuario")
	public Integer getIdPerfilUsuario() {
		return idPerfilUsuario;
	}

	public void setIdPerfilUsuario(Integer idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}

	@Column(name = "id_area")
	public Integer getIdArea() {
		return idArea;
	}

	public void setIdArea(Integer idArea) {
		this.idArea = idArea;
	}

	@Column(name = "st_activado")
	public Boolean getActivado() {
		return activado;
	}

	public void setActivado(Boolean activado) {
		this.activado = activado;
	}

	@Column(name = "id_superior")
	public Integer getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Integer empleado) {
		this.empleado = empleado;
	}

	@OneToOne
	@PrimaryKeyJoinColumn(name = "id_usuario")
	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_perfil_usuario", referencedColumnName = "id_perfil_usuario", insertable = false, updatable = false) })
	public PerfilUsuario getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(PerfilUsuario perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	@OneToMany(mappedBy = "responsable")
	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "id_area", referencedColumnName = "id_area", insertable = false, updatable = false) })
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@OrderBy("principal desc,idTipoContacto")
	@OneToMany(mappedBy = "usuario")
	public List<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	@OneToMany(mappedBy = "usuario")
	public List<Programa> getProgramas() {
		return programas;
	}

	public void setProgramas(List<Programa> programas) {
		this.programas = programas;
	}

	@ManyToOne
	@JoinColumn(name = "id_superior", referencedColumnName = "id_usuario", insertable = false, updatable = false)
	public Usuario getEmpleados() {
		return empleados;
	}

	public void setEmpleados(Usuario empleados) {
		this.empleados = empleados;
	}

	@OneToMany(mappedBy = "empleados")
	public List<Usuario> getSuperior() {
		return superior;
	}

	public void setSuperior(List<Usuario> superior) {
		this.superior = superior;
	}

	@Override
	public String toString() {

		final ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(
				this);

		reflectionToStringBuilder.setExcludeFieldNames(new String[] {
				"direccion", "perfilUsuario", "area", "proyectos", "programas",
				"contactos" });
		return reflectionToStringBuilder.toString();
	}

	/**
	 * Se obtienen los proyectos creados por el usuario
	 * 
	 * @return Una lista con los proyectos que tienen estado de registrado,
	 *         ordenados por ID y por fecha de registro descendentemente
	 */
	@OneToMany(mappedBy = "responsable")
	@Where(clause = "id_estado = " + Estado.REGISTRADO)
	@OrderBy("fechaRegistro DESC, idProyecto DESC ")
	public List<Proyecto> getProyectosCreados() {
		return proyectosCreados;
	}

	public void setProyectosCreados(List<Proyecto> proyectosCreados) {
		this.proyectosCreados = proyectosCreados;
	}

	/**
	 * Se obtienen los proyectos asignados al usuario y que tienen observaciones
	 * o restriciones atendidas
	 * 
	 * @return Una lista con los proyectos que tienen estado de registrado,
	 *         ordenados por ID y por fecha de registro descendentemente
	 */
	@OneToMany(mappedBy = "responsable")
	@OrderBy("fechaModificacion DESC")
	public List<Proyecto> getProyectosOrdenadosFechaModificacion() {
		return proyectosOrdenadosFechaModificacion;
	}

	public void setProyectosOrdenadosFechaModificacion(
			List<Proyecto> proyectosOrdenadosFechaModificacion) {
		this.proyectosOrdenadosFechaModificacion = proyectosOrdenadosFechaModificacion;
	}

	/**
	 * Se obtienen los proyectos asignados al usuario y que tienen observaciones
	 * o restriciones atendidas
	 * 
	 * @return Una lista con los proyectos que tienen estado de registrado,
	 *         ordenados por ID y por fecha de registro descendentemente
	 */
	@Transient
	public List<Proyecto> getProyectosConPendientes() {

		List<Proyecto> proyectosConPendientes = new ArrayList<Proyecto>();
		List<Proyecto> proyectosUsuario = new ArrayList<Proyecto>();
		List<Programa> programasuno = new ArrayList<Programa>();
		List<Estructura> estructuras;

		switch (this.idPerfilUsuario) {
		case PerfilUsuario.COORDINADOR:
			proyectosUsuario = this.getProyectosOrdenadosFechaModificacion();
			break;

		case PerfilUsuario.GERENTE:
			programasuno = this.getProgramas();
			for (Programa programa : programasuno) {
				estructuras = new ArrayList<Estructura>();
				estructuras = programa.getEstructurasTodas();
				for (Estructura estructura : estructuras) {
					proyectosUsuario.addAll(estructura.getProyectos());
				}
			}
			break;

		case PerfilUsuario.SECRETARIO:
			for (Programa programa : programasuno) {
				estructuras = new ArrayList<Estructura>();
				estructuras = programa.getEstructurasTodas();
				for (Estructura estructura : estructuras) {
					proyectosUsuario.addAll(estructura.getProyectos());
				}
			}
			break;
		}

		for (Proyecto proyecto : proyectosUsuario) {
			switch (this.idPerfilUsuario) {
			case PerfilUsuario.COORDINADOR:
				if (proyecto.getRestriccionesNoAtendidas() > 0 || proyecto.getObservaciones() > 0)
				{
					proyectosConPendientes.add(proyecto);
				}
				break;

			case PerfilUsuario.GERENTE:
				if (proyecto.getRestriccionesNoAtendidas() > 0 || proyecto.getRestriccionesTurnadas() > 0)
				{
					proyectosConPendientes.add(proyecto);
				}
				break;

			case PerfilUsuario.SECRETARIO:
				if (proyecto.getRestriccionesNoAtendidas() > 0)
				{
					proyectosConPendientes.add(proyecto);
				}
				break;
			default:
				break;
			}
		}

		return proyectosConPendientes;
	}

	@OneToMany(mappedBy = "responsable")
	@Where(clause = "id_estado = " + Estado.EDICION)
	@OrderBy("fechaRegistro DESC, idProyecto DESC ")
	public List<Proyecto> getProyectosEnEdicion() {
		return proyectosEnEdicion;
	}

	public void setProyectosEnEdicion(List<Proyecto> proyectosEnEdicion) {
		this.proyectosEnEdicion = proyectosEnEdicion;
	}

	@OneToMany(mappedBy = "responsable")
	@Where(clause = "id_estado = " + Estado.REVISION)
	@OrderBy("fechaRegistro DESC, idProyecto DESC ")
	public List<Proyecto> getProyectosEnRevision() {
		return proyectosEnRevision;
	}

	public void setProyectosEnRevision(List<Proyecto> proyectosEnRevision) {
		this.proyectosEnRevision = proyectosEnRevision;
	}

	@OneToMany(mappedBy = "responsable")
	@Where(clause = "id_estado = " + Estado.EJECUCION)
	public List<Proyecto> getProyectosEnEjecucion() {
		return proyectosEnEjecucion;
	}

	public void setProyectosEnEjecucion(List<Proyecto> proyectosEnEjecucion) {
		this.proyectosEnEjecucion = proyectosEnEjecucion;
	}
	
	
}