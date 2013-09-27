package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

@DataTransferObject(javascript = "RaizAuxiliar")
public class RaizAuxiliar implements Nodo {
	private String nombre;
	private Integer id;
	private Periodo periodo;
	private List<Programa> listaProgramas;

	@SuppressWarnings("unchecked")
	// Por el cast de lista de nodos a lista de programas
	@RemoteProperty
	@Override
	public <N extends Nodo> List<N> getNodosHijo() {
		return (List<N>) getListaProgramas();
	}

	@Override
	public <N extends Nodo> List<N> getNodosHijoInicializar() {
		return null;
	}

	@Override
	public Nodo getNodoPadre() {
		return null;
	}

	@RemoteProperty
	@Override
	public Periodo getPeriodo() {
		Integer duracionMayor = -1;

		this.periodo = new Periodo();
		getListaProgramas();

		for (Programa programa : listaProgramas) {
			periodo = programa.getPeriodo();
			if (periodo.getTipoPeriodo().equals(Periodo.PERIODO_DEFINIDO)
					&& periodo.getDuracionCalculado() > duracionMayor) {
				duracionMayor = periodo.getDuracionCalculado();
				this.periodo.setDuracion(duracionMayor);
				this.periodo.setFechaInicio(periodo.getFechaInicio());
				this.periodo.setFechaFin(periodo.getFechaFinCalculada());
			}
		}

		return this.periodo;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setNodoPadre(Nodo nodo) {

	}

	@RemoteProperty
	@Override
	public String getNombre() {
		nombre = "Programas disponibles";
		return nombre;
	}

	@RemoteProperty
	public List<Programa> getListaProgramas() {

		return listaProgramas;
	}

	public void setListaProgramas(List<Programa> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	@Override
	public Integer getAvance() {
		
		return null;
	}

	@Override
	public String getTipoNodo() {

		return null;
	}

}
