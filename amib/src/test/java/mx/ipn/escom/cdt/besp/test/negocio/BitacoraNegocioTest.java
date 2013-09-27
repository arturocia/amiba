package mx.ipn.escom.cdt.besp.test.negocio;

import java.util.List;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.modelo.Bitacora;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.negocio.BitacoraNegocio;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;
import mx.ipn.escom.cdt.besp.negocio.IndicadorNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.CDTUtil;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class BitacoraNegocioTest extends
		AbstractTransactionalTestNGSpringContextTests {
	Logger logger = Logger.getLogger(BitacoraNegocioTest.class);

	@Inject
	BitacoraNegocio bitacoraNegocio;

	@Test(enabled = true)
	public void caca() {
		List<List<Bitacora>> lists;
		lists = bitacoraNegocio.getRegistrosBitacoraPrograma(1);
		assert lists != null && lists.size() > 0;
	}

}
