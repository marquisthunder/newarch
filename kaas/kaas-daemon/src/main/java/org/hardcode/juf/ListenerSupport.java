/*
 * Created on 13-nov-2004
 */
package org.hardcode.juf;

/**
 * @author Fernando G
 */
interface ListenerSupport {

	public void addProgressListener(ProgressListener listener);
	public void notifyProgress(int progress);

	public void notifyFileProgress(int progress);

}
