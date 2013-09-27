package mx.ipn.escom.cdt.besp.util.exception;

public class AutenticarInterceptorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AutenticarInterceptorException() {
		super();
	}

	public AutenticarInterceptorException(String message, Throwable cause) {
		super(message, cause);
	}

	public AutenticarInterceptorException(String message) {
		super(message);
	}
	
	public AutenticarInterceptorException(Throwable cause) {
		super(cause);
	}
	
	public AutenticarInterceptorException(Exception other) {
		super(other.getMessage(),other.getCause());
		this.setStackTrace(other.getStackTrace());
	}
}
