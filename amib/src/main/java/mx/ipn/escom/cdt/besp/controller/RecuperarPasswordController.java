package mx.ipn.escom.cdt.besp.controller;

import java.util.List;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionSupport;

import mx.ipn.escom.cdt.besp.negocio.UsuarioNegocio;
import mx.ipn.escom.cdt.besp.util.exception.MailFormatError;
import mx.ipn.escom.cdt.besp.modelo.Usuario;

@Results({ @Result(name = "success", location = "login.jsp") })
public class RecuperarPasswordController extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -854223975876731622L;
	private String userId;
	private Logger loggerInstancia = Logger.getLogger(this.getClass());

	private UsuarioNegocio usuarioNegocio;

	// excepcion para el correo invalido

	@SkipValidation
	public HttpHeaders index() {
		return new DefaultHttpHeaders("index").disableCaching();
	}

	@Override
	public String execute(){

		Usuario usuario = new Usuario();
		usuario.setLogin(userId);
		Pattern pattern;
		Matcher matcher;
		String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		List<Usuario> usuarios = usuarioNegocio.findByExample(usuario);

		if (usuarios.isEmpty()) {
			clearErrorsAndMessages();
			addActionError(getText("recuperarError"));
			return ERROR;
		} else {
			clearErrorsAndMessages();

			try {
				Usuario usuarioRecuperado = usuarios.get(0);
				String correoDestinatario = usuarioRecuperado.getContactos()
						.get(1).getContacto();

				loggerInstancia.trace("Correo recuperado: "
						+ correoDestinatario);
				String mailServidor = "contactosirs@gmail.com";
				String passwordServidor = "escomcdt";
				// Propiedades de la conexión.
				Properties props = new Properties();
				props.setProperty("mail.smtp.host", "smtp.gmail.com");
				props.setProperty("mail.smtp.starttls.enable", "true");
				props.setProperty("mail.smtp.port", "587");
				props.setProperty("mail.smtp.user", mailServidor);
				props.setProperty("mail.smtp.auth", "true");
				Session session = Session.getDefaultInstance(props);

				// VALIDACION DEL CORREO EN CONTACTO antes de recuperar password
				// aqui la expresión regular se evalua
				pattern = Pattern.compile(emailPattern);
				// el contacto se verifica con la expresion regular
				matcher = pattern.matcher(correoDestinatario);
				// si el resultado de la validacion es falso lanza el error
				if (!matcher.matches()) {
					addActionError(getText("recuperarCorreoError"));
					throw new MailFormatError();
				}

				// Se construye el mensaje a enviar
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(mailServidor));
				message.addRecipients(Message.RecipientType.TO,
						InternetAddress.parse(correoDestinatario));
				message.setSubject("Información sobre su cuenta.");
				message.setText("Usuario: " + usuarioRecuperado.getLogin()
						+ " Contraseña: " + usuarioRecuperado.getPsw());

				// Lo enviamos.
				Transport t = session.getTransport("smtp");
				t.connect(mailServidor, passwordServidor);
				t.sendMessage(message, message.getAllRecipients());

				// Cierre.
				t.close();
				addActionError(getText("recuperarCorreo"));
			} catch (NoSuchProviderException e) {
				loggerInstancia.error(e.getMessage());
				e.getMessage();

			} catch (AddressException e) {
				loggerInstancia.error(e.getMessage());
				e.getMessage();

			} catch (MessagingException e) {
				loggerInstancia.error(e.getMessage());
				e.getMessage();

			}  catch (MailFormatError e) {
				loggerInstancia.error(e.getMessage());
				e.getMessage();

			}
			loggerInstancia.trace("Retorna SUCCESS");
			return SUCCESS;
		}
	}

	public String create() {
		return execute();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UsuarioNegocio getUsuarioNegocio() {
		return usuarioNegocio;
	}

	public void setUsuarioNegocio(UsuarioNegocio usuarioNegocio) {
		this.usuarioNegocio = usuarioNegocio;
	}
}