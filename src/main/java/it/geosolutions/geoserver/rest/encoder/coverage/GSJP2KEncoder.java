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
package it.geosolutions.geoserver.rest.encoder.coverage;

import it.geosolutions.geoserver.rest.encoder.utils.ElementUtils;
import it.geosolutions.geoserver.rest.encoder.utils.NestedElementEncoder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Should be used to encode a Jpeg2K coverage.
 *
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * @version $Id: $
 */
public class GSJP2KEncoder extends GSCoverageEncoder {

    final private static String STRING = "string";

    final private static String ENTRY = "entry";

    final private NestedElementEncoder parameters = new NestedElementEncoder("parameters");

    /**
     * <p>Constructor for GSJP2KEncoder.</p>
     */
    public GSJP2KEncoder() {
        // Link members to the parent
        addContent(parameters.getRoot());
    }

    static class parametersFilter implements Filter {
        final String name;

        public parametersFilter(final String name) {
            this.name = name;
        }

        private static final long serialVersionUID = 1L;

        public boolean matches(Object obj) {
            if (obj instanceof Element) {
                if (((Element) obj).getName().equals(ENTRY)) {
                    List<Element> values=((Element) obj).getChildren(STRING);
                    for (Element el : values){
                        if (el == null)
                            return false;
                        if (el.getText().equals(this.name)) {
                            return true;
                        }
                    }
//                    final Element el = ((Element) obj).getChild(STRING);
//                    if (el == null)
//                        return false;
//                    if (el.getText().equals(this.name)) {
//                        return true;
//                    }
                }
            }
            return false;
        }
    };

    private final boolean removeParameter(final Filter filter) {
        final Iterator<Element> it = ElementUtils.search(getRoot(), filter).iterator();
        if (it.hasNext()) {
            final Element el = it.next();
            return ElementUtils.remove(el, el);
        }
        return false;
    }
    
    private final Element getParameter(final Filter filter) {
        final Iterator<Element> it = ElementUtils.search(getRoot(), filter).iterator();
        if (it.hasNext()) {
            final Element el = it.next();
            return ElementUtils.contains(el, el);
        }
        return null;
    }

    private final static String useMultithreading = "USE_MULTITHREADING";

    /**
     * <p>addUseMultithreading</p>
     *
     * @param val a boolean.
     */
    protected void addUseMultithreading(final boolean val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(useMultithreading));
        list.add(new Element(STRING).setText((val) ? "true" : "false"));

        parameters.add(null, list);
    }

    private final static Filter useMultithreadingFilter = new parametersFilter(useMultithreading);

    /**
     * <p>Setter for the field <code>useMultithreading</code>.</p>
     *
     * @param val a boolean.
     */
    public void setUseMultithreading(final boolean val) {
        removeParameter(useMultithreadingFilter);
        addUseMultithreading(val);
    }
    
    /**
     * <p>Getter for the field <code>useMultithreading</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getUseMultithreading() {
        Element el=getParameter(useMultithreadingFilter);
        if (el!=null){
            List<Element> values=el.getChildren();
            for (Element elm : values){
                String value=elm.getValue();
                if (!value.equalsIgnoreCase(useMultithreading)){
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

    private final static String SUGGESTED_TILE_SIZE = "SUGGESTED_TILE_SIZE";

    /**
     * <p>addSuggestedTileSize</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addSuggestedTileSize(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(SUGGESTED_TILE_SIZE));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter SUGGESTED_TILE_SIZEFilter = new parametersFilter(
            SUGGESTED_TILE_SIZE);

    /**
     * <p>setSuggestedTileSize</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setSuggestedTileSize(final String val) {
        removeParameter(SUGGESTED_TILE_SIZEFilter);
        addSuggestedTileSize(val);
    }
    
    /**
     * <p>getSuggestedTileSize</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSuggestedTileSize() {
        Element el=getParameter(SUGGESTED_TILE_SIZEFilter);
        if (el!=null){
            List<Element> values=el.getChildren();
            for (Element elm : values){
                String value=elm.getValue();
                if (!value.equalsIgnoreCase(SUGGESTED_TILE_SIZE)){
                    return value;
                }
            }
             
        }
        return null;
    }

    private final static String USE_JAI_IMAGEREAD = "USE_JAI_IMAGEREAD";

    /**
     * <p>addUseJaiImageRead</p>
     *
     * @param val a boolean.
     */
    protected void addUseJaiImageRead(final boolean val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(USE_JAI_IMAGEREAD));
        list.add(new Element(STRING).setText((val) ? "true" : "false"));
        parameters.add(null, list);
    }

    private final static Filter USE_JAI_IMAGEREADFilter = new parametersFilter(USE_JAI_IMAGEREAD);

    /**
     * <p>setUseJaiImageRead</p>
     *
     * @param val a boolean.
     */
    public void setUseJaiImageRead(final boolean val) {
        removeParameter(USE_JAI_IMAGEREADFilter);
        addUseJaiImageRead(val);
    }
    
    /**
     * <p>getUseJaiImageRead</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getUseJaiImageRead() {
        Element el=getParameter(USE_JAI_IMAGEREADFilter);
        if (el!=null){
            List<Element> values=el.getChildren();
            for (Element elm : values){
                String value=elm.getValue();
                if (!value.equalsIgnoreCase(USE_JAI_IMAGEREAD)){
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

}
