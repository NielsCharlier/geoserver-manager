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
import it.geosolutions.geoserver.rest.decoder.utils.NameLinkElem;

import org.jdom.Element;

/**
 * Parses list of summary data about Layers.
 *
 *
 * Layer summary info.
 * <BR>This is an XML fragment:
 * <PRE>
 *{@code
 *<layer>
 *    <name>states</name>
 *    <atom:link
 *        xmlns:atom="http://www.w3.org/2005/Atom"
 *        rel="alternate"
 *        href="http://localhost:8080/geoserver/rest/layers/states.xml"
 *        type="application/xml"/>
 *</layer>
 * }
 * </PRE>
 *
 * @author ETj (etj at geo-solutions.it)
 * @version $Id: $
 */
public class RESTLayerList extends RESTAbstractList<NameLinkElem> {

    /**
     * <p>build</p>
     *
     * @param response a {@link java.lang.String} object.
     * @return a {@link it.geosolutions.geoserver.rest.decoder.RESTLayerList} object.
     */
    public static RESTLayerList build(String response) {
        Element elem = JDOMBuilder.buildElement(response);
        return elem == null? null : new RESTLayerList(elem);
	}

    /**
     * <p>Constructor for RESTLayerList.</p>
     *
     * @param list a {@link org.jdom.Element} object.
     */
    protected RESTLayerList(Element list) {
        super(list);
    }
}
