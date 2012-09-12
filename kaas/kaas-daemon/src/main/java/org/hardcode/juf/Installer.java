package org.hardcode.juf;

import org.hardcode.juf.status.UpdateInfo;

import java.util.HashMap;


/**
 * 
 *
 * @author kaas
 */
public interface Installer { 
    /**
     * Method invoked when you downloaded the upgrade jar
     *
     * @param State clientStatus assigned by the client for the upgrade execution
     * @param resident status in the client upgrade
     * @param listener listener progress of the update can be null
     *
     * @return UpdateInfo
     */
    public UpdateInfo install(HashMap clientStatus, UpdateInfo status,
        ProgressListener listener) throws InstallException;
}
