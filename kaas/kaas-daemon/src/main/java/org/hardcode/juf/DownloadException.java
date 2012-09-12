/*
 * Created on 14-nov-2004
 */
package org.hardcode.juf;

/**
 * Download exception
 * 
 * @author kaas
 */
public class DownloadException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public DownloadException() {
		super();
	}
	/**
	 * @param arg0
	 */
	public DownloadException(String arg0) {
		super(arg0);
	}
	/**
	 * @param arg0
	 * @param arg1
	 */
	public DownloadException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	/**
	 * @param arg0
	 */
	public DownloadException(Throwable arg0) {
		super(arg0);
	}
}
