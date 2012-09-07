package org.hardcode.juf;

/**
 * Excepci�n gen�rica que indica un fallo en la ejecuci�n de una actualizaci�n
 */
public class InstallException extends Exception {
    /**
     *
     */
    public InstallException() {
        super();
    }

    /**
     * DOCUMENT ME!
     *
     * @param message
     */
    public InstallException(String message) {
        super(message);
    }

    /**
     * DOCUMENT ME!
     *
     * @param message
     * @param cause
     */
    public InstallException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * DOCUMENT ME!
     *
     * @param cause
     */
    public InstallException(Throwable cause) {
        super(cause);
    }
}
