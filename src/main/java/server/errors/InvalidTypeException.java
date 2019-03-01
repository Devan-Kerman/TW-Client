package server.errors;

public class InvalidTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4846021776695856025L;

	public InvalidTypeException() {

	}

	public InvalidTypeException(String message) {
		super(message);

	}

	public InvalidTypeException(Throwable cause) {
		super(cause);

	}

	public InvalidTypeException(String message, Throwable cause) {
		super(message, cause);

	}

	public InvalidTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
