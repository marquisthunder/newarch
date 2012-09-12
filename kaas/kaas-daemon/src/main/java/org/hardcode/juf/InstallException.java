package org.hardcode.juf;

/**
 * install Exception 
 */
public class InstallException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *
     */
    public InstallException() {
        super();
    }

    /**
     *
     * @param message
     */
    public InstallException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public InstallException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public InstallException(Throwable cause) {
        super(cause);
    }
}
