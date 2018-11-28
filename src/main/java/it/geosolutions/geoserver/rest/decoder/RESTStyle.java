/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *
 *  Copyright (C) 2007,2013 GeoSolutions S.A.S.
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
 * <p>RESTStyle class.</p>
 *
 * @author ETj (etj at geo-solutions.it)
 * @version $Id: $
 */
public class RESTStyle {

    private final Element elem;

    /**
     * <p>build</p>
     *
     * @param xml a {@link java.lang.String} object.
     * @return a {@link it.geosolutions.geoserver.rest.decoder.RESTStyle} object.
     */
    public static RESTStyle build(String xml) {
        if (xml == null) {
            return null;
        }

        Element e = JDOMBuilder.buildElement(xml);
        if (e != null) {
            return new RESTStyle(e);
        } else {
            return null;
        }
    }

    /**
     * <p>Constructor for RESTStyle.</p>
     *
     * @param elem a {@link org.jdom.Element} object.
     */
    protected RESTStyle(Element elem) {
        this.elem = elem;
    }

    /**
     * <p>getName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return elem.getChildText("name");
    }

    /**
     * <p>getFileName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFileName() {
        return elem.getChildText("filename");
    }
    
    /**
     * <p>getFormat</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getFormat() {     
        return elem.getChildText("format");
    }
    
    /**
     * <p>getVersion</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getVersion() {       
        if(elem.getChild("languageVersion") != null) {
            return elem.getChild("languageVersion").getChildText("version");
        } else {
            return null;
        }
    }

    /**
     * <p>getWorkspace</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getWorkspace() {
        if(elem.getChild("workspace") != null)
            return elem.getChild("workspace").getChildText("name");
        else
            return null;
    }

}
