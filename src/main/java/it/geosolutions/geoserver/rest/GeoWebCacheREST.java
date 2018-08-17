/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007 - 2016 GeoSolutions S.A.S.
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
package it.geosolutions.geoserver.rest;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.geosolutions.geoserver.rest.encoder.GSCachedLayerEncoder;

public class GeoWebCacheREST {

    /** The logger for this class */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoWebCacheREST.class);
    
    /**
     * GeoServer instance base URL. E.g.: <TT>http://localhost:8080/geoserver</TT>.
     */
    private final String restURL;

    /**
     * GeoServer instance privileged username, with read & write permission on REST API
     */
    private final String gsuser;

    /**
     * GeoServer instance password for privileged username with r&w permission on REST API
     */
    private final String gspass;
    
    /**
     * Creates a <TT>GeoServerRESTPublisher</TT> to connect against a GeoServer instance with the given URL and user credentials.
     * 
     * @param restURL the base GeoServer URL (e.g.: <TT>http://localhost:8080/geoserver</TT>)
     * @param username auth credential
     * @param password auth credential
     */
    public GeoWebCacheREST(URL restURL, String username, String password) {
        this.restURL = HTTPUtils.decurtSlash(restURL.toString());
        this.gsuser = username;
        this.gspass = password;
    }
    
    public boolean configureLayer(final GSCachedLayerEncoder layer) 
            throws IllegalArgumentException {
        if (layer == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (layer.isEmpty()) {
            throw new IllegalArgumentException("Empty argument");
        }
        
        final String layerName = layer.getName();
        if (layerName == null || layerName.isEmpty()) {
            throw new IllegalArgumentException("Missing layer name");
        }        

        final String url = restURL + "/gwc/rest/layers/" + layerName + ".xml";

        String layerXml = layer.toString();
        String sendResult = HTTPUtils.putXml(url, layerXml, gsuser, gspass);
        if (sendResult != null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Cached layer successfully configured: " + layerName);
            }
        } else {
            if (LOGGER.isWarnEnabled())
                LOGGER.warn("Error configuring cached layer " + layerName + " (" + sendResult + ")");
        }

        return sendResult != null;
    }
    
    public boolean updateLayer(final GSCachedLayerEncoder layer) 
            throws IllegalArgumentException {
        if (layer == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (layer.isEmpty()) {
            throw new IllegalArgumentException("Empty argument");
        }
        
        final String layerName = layer.getName();
        if (layerName == null || layerName.isEmpty()) {
            throw new IllegalArgumentException("Missing layer name");
        }        

        final String url = restURL + "/gwc/rest/layers/" + layerName + ".xml";

        String layerXml = layer.toString();
        String sendResult = HTTPUtils.postXml(url, layerXml, gsuser, gspass);
        if (sendResult != null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Cached layer successfully configured: " + layerName);
            }
        } else {
            if (LOGGER.isWarnEnabled())
                LOGGER.warn("Error configuring cached layer " + layerName + " (" + sendResult + ")");
        }

        return sendResult != null;
    }
    
    public GSCachedLayerEncoder getLayer(final String layerName) {
        if (layerName == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (layerName.isEmpty()) {
            throw new IllegalArgumentException("Empty argument");
        }     

        final String url = restURL + "/gwc/rest/layers/" + layerName + ".xml";

        return GSCachedLayerEncoder.build(HTTPUtils.get(url, gsuser, gspass));
    }
    
    public boolean deleteLayer(final String layerName) {
        if (layerName == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (layerName.isEmpty()) {
            throw new IllegalArgumentException("Empty argument");
        }     

        final String url = restURL + "/gwc/rest/layers/" + layerName + ".xml";

        boolean sendResult = HTTPUtils.delete(url, gsuser, gspass);
        
        if (sendResult) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Successfully deleted cached layer: " + layerName);
            }
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Error deleting cached layer " + layerName);
            }
        }
        return sendResult;
    }
    
    public boolean truncateLayer(final String layerName) {
        final String url = restURL + "/gwc/rest/masstruncate";
        final String xml = "<truncateLayer><layerName>" + layerName + "</layerName></truncateLayer>";
        
        String sendResult = HTTPUtils.postXml(url, xml, gsuser, gspass);
        if (sendResult != null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Successfully mass truncated layer: " + layerName);
            }
            return true;
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Error mass truncating layer " + layerName + " (" + sendResult + ")");
            }
            return false;
        }
    }

}
