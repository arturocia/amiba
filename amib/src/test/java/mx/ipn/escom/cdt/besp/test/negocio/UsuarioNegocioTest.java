package mx.ipn.escom.cdt.besp.test.negocio;

import java.util.List;

import javax.inject.Inject;

import mx.ipn.escom.cdt.besp.modelo.Usuario;
import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;

import org.apache.log4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "file:src/test/resources/applicationContext.xml" })
public class UsuarioNegocioTest extends
		AbstractTransactionalTestNGSpringContextTests {
	Logger logger = Logger.getLogger(UsuarioNegocioTest.class);

	@Inject
	UsuarioNegocio usuarioNegocio;

	@Test(enabled = true)
	public void caca() {
		List<Usuario> usuarios;
		Usuario usuario;
		usuario = usuarioNegocio.findById(1);
		usuarios = usuarioNegocio.getUsuariosDisponibles(usuario);
		System.out.println("el usuario es:" + usuario);
		logger.trace("Los usuarios encontrados son " + usuarios);
		assert usuarios.size() > 0;
	}

}
