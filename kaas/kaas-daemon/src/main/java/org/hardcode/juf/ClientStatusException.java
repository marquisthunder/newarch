/*
 * Created on 14-nov-2004
 */
package org.hardcode.juf;

/**
 * Excepci�n producida cuando no se puede acceder a la informaci�n de las actualizaciones en el cliente
 * 
 * @author Fernando Gonz�lez Cort�s
 */
public class ClientStatusException extends Exception {

	/**
	 * 
	 */
	public ClientStatusException() {
		super();
	}
	/**
	 * @param arg0
	 */
	public ClientStatusException(String arg0) {
		super(arg0);
	}
	/**
	 * @param arg0
	 * @param arg1
	 */
	public ClientStatusException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	/**
	 * @param arg0
	 */
	public ClientStatusException(Throwable arg0) {
		super(arg0);
	}
}
