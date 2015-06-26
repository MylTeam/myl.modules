package com.myl.exception;

/**
 * Excepcion que se lanza cuando no se puede establecer realizar una transaccion
 * 
 * @author axel
 * 
 */
public class MessageStoreInterceptorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1995342233588646186L;
	
	public MessageStoreInterceptorException() {
		super();
	}

	public MessageStoreInterceptorException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageStoreInterceptorException(String message) {
		super(message);
	}

	public MessageStoreInterceptorException(Throwable cause) {
		super(cause);
	}


}
