package mx.ipn.escom.cdt.besp.test.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class AccionNegocioTest extends
AbstractTransactionalTestNGSpringContextTests{
	
	Accion accion;
	Logger logger = Logger.getLogger(AccionNegocioTest.class);
	@Inject
	AccionNegocio accionNegocio;

	@Test
	public void pruebaValidacionIndicador()
	{
		List<Accion> lista= new ArrayList<Accion>();
		accion= accionNegocio.findById(35);
		logger.trace("Avance:"+accion.getAvance());
		System.out.println("Avance S "+accion.getAvance());
		
		
		
	}
}
