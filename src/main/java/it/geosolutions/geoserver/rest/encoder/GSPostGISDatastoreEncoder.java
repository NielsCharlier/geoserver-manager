/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007,2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package it.geosolutions.geoserver.rest.encoder;

import it.geosolutions.geoserver.rest.encoder.utils.NestedElementEncoder;
import it.geosolutions.geoserver.rest.encoder.utils.PropertyXMLEncoder;

/**
 * Geoserver datastore XML encoder.
 *
 * @author Eric Grosso
 * @author ETj
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * @deprecated Will be removed in next version 1.5.x.
 *   Use {@link it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder} instead.
 * @version $Id: $
 */
public class GSPostGISDatastoreEncoder extends PropertyXMLEncoder {

    private NestedElementEncoder connectionParameters = new NestedElementEncoder("connectionParameters");

    /**
     * <p>Constructor for GSPostGISDatastoreEncoder.</p>
     */
    public GSPostGISDatastoreEncoder() {
        super("dataStore");
        addContent(connectionParameters.getRoot());
        
        addType("PostGIS"); // may be overwritten with e.g. "PostGIS (JNDI)"
        addDatabaseType("postgis");
    }
    
    /**
     * Set some initial defaults.
     * The default parameters are as follows: <ul>
     * <li>maximum connections: 10, </li>
     * <li>minimum connections: 1,</li>
     * <li>fetch size: 1000, </li>
     * <li>connection timeout: 20 seconds, </li>
     * <li>loose BBox: true, </li>
     * <li>prepared statements: false,</li>
     * <li>maximum open prepared statements: 50.    </li>
     * </ul>
     */
    public void defaultInit() {
        setMinConnections(1);
        setMaxConnections(10);
        setFetchSize(1000);
        setConnectionTimeout(20);
        setLooseBBox(true);
        setPreparedStatements(false);
        setMaxOpenPreparedStatements(50);
    }

    /**
     * <p>addName</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    protected void addName(String name) {
        add("name", name);
    }
    
    /**
     * <p>setName</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        set("name", name);
    }

    /**
     * <p>addDescription</p>
     *
     * @param description a {@link java.lang.String} object.
     */
    protected void addDescription(String description) {
        add("description", description);
    }
    
    /**
     * <p>setDescription</p>
     *
     * @param description a {@link java.lang.String} object.
     */
    public void setDescription(String description) {
        set("description", description);
    }

	/**
	 * <p>addType</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 */
	protected void addType(String type) {
        add("type", type);
    }
    
    /**
     * <p>setType</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    public void setType(String type) {
        set("type", type);
    }

	/**
	 * <p>addEnabled</p>
	 *
	 * @param enabled a boolean.
	 */
	protected void addEnabled(boolean enabled) {
        add("enabled", Boolean.toString(enabled));
    }
    
    /**
     * <p>setEnabled</p>
     *
     * @param enabled a boolean.
     */
    public void setEnabled(boolean enabled) {
        set("enabled", Boolean.toString(enabled));
    }
    
	/**
	 * <p>addNamespace</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 */
	protected void addNamespace(String namespace) {
        connectionParameters.add("namespace", namespace);
    }
    
    /**
     * <p>setNamespace</p>
     *
     * @param namespace a {@link java.lang.String} object.
     */
    public void setNamespace(String namespace) {
        connectionParameters.set("namespace", namespace);
    }
    
    /**
     * <p>addHost</p>
     *
     * @param host a {@link java.lang.String} object.
     */
    protected void addHost(String host) {
        connectionParameters.add("host", host);
    }
    
    /**
     * <p>setHost</p>
     *
     * @param host a {@link java.lang.String} object.
     */
    public void setHost(String host) {
        connectionParameters.set("host", host);
    }

    /**
     * <p>addPort</p>
     *
     * @param port a int.
     */
    protected void addPort(int port) {
        connectionParameters.add("port", Integer.toString(port));
    }
    
    /**
     * <p>setPort</p>
     *
     * @param port a int.
     */
    public void setPort(int port) {
        connectionParameters.set("port", Integer.toString(port));
    }

    /**
     * <p>addDatabase</p>
     *
     * @param database a {@link java.lang.String} object.
     */
    protected void addDatabase(String database) {
        connectionParameters.add("database", database);
    }
    
    /**
     * <p>setDatabase</p>
     *
     * @param database a {@link java.lang.String} object.
     */
    public void setDatabase(String database) {
        connectionParameters.set("database", database);
    }

    /**
     * <p>addSchema</p>
     *
     * @param schema a {@link java.lang.String} object.
     */
    protected void addSchema(String schema) {
        connectionParameters.add("schema", schema);
    }
    
    /**
     * <p>setSchema</p>
     *
     * @param schema a {@link java.lang.String} object.
     */
    public void setSchema(String schema) {
        connectionParameters.set("schema", schema);
    }

    /**
     * <p>addUser</p>
     *
     * @param user a {@link java.lang.String} object.
     */
    protected void addUser(String user) {
        connectionParameters.add("user", user);
    }
    
    /**
     * <p>setUser</p>
     *
     * @param user a {@link java.lang.String} object.
     */
    public void setUser(String user) {
        connectionParameters.set("user", user);
    }

    /**
     * <p>addPassword</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    protected void addPassword(String password) {
        connectionParameters.add("passwd", password);
    }
    
    /**
     * <p>setPassword</p>
     *
     * @param password a {@link java.lang.String} object.
     */
    public void setPassword(String password) {
        connectionParameters.set("passwd", password);
    }

    /**
     * <p>addDatabaseType</p>
     *
     * @param dbtype a {@link java.lang.String} object.
     */
    protected void addDatabaseType(String dbtype) {
        connectionParameters.add("dbtype", dbtype);
    }

    /**
     * <p>setDatabaseType</p>
     *
     * @param dbtype a {@link java.lang.String} object.
     */
    public void setDatabaseType(String dbtype) {
        connectionParameters.set("dbtype", dbtype);
    }

    /**
     * <p>addJndiReferenceName</p>
     *
     * @param jndiReferenceName a {@link java.lang.String} object.
     */
    protected void addJndiReferenceName(String jndiReferenceName) {
        connectionParameters.add("jndiReferenceName", jndiReferenceName);
    }
    
    /**
     * <p>setJndiReferenceName</p>
     *
     * @param jndiReferenceName a {@link java.lang.String} object.
     */
    public void setJndiReferenceName(String jndiReferenceName) {
        connectionParameters.set("jndiReferenceName", jndiReferenceName);
    }

    /**
     * <p>addExposePrimaryKeys</p>
     *
     * @param exposePrimaryKeys a boolean.
     */
    protected void addExposePrimaryKeys(boolean exposePrimaryKeys) {
    	connectionParameters.add("Expose primary keys", Boolean.toString(exposePrimaryKeys));
    }
    
    /**
     * <p>setExposePrimaryKeys</p>
     *
     * @param exposePrimaryKeys a boolean.
     */
    public void setExposePrimaryKeys(boolean exposePrimaryKeys) {
    	connectionParameters.set("Expose primary keys", Boolean.toString(exposePrimaryKeys));
    }
    
    /**
     * <p>addMaxConnections</p>
     *
     * @param maxConnections a int.
     */
    protected void addMaxConnections(int maxConnections) {
    	connectionParameters.add("max connections", Integer.toString(maxConnections));
    }
    
    /**
     * <p>setMaxConnections</p>
     *
     * @param maxConnections a int.
     */
    public void setMaxConnections(int maxConnections) {
    	connectionParameters.set("max connections", Integer.toString(maxConnections));
    }
    
    /**
     * <p>addMinConnections</p>
     *
     * @param minConnections a int.
     */
    protected void addMinConnections(int minConnections) {
    	connectionParameters.add("min connections", Integer.toString(minConnections));
    }
    
    /**
     * <p>setMinConnections</p>
     *
     * @param minConnections a int.
     */
    public void setMinConnections(int minConnections) {
    	connectionParameters.set("min connections", Integer.toString(minConnections));
    }
    
    /**
     * <p>addFetchSize</p>
     *
     * @param fetchSize a int.
     */
    protected void addFetchSize(int fetchSize) {
    	connectionParameters.add("fetch size", Integer.toString(fetchSize));
    }
    
    /**
     * <p>setFetchSize</p>
     *
     * @param fetchSize a int.
     */
    public void setFetchSize(int fetchSize) {
    	connectionParameters.set("fetch size", Integer.toString(fetchSize));
    }
    
    /**
     * <p>addConnectionTimeout</p>
     *
     * @param seconds a int.
     */
    protected void addConnectionTimeout(int seconds) {
    	connectionParameters.add("Connection timeout", Integer.toString(seconds));
    }
    
    /**
     * <p>setConnectionTimeout</p>
     *
     * @param seconds a int.
     */
    public void setConnectionTimeout(int seconds) {
    	connectionParameters.set("Connection timeout", Integer.toString(seconds));
    }
    
    /**
     * <p>addValidateConnections</p>
     *
     * @param validateConnections a boolean.
     */
    protected void addValidateConnections(boolean validateConnections) {
    	connectionParameters.add("validate connections", Boolean.toString(validateConnections));
    }
    
    /**
     * <p>setValidateConnections</p>
     *
     * @param validateConnections a boolean.
     */
    public void setValidateConnections(boolean validateConnections) {
    	connectionParameters.set("validate connections", Boolean.toString(validateConnections));
    }
    
    /**
     * <p>addPrimaryKeyMetadataTable</p>
     *
     * @param primaryKeyMetadataTable a {@link java.lang.String} object.
     */
    protected void addPrimaryKeyMetadataTable(String primaryKeyMetadataTable) {
    	connectionParameters.add("Primary key metadata table", primaryKeyMetadataTable);
    }
    
    /**
     * <p>setPrimaryKeyMetadataTable</p>
     *
     * @param primaryKeyMetadataTable a {@link java.lang.String} object.
     */
    public void setPrimaryKeyMetadataTable(String primaryKeyMetadataTable) {
    	connectionParameters.set("Primary key metadata table", primaryKeyMetadataTable);
    }
    
    /**
     * <p>addLooseBBox</p>
     *
     * @param looseBBox a boolean.
     */
    protected void addLooseBBox(boolean looseBBox) {
    	connectionParameters.add("Loose bbox", Boolean.toString(looseBBox));
    }
    
    /**
     * <p>setLooseBBox</p>
     *
     * @param looseBBox a boolean.
     */
    public void setLooseBBox(boolean looseBBox) {
    	connectionParameters.set("Loose bbox", Boolean.toString(looseBBox));
    }
    
    /**
     * <p>addPreparedStatements</p>
     *
     * @param preparedStatements a boolean.
     */
    protected void addPreparedStatements(boolean preparedStatements) {
    	connectionParameters.add("preparedStatements", Boolean.toString(preparedStatements));
    }
    
    /**
     * <p>setPreparedStatements</p>
     *
     * @param preparedStatements a boolean.
     */
    public void setPreparedStatements(boolean preparedStatements) {
    	connectionParameters.set("preparedStatements", Boolean.toString(preparedStatements));
    }
    
    /**
     * <p>addMaxOpenPreparedStatements</p>
     *
     * @param maxOpenPreparedStatements a int.
     */
    protected void addMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
    	connectionParameters.add("Max open prepared statements", Integer.toString(maxOpenPreparedStatements));
    }
    
    /**
     * <p>setMaxOpenPreparedStatements</p>
     *
     * @param maxOpenPreparedStatements a int.
     */
    public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
    	connectionParameters.set("Max open prepared statements", Integer.toString(maxOpenPreparedStatements));
    }

}
