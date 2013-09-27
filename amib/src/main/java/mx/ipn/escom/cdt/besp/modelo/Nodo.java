package mx.ipn.escom.cdt.besp.modelo;

import java.util.List;

public interface Nodo {

	<N extends Nodo> List<N> getNodosHijo();

	<N extends Nodo> List<N> getNodosHijoInicializar();

	Nodo getNodoPadre();

	Periodo getPeriodo();

	Integer getId();

	void setNodoPadre(Nodo nodo);

	String getNombre();
	
	Integer getAvance();

	String getTipoNodo();
}
