package org.hardcode.juf;

import org.hardcode.juf.status.UpdateInfo;

import java.util.HashMap;


/**
 * Interfaz a implementar por las actualizaciones
 *
 * @author Fernando Gonz�lez Cort�s
 */
public interface Installer {
    /**
     * M�todo invocado cuando se ha descargado el jar de actualizaci�n
     *
     * @param clientStatus Estado asignado por el cliente para la ejecuci�n de la actualizaci�n
     * @param status actualizaci�n residente en el cliente
     * @param listener escuchador del progreso de la actualizaci�n. Puede ser
     *        null
     *
     * @return DOCUMENT ME!
     */
    public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
        ProgressListener listener) throws InstallException;
}
