package mx.ipn.escom.cdt.besp.test.negocio;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;
import mx.ipn.escom.cdt.besp.negocio.PeriodoNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class PeriodoNegocioTest extends
		AbstractTransactionalTestNGSpringContextTests {
	Logger logger = Logger.getLogger(PeriodoNegocioTest.class);
	
	@Inject
	PeriodoNegocio periodoNegocio;
	@Inject
	EstructuraNegocio estructuraNegocio;
	@Inject
	ProyectoNegocio proyectoNegocio;
	@Inject
	AccionNegocio accionNegocio;
	/*
	@Test
	public void caca() {
		Calendar xmas = new GregorianCalendar(2011, Calendar.DECEMBER, 25);
	    Date date1 = xmas.getTime();
	    Calendar jan = new GregorianCalendar(2011, Calendar.JANUARY, 25);
	    Date date2 = jan.getTime();
	    
		Periodo periodo=new Periodo();
		periodo.setFechaFin(date1);
		periodo.setFechaInicio(date2);
		
		periodo.setDuracion(null);
		System.out.println("Perido mierda " + periodoNegocio);
		assert !periodoNegocio.validaBienFormado(periodo);
	}
	*/
	
/*	@Test 
	public void prueb(){
		Estructura estructura;										
		estructura= estructuraNegocio.findById(25);
		Accion accion;
		accion= accionNegocio.findById(1);
		System.out.println(periodoNegocio.validaNoIndefinidoRelativo(accion));
		
	}*/
	
	/*@Test
	public void pruebaMetodo(){
		Accion accion = accionNegocio.findById(5);
		assert periodoNegocio.getNodoPadreConPeriodoRestrictivo(accion) != null;
	}*/
	
	@Test
	public void pruebaMetodo(){
		Estructura estructuraX = estructuraNegocio.findById(26);
		Estructura estructuraY = estructuraNegocio.findById(42);
		Proyecto proyectoPrueba = proyectoNegocio.findById(40);
		
		proyectoPrueba.getEstructuras().add(estructuraX);
		proyectoPrueba.getEstructuras().add(estructuraY);
		
		logger.trace("EstructuraX: " + estructuraX);
		logger.trace("EstructuraY: " + estructuraY);
		logger.trace("nodoPrueba:::" + proyectoPrueba);
		Nodo nodoRestrictivo = periodoNegocio.getNodoPadreConPeriodoRestrictivo(proyectoPrueba);
		
		logger.trace("Nodo devuelto: " + nodoRestrictivo);
		assert nodoRestrictivo != null;
	}
	
}
