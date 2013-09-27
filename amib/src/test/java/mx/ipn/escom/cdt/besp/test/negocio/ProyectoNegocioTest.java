package mx.ipn.escom.cdt.besp.test.negocio;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.modelo.*;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class ProyectoNegocioTest extends
AbstractTransactionalTestNGSpringContextTests{
	@Inject
	ProyectoNegocio proyectonegocio;
	Proyecto proyecto;
	Logger logger = Logger.getLogger(ProgramaNegocioTest.class);
	
	@Test(enabled= false)
	public void pruebaProyecto()
	{
		proyecto = proyectonegocio.findById(8);
		assert proyectonegocio.validaSiglasUnicas(proyecto);
	}
	
	@Test(enabled=false)
	public void probarMapeoEjesMarado(){
	  proyecto = proyectonegocio.findById(2);
	  assert proyectonegocio.traerTemas(proyecto).size()>0;
	}
	
	@Test (enabled = false)
	public void probarMapeoTemasTransversales(){
		proyecto = proyectonegocio.findById(1);
		for (EjeTematico ejeTematico : proyectonegocio.traerEjes(proyecto)) {
			logger.trace(ejeTematico.getIdEje() + "puta madre");
		}
		assert proyectonegocio.traerEjes(proyecto).size()>0;
		
		
	}
	
	@Test (enabled = false)
	public void probarRN33(){
		proyecto = proyectonegocio.findById(34);
		proyecto.setNombre("Detección y supresión de fugas");
		//proyecto.setIdProyecto(null);
		assert proyectonegocio.validaNombreUnico(proyecto);
	}
	
	@Test
	public void probarCantidades(){
		Integer cantidad = 0; 
		proyecto = proyectonegocio.findById(34);
		System.out.println("ID proyecto: "+proyecto.getIdProyecto());
		cantidad = proyecto.getRestriccionesAtendidas();
		System.out.println("Cantidad atendidas: "+cantidad);
		cantidad = proyecto.getRestriccionesTurnadas();
		System.out.println("Cantidad turnadas : "+cantidad);
		cantidad = proyecto.getRestriccionesNoAtendidas();
		System.out.println("Cantidad No atendidas: "+cantidad);
		assert cantidad != null;
	}
	

}
