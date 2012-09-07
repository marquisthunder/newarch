package org.hardcode.juf;

import org.hardcode.juf.status.UpdateInfo;

import java.util.HashMap;


/**
 * Interfaz a implementar por las actualizaciones
 *
 * @author Fernando Gonzlez Cort
 */
public interface Installer { 
    /**
     * M�todo invocado cuando se ha descargado el jar de actualizaci
     *
     * @param clientStatus Estado asignado por el cliente para la ejecuc de la actualizaci�n
     * @param status actualizacin residente en el cliente
     * @param listener escuchador del progreso de la actualizaci Puede ser
     *        null
     *
     * @return DOCUMENT ME!
     */
    public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
        ProgressListener listener) throws InstallException;
}
