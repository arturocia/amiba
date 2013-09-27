package mx.ipn.escom.cdt.besp.test.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.negocio.AccionNegocio;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;
import mx.ipn.escom.cdt.besp.negocio.IndicadorNegocio;
import mx.ipn.escom.cdt.besp.negocio.ProyectoNegocio;
import mx.ipn.escom.cdt.besp.modelo.Accion;
import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Indicador;
import mx.ipn.escom.cdt.besp.modelo.Proyecto;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class IndicadorNegocioTest extends
AbstractTransactionalTestNGSpringContextTests {
	
	Logger logger = Logger.getLogger(IndicadorNegocioTest.class);
	
	@Inject
	IndicadorNegocio indicadorNegocio;
	@Inject
	AccionNegocio accionNegocio;
	@Inject
	ProyectoNegocio proyectoNegocio;
	@Inject
	EstructuraNegocio estructuraNegocio;
	Indicador indicador;
	Accion accion;
	Proyecto proyecto;
	Estructura estructura;
	
	//@Test
	public void pruebaValidacionIndicador()
	{
		estructura=estructuraNegocio.findById(11);
		logger.trace(estructura.getAvance());
		
	}
	
	@Test
	public void pruebaGetPorcentaje(){
		List<Indicador> lista=new ArrayList<Indicador>();
		Indicador indi=new Indicador();
		indi.setIdProyecto(258);
		lista=	indicadorNegocio.findByExample(indi);
		for(Indicador aux:lista){
			//aux.getPorcentaje();
			System.out.println("IdIndicador:"+aux.getIdIndicador()+" porcentaje "+aux.getPorcentaje()+" idAccion "+aux.getIdAccion());
				
		}
		
	}
}
