package com.thinkingtop.kaas.etl.database;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.PoolUtil;
import com.thinkingtop.kaas.etl.reader.EtlInfoReader;

//org.apache.commons.dbcp.PoolableConnectionFactory.UNKNOWN_TRANSACTIONISOLATION=-1
public class KaasBoneCPDataSource extends KaasBoneCPConfig implements DataSource {
	/** Serialization UID. */
	private static final long serialVersionUID = 1L;
	/** Config setting. */
	private PrintWriter logWriter = null;
	/** Pool handle. */
	private transient volatile BoneCP pool = null;
	/** Lock for init. */
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	/** JDBC driver to use. */
	private String driverClass;
	/** Class logger. */
	private static final Logger logger = LoggerFactory.getLogger(KaasBoneCPDataSource.class);
	/**
	 * Default empty constructor.
	 *
	 */
	public KaasBoneCPDataSource() {
		// default constructor
	}
	/**
	 * 
	 *
	 * @param config
	 */
	public KaasBoneCPDataSource(KaasBoneCPConfig config) {
		Field[] fields = KaasBoneCPConfig.class.getDeclaredFields();
		for (Field field: fields){
			try {
				field.setAccessible(true);
				field.set(this, field.get(config));
			} catch (Exception e) {
				// should never happen
			}
		}
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		if (this.pool == null){
			maybeInit();
		}
		return this.pool.getConnection();
	}

	/**
	 * Close the datasource. 
	 *
	 */
	public void close(){
		if (this.pool != null){
			this.pool.shutdown();
		}
	}

	/**
	 * @throws SQLException 
	 * 
	 *
	 */
	private void maybeInit() throws SQLException {
		this.rwl.readLock().lock();
		if (this.pool == null){
			this.rwl.readLock().unlock();
			this.rwl.writeLock().lock();
			if (this.pool == null){ //read might have passed, write might not
				try {
					if (this.getDriverClass() != null){
						loadClass(this.getDriverClass());
					}
				}
				catch (ClassNotFoundException e) {
					throw new SQLException(PoolUtil.stringifyException(e));
				}


				logger.debug(this.toString());

				this.pool = new BoneCP(this);
			}

			this.rwl.writeLock().unlock(); // Unlock write
		} else {
			this.rwl.readLock().unlock(); // Unlock read
		}
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	public Connection getConnection(String username, String password)
	throws SQLException {
		throw new UnsupportedOperationException("getConnectionString username, String password) is not supported");
	}

	/**
	 * Retrieves the log writer for this DataSource object.
	 * 
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return this.logWriter;
	}

	/**
	 * Gets the maximum time in seconds that this data source can wait while attempting to connect to a database. 
	 * A value of zero means that the timeout is the default system timeout if there is one; otherwise, it means that there is no timeout. When a DataSource object is created, the login timeout is initially zero.
	 * 
	 */
	public int getLoginTimeout()
	throws SQLException {
		throw new UnsupportedOperationException("getLoginTimeout is unsupported.");
	}

	/**
	* Sets the log writer for this DataSource object to the given java.io.PrintWriter object.
	 */
	public void setLogWriter(PrintWriter out)
	throws SQLException {
		this.logWriter = out;
	}

	/**
	* Sets the maximum time in seconds that this data source will wait while 
	* attempting to connect to a database. A value of zero specifies that the timeout is the default 
	* system timeout if there is one; otherwise, it specifies that there is no timeout. When a DataSource object is created, the login timeout is initially zero.
	 */
	public void setLoginTimeout(int seconds)
	throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout is unsupported.");
	}

	/**
	 * Returns true if this either implements the interface argument or is directly or indirectly a wrapper for an object that does.
	 * @param arg0 class
	 * @return t/f
	 * @throws SQLException on error
	 *
	 */
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	/**
	 * Returns an object that implements the given interface to allow access to non-standard methods, 
	 * or standard methods not exposed by the proxy.
	 * @param arg0 obj
	 * @return unwrapped object
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public Object unwrap(Class arg0) throws SQLException {
		return null;
	}


	/**
	 * Gets driver class set in config. 
	 *
	 * @return Driver class set in config
	 */
	public String getDriverClass() {
		return this.driverClass;
	}


	/**
	 * Sets driver to use (called via reflection).
	 *
	 * @param driverClass Driver to use
	 */
	public void setDriverClass(String driverClass) {
		EtlInfoReader reader = EtlInfoReader.getInstance();
		Map<String,String> map =(Map<String,String>) reader.getDatasourceParameters();
	    this.driverClass = map.get("driverClass");
	}


	/**
	 * Returns the total leased connections.
	 *
	 * @return total leased connections
	 */
	public int getTotalLeased() {
		return this.pool.getTotalLeased();
	}

	/** Returns a configuration object built during initialization of the connection pool. 
	 *   
	 * @return the config
	 */
	public KaasBoneCPConfig getConfig() {
		return this;
	}
	/**
	 * since jdk1.7
	 */
	@Override
	public java.util.logging.Logger getParentLogger()
			throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
}