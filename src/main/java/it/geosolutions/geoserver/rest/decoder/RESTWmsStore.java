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

package it.geosolutions.geoserver.rest.decoder;

import it.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;

import org.jdom.Element;

/**
 * Parse WmsStores returned as XML REST objects.
 * <P>
 * This is the XML document returned by GeoServer when requesting a WmsStore:
 * <PRE>
 * {@code
 *<wmsStore>
 *	<name>regione</name>
 *	<type>WMS</type>
 *	<enabled>true</enabled>
 *	<workspace>
 *		<name>arit</name>
 *		<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://172.27.30.25:8080/geoserver/rest/workspaces/arit.xml" type="application/xml"/>
 *	</workspace>
 *	<metadata>
 *		<entry key="useConnectionPooling">true</entry>
 *	</metadata>
 *	<__default>false</__default>
 *	<capabilitiesURL>http://www.regione.lazio.it/geoserver/wms</capabilitiesURL>
 *	<user>admin</user>
 *	<password>geoserver</password>
 *	<maxConnections>6</maxConnections>
 *	<readTimeout>60</readTimeout>
 *	<connectTimeout>30</connectTimeout>
 *	<wmsLayers>
 *		<atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://172.27.30.25:8080/geoserver/rest/workspaces/arit/wmsstores/regione/wmslayers.xml" type="application/xml"/>
 *	</wmsLayers>
 *</wmsStore>
 * }
 * </PRE>
 *
 * Note: the whole XML fragment is stored in memory. At the moment, there are
 * methods to retrieve only the more useful data.
 *
 * @author etj
 * @version $Id: $
 */
public class RESTWmsStore {
	private final Element cs;


	/**
	 * <p>Constructor for RESTWmsStore.</p>
	 *
	 * @param cs a {@link org.jdom.Element} object.
	 */
	public RESTWmsStore(Element cs) {
		this.cs = cs;
	}

    /**
     * <p>build</p>
     *
     * @param response a {@link java.lang.String} object.
     * @return a {@link it.geosolutions.geoserver.rest.decoder.RESTWmsStore} object.
     */
    public static RESTWmsStore build(String response) {
        if(response == null)
            return null;
        if(response.isEmpty())
        	return new RESTWmsStore(new Element("wmsStore")); // TODO check how to response
        
        Element pb = JDOMBuilder.buildElement(response);
        if(pb != null)
            return new RESTWmsStore(pb);
        else
            return null;
    }

    /**
     * <p>getName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return cs.getChildText("name");
    }

    /**
     * <p>getType</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getType() {
        return cs.getChildText("type");
    }

    /**
     * <p>getEnabled</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getEnabled() {
        return Boolean.parseBoolean(cs.getChildText("enabled"));
    }
    
    /**
     * <p>getWorkspaceName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getWorkspaceName() {
        return cs.getChild("workspace").getChildText("name");
    }    
    
    /**
     * <p>getUseConnectionPooling</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getUseConnectionPooling(){
    	Element entry = cs.getChild("metadata").getChild("entry");    	
    	return entry.getAttributeValue("key").equals("useConnectionPooling") && Boolean.parseBoolean(entry.getValue());
    }
    
    /**
     * <p>getCapabilitiesURL</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCapabilitiesURL() {
        return cs.getChildText("capabilitiesURL");
    }
    
    /**
     * <p>getMaxConnections</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMaxConnections() {
    	return cs.getChildText("maxConnections");
    }
    
	/**
	 * <p>getReadTimeout</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getReadTimeout() {
		return cs.getChildText("readTimeout");
    }
    
    /**
     * <p>getConnectTimeout</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getConnectTimeout() {
		return cs.getChildText("connectTimeout");
    }        

    /**
     * <p>getUser</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getUser() {
        return cs.getChildText("user");
    }
    
    /**
     * <p>getPassword</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPassword() {
        return cs.getChildText("password");
    }
    
    /**
     * <p>toString</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName())
                .append('[');
        if(cs == null)
            sb.append("null");
        else
            sb.append("name:").append(getName())
                .append(" wsname:").append(getWorkspaceName());

        return sb.toString();
    }
}
