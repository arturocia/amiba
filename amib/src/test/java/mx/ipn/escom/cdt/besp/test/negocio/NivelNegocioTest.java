package mx.ipn.escom.cdt.besp.test.negocio;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.modelo.Nivel;
import mx.ipn.escom.cdt.besp.negocio.NivelNegocio;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/main/resources/applicationContext.xml" })
public class NivelNegocioTest extends
		AbstractTransactionalTestNGSpringContextTests {
	@Inject
	NivelNegocio nivelNegocio;

	@Test
	public void pruebaEliminarPrograma() {
		Nivel nivel = nivelNegocio.findById(55);
		assert nivelNegocio.puedeSerEliminado(nivel);
	}
}
