package mx.ipn.escom.cdt.besp.test.negocio;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.modelo.Estructura;
import mx.ipn.escom.cdt.besp.modelo.Nodo;
import mx.ipn.escom.cdt.besp.modelo.Periodo;
import mx.ipn.escom.cdt.besp.negocio.EstructuraNegocio;
import mx.ipn.escom.cdt.besp.negocio.IndicadorNegocio;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.CDTUtil;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class EstructuraNegocioTest extends
		AbstractTransactionalTestNGSpringContextTests {

	Logger logger = Logger.getLogger(EstructuraNegocioTest.class);

	@Inject
	EstructuraNegocio estructuraNegocio;
	@Inject
	UsuarioNegocio usuarioNegocio;
	@Inject
	IndicadorNegocio indicadorNegocio;

	Nodo nodoRaiz = null;
/*
	@Test(enabled = false)
	public void init() {
		nodoRaiz = usuarioNegocio.findById(2);
		estructuraNegocio.recorrerArbol(nodoRaiz);
		logger.trace("La raiz del arbol es " + nodoRaiz);
	}
*/
	@Test
	public void pruebaEncuentraEstructuraEnArbol() {
		Estructura estructura;
		estructura = (Estructura) estructuraNegocio.findByIdEnArbol(35,
				nodoRaiz);
		logger.trace("La estructura encontrada es " + estructura);
		assert estructura != null;
	}

	@Test
	public void pruebaValidarPeriodoValidoIndefinido() {
		Estructura estructura;
		Estructura estructuraPadre;
		estructura = new Estructura();
		estructura.setPeriodo(new Periodo());
		estructuraPadre = (Estructura) estructuraNegocio.findByIdEnArbol(47,
				nodoRaiz);
		estructura.setNodoPadre(estructuraPadre);
		assert estructuraNegocio.validaPeriodo(estructura, nodoRaiz);
	}

	@Test
	public void pruebaValidarPeriodoValidoFechaInicialInvalida() {
		Estructura estructura;
		Estructura estructuraPadre;
		estructura = new Estructura();
		estructura.setPeriodo(new Periodo());
		estructura.getPeriodo().setFechaInicio(
				CDTUtil.getFechaDeFormatoCorto("2011-09-06"));
		estructuraPadre = (Estructura) estructuraNegocio.findByIdEnArbol(47,
				nodoRaiz);
		estructura.setNodoPadre(estructuraPadre);
		assert !estructuraNegocio.validaPeriodo(estructura, nodoRaiz);
	}

	@Test
	public void pruebaValidarPeriodoValidoFechaInicialValida() {
		Estructura estructura;
		Estructura estructuraPadre;
		estructura = new Estructura();
		estructura.setPeriodo(new Periodo());
		estructura.getPeriodo().setFechaInicio(
				CDTUtil.getFechaDeFormatoCorto("2011-09-08"));
		estructuraPadre = (Estructura) estructuraNegocio.findByIdEnArbol(47,
				nodoRaiz);
		estructura.setNodoPadre(estructuraPadre);
		assert estructuraNegocio.validaPeriodo(estructura, nodoRaiz);
	}

	@Test
	public void pruebaValidarPeriodoValidoFechasInvalida() {
		Estructura estructura;
		Estructura estructuraPadre;
		estructura = new Estructura();
		estructura.setPeriodo(new Periodo());
		estructura.getPeriodo().setFechaInicio(
				CDTUtil.getFechaDeFormatoCorto("2011-09-08"));
		estructura.getPeriodo().setFechaFin(
				CDTUtil.getFechaDeFormatoCorto("2012-09-08"));
		estructuraPadre = (Estructura) estructuraNegocio.findByIdEnArbol(47,
				nodoRaiz);
		estructura.setNodoPadre(estructuraPadre);
		assert !estructuraNegocio.validaPeriodo(estructura, nodoRaiz);
	}

	@Test
	public void pruebaValidarPeriodoValidoFechasValida() {
		Estructura estructura;
		Estructura estructuraPadre;
		estructura = new Estructura();
		estructura.setPeriodo(new Periodo());
		estructura.getPeriodo().setFechaInicio(
				CDTUtil.getFechaDeFormatoCorto("2011-09-08"));
		estructura.getPeriodo().setFechaFin(
				CDTUtil.getFechaDeFormatoCorto("2011-09-09"));
		estructuraPadre = (Estructura) estructuraNegocio.findByIdEnArbol(47,
				nodoRaiz);
		estructura.setNodoPadre(estructuraPadre);
		assert estructuraNegocio.validaPeriodo(estructura, nodoRaiz);
	}

	@Test
	public void pruebaValidarPeriodoValidoFechasInvalidaPorDuracion() {
		Estructura estructura;
		Estructura estructuraPadre;
		estructura = new Estructura();
		estructura.setPeriodo(new Periodo());
		estructura.getPeriodo().setFechaInicio(
				CDTUtil.getFechaDeFormatoCorto("2011-09-08"));
		estructura.getPeriodo().setFechaFin(
				CDTUtil.getFechaDeFormatoCorto("2012-09-22"));
		estructuraPadre = (Estructura) estructuraNegocio.findByIdEnArbol(47,
				nodoRaiz);
		estructura.setNodoPadre(estructuraPadre);
		assert !estructuraNegocio.validaPeriodo(estructura, nodoRaiz);
	}

	@Test
	public void xEstructura() {
		Estructura estructura;
		estructura = estructuraNegocio.findById(19);
		estructuraNegocio.validarNivel(estructura);
	}

	// | 43 | 76 | 2011-09-07 | NULL | 20 |
	// | 44 | 76 | 2011-09-07 | NULL | 20 |
	// | 45 | 77 | NULL | NULL | 15 |
	// | 46 | 78 | NULL | NULL | 14 |
	// | 47 | 79 | NULL | NULL | 0 |

	@Test
	public void prueba() {
		Integer idIndicador = 1;

		if (indicadorNegocio.validarIndicadorSinAvances(idIndicador))
			System.out.println("se puede eliminar");
		else
			System.out.println("no se puede eliminar");

	}

}
