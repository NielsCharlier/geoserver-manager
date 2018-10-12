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

package it.geosolutions.geoserver.rest.decoder.utils;

import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

/**
 * <p>Abstract JDOMListIterator class.</p>
 *
 * @author ETj (etj at geo-solutions.it)
 * @version $Id: $
 */
public abstract class JDOMListIterator<ELEM> implements Iterator<ELEM> {

    private final Iterator<Element> iter;

    /**
     * <p>Constructor for JDOMListIterator.</p>
     *
     * @param orig a {@link java.util.List} object.
     */
    public JDOMListIterator(List<Element> orig) {
        iter = orig.iterator();
    }

    /**
     * <p>hasNext</p>
     *
     * @return a boolean.
     */
    public boolean hasNext() {
        return iter.hasNext();
    }

    /**
     * <p>next</p>
     *
     * @return a ELEM object.
     */
    public ELEM next() {
        return transform(iter.next());
    }

    /**
     * <p>transform</p>
     *
     * @param listItem a {@link org.jdom.Element} object.
     * @return a ELEM object.
     */
    public abstract ELEM transform(Element listItem);

    /**
     * <p>remove</p>
     */
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
