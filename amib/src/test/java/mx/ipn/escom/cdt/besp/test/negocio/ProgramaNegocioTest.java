package mx.ipn.escom.cdt.besp.test.negocio;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.negocio.ProgramaNegocio;
import mx.ipn.escom.cdt.besp.modelo.Programa;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class ProgramaNegocioTest extends
		AbstractTransactionalTestNGSpringContextTests {
	@Inject
	ProgramaNegocio programaNegocio;
	Programa programa;
	
	@Test
	public void pruebaValidacionPrograma()
	{
		programa = programaNegocio.findById(9);
		//programa.setIdPrograma(1);
		/*if(programaNegocio.evaluaPeriodo(programa))
		{
			System.out.println("Programa valido");
		}
		else
		{
			System.out.println("Programa no valido");
		}*/
		//assert programaNegocio.evaluaPeriodo(programa);
		assert programaNegocio.borraPrograma(programa);
	}
}
