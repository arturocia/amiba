package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;



@DataTransferObject
@Entity
@Table(name = "t017_tipo_registro_cat")
public class TipoRegistro {

	public static final int OBSERVACION = 1;
	public static final int RESTRICCION = 2;
	public static final int ATENCION_INMEDIATA = 3;
	public static final int DERIVACION = 4;
	public static final int ATENCION_DIRECTOR = 5;
	public static final int ATENCION_TURNADA = 6;
	public static final int AJUSTE = 7;
	
	private Integer idTipoRegistro;
	private String nombre;
	
	private List<Bitacora> regbitacora;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_tipo_registro")
	public Integer getIdTipoRegistro() {
		return idTipoRegistro;
	}

	public void setIdTipoRegistro(Integer idTipoRegistro) {
		this.idTipoRegistro = idTipoRegistro;
	}

	@RemoteProperty
	@Column(name="nb_tipo_registro")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(mappedBy="tipoRegistro")
	public List<Bitacora> getRegbitacora() {
		return regbitacora;
	}

	public void setRegbitacora(List<Bitacora> regbitacora) {
		this.regbitacora = regbitacora;
	}
	
	
}
