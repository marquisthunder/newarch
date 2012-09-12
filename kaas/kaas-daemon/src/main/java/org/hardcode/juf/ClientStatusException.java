/*
 * Created on 14-nov-2004
 */
package org.hardcode.juf;

/**
 * 
 * @author kaas
 */
public class ClientStatusException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
