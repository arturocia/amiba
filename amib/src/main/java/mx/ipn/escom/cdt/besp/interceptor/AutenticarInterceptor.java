package mx.ipn.escom.cdt.besp.interceptor;

import mx.ipn.escom.cdt.besp.util.NombreObjetosSesion;
import mx.ipn.escom.cdt.besp.util.exception.AutenticarInterceptorException;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContextFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AutenticarInterceptor implements Interceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger loggerInstancia = Logger.getLogger(this.getClass());
	private String prevAction;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws AutenticarInterceptorException{
		String previous = null;
		if (actionInvocation.getProxy().getActionName().equals("login") || actionInvocation.getProxy().getActionName().equals("consulta-general") || actionInvocation.getProxy().getActionName().equals("recuperar-password")) {
			try {
				actionInvocation.invoke();
				if (ActionContext.getContext().getSession()
						.get(NombreObjetosSesion.USUARIO) == null) {
					return Action.LOGIN;
				} else {
					return "next";
				}
			} catch (Exception e) {
				throw new AutenticarInterceptorException(e);
			}
		} else {
			if (ActionContext.getContext().getSession()
					.get(NombreObjetosSesion.USUARIO) == null) {
				loggerInstancia.debug("No esta loggueado."
						+ ActionContext.getContext().getSession()
								.get(NombreObjetosSesion.USUARIO));

				setPrevAction(actionInvocation.getProxy().getActionName());

				ActionContext.getContext().getSession()
						.put("prevAction", getPrevAction());
				return Action.LOGIN;
			} else {
				loggerInstancia.debug("Loggueado correctamente."
						+ ActionContext.getContext().getSession()
								.get(NombreObjetosSesion.USUARIO));
				if (WebContextFactory.get() != null) {
					loggerInstancia.trace("Listo");
				}
				previous = (String) ActionContext.getContext().getSession()
						.get("prevAction");
				if (previous != null) {
					ActionContext.getContext().getSession()
							.put("current", previous);
					ActionContext.getContext().getSession()
							.put("prevAction", null);
					return "next";
				} else {
					try{
						return actionInvocation.invoke();
					} catch (Exception e) {
						throw new AutenticarInterceptorException(e);
					}
				}

			}
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	public String getPrevAction() {
		return prevAction;
	}

	public void setPrevAction(String prevAction) {
		this.prevAction = prevAction;
	}

}
