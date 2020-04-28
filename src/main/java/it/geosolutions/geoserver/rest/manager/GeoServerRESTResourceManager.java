/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *
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

package it.geosolutions.geoserver.rest.manager;

import it.geosolutions.geoserver.rest.HTTPUtils;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.httpclient.methods.InputStreamRequestEntity;

public class GeoServerRESTResourceManager extends GeoServerRESTAbstractManager {
    
    public GeoServerRESTResourceManager(URL restURL, String username, String password)
            throws IllegalArgumentException {
        super(restURL, username, password);
    }

    private String RESOURCE_PATH = "/rest/resource/";
    
    private String urlFromPath(String path) {
        return HTTPUtils.append(gsBaseUrl, RESOURCE_PATH, path).toString();
    }    
    
    public boolean upload(String path, InputStream is) {
        return HTTPUtils.put(urlFromPath(path), new InputStreamRequestEntity(is), 
                gsuser, gspass) != null;
    }    
    
    public InputStream download(String path) {
        return HTTPUtils.getAsStream(urlFromPath(path), gsuser, gspass);
    }    
    
    public boolean delete(String path) {
        return HTTPUtils.delete(urlFromPath(path), gsuser, gspass);
    }

}