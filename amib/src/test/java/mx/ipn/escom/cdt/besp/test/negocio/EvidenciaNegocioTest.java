
package mx.ipn.escom.cdt.besp.test.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.negocio.*;
import mx.ipn.escom.cdt.besp.modelo.*;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;



@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class EvidenciaNegocioTest extends AbstractTransactionalTestNGSpringContextTests {
	
		@Inject
		EvidenciaNegocio evidenciaNegocio;
		Proyecto proyecto;
		Logger logger = Logger.getLogger(EvidenciaNegocioTest.class);
		List<Integer> idAreas;
		List<Evidencia> evidencias;
 		
		@Test
		public void pruebaTraerEvidencias(){
			idAreas=new ArrayList<Integer>();
		  idAreas.add(1);

		  evidencias=evidenciaNegocio.getEvidencias(null,null,null, idAreas);
		    logger.trace("las evidencias son "+evidencias);
		}
	
	
}
