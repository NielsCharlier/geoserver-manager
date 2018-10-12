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
 * Use this encoder for ImageMosaic coverages.
 *
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * @author Simone Giannecchini, GeoSolutions SAS
 * @version $Id: $
 */
@SuppressWarnings("unchecked")
public class GSImageMosaicEncoder extends GSCoverageEncoder {

    final private static String STRING = "string";

    final private static String ENTRY = "entry";

    final private NestedElementEncoder parameters = new NestedElementEncoder("parameters");

    /**
     * <p>Constructor for GSImageMosaicEncoder.</p>
     */
    public GSImageMosaicEncoder() {
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
                    final Element el = ((Element) obj).getChild(STRING);
                    if (el == null)
                        return false;
                    if (el.getText().equals(this.name)) {
                        return true;
                    }
                }
            }
            return false;
        }
    };

    private final boolean removeParameter(final Filter filter) {
        final Iterator<Element> it = ElementUtils.search(getRoot(), filter).iterator();
        if (it.hasNext()) {
            final Element el = it.next();
            // if (it.hasNext())
            // return false;
            // else
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

    /** Constant <code>allowMultithreading="AllowMultithreading"</code> */
    public final static String allowMultithreading = "AllowMultithreading";

    /**
     * <p>addAllowMultithreading</p>
     *
     * @param val a boolean.
     */
    protected void addAllowMultithreading(final boolean val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(allowMultithreading));
        list.add(new Element(STRING).setText((val) ? "true" : "false"));

        parameters.add(null, list);
    }

    private final static Filter allowMultithreadingFilter = new parametersFilter(
            allowMultithreading);

    /**
     * <p>Setter for the field <code>allowMultithreading</code>.</p>
     *
     * @param val a boolean.
     */
    public void setAllowMultithreading(final boolean val) {
        removeParameter(allowMultithreadingFilter);
        addAllowMultithreading(val);
    }

    /**
     * <p>Getter for the field <code>allowMultithreading</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getAllowMultithreading() {
        Element el = getParameter(allowMultithreadingFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(allowMultithreading)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

    //
    // Filter
    //
    /** Constant <code>filter="Filter"</code> */
    public final static String filter = "Filter";

    /**
     * <p>addFilter</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addFilter(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(filter));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter filterFilter = new parametersFilter(filter);

    /**
     * <p>Setter for the field <code>filter</code>.</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setFilter(final String val) {
        removeParameter(filterFilter);
        addFilter(val);
    }
    /**
     * <p>Getter for the field <code>filter</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getFilter() {
        Element el = getParameter(filterFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(filter)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }
    
    //
    // MaxAllowedTiles
    //
    /** Constant <code>maxAllowedTiles="MaxAllowedTiles"</code> */
    public final static String maxAllowedTiles = "MaxAllowedTiles";
    
    /**
     * <p>addMaxAllowedTiles</p>
     *
     * @param val a int.
     */
    protected void addMaxAllowedTiles(final int val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(maxAllowedTiles));
        list.add(new Element(STRING).setText(String.valueOf(val)));
        parameters.add(null, list);
    }

    private final static Filter maxAllowedTilesFilter = new parametersFilter(maxAllowedTiles);

    /**
     * <p>Setter for the field <code>maxAllowedTiles</code>.</p>
     *
     * @param val a int.
     */
    public void setMaxAllowedTiles(final int val) {
        removeParameter(maxAllowedTilesFilter);
        addMaxAllowedTiles(val);
    }
    
    /**
     * <p>Getter for the field <code>maxAllowedTiles</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getMaxAllowedTiles() {
        Element el = getParameter(maxAllowedTilesFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(maxAllowedTiles)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

    
    //
    // InputTransparentColor
    //
    /** Constant <code>inputTransparentColor="InputTransparentColor"</code> */
    public final static String inputTransparentColor = "InputTransparentColor";

    /**
     * <p>addInputTransparentColor</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addInputTransparentColor(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(inputTransparentColor));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter inputTransparentColorFilter = new parametersFilter(
            inputTransparentColor);

    /**
     * <p>Setter for the field <code>inputTransparentColor</code>.</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setInputTransparentColor(final String val) {
        removeParameter(inputTransparentColorFilter);
        addInputTransparentColor(val);
    }
    
    /**
     * <p>Getter for the field <code>inputTransparentColor</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getInputTransparentColor() {
        Element el = getParameter(inputTransparentColorFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(inputTransparentColor)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

    
    //
    // OutputTransparentColor
    //
    /** Constant <code>outputTransparentColor="OutputTransparentColor"</code> */
    public final static String outputTransparentColor = "OutputTransparentColor";

    /**
     * <p>addOutputTransparentColor</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addOutputTransparentColor(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(outputTransparentColor));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter outputTransparentColorFilter = new parametersFilter(
            outputTransparentColor);

    /**
     * <p>Setter for the field <code>outputTransparentColor</code>.</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setOutputTransparentColor(final String val) {
        removeParameter(outputTransparentColorFilter);
        addInputTransparentColor(val);
    }
    /**
     * <p>Getter for the field <code>outputTransparentColor</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getOutputTransparentColor() {
        Element el = getParameter(outputTransparentColorFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(outputTransparentColor)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }
    
    //
    // SUGGESTED_TILE_SIZE
    //
    /** Constant <code>SUGGESTED_TILE_SIZE="SUGGESTED_TILE_SIZE"</code> */
    public final static String SUGGESTED_TILE_SIZE = "SUGGESTED_TILE_SIZE";

    /**
     * <p>addSUGGESTED_TILE_SIZE</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addSUGGESTED_TILE_SIZE(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(SUGGESTED_TILE_SIZE));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter SUGGESTED_TILE_SIZEFilter = new parametersFilter(
            SUGGESTED_TILE_SIZE);

    /**
     * <p>setSUGGESTED_TILE_SIZE</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setSUGGESTED_TILE_SIZE(final String val) {
        removeParameter(SUGGESTED_TILE_SIZEFilter);
        addSUGGESTED_TILE_SIZE(val);
    }

    /**
     * <p>getSuggestedTileSize</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSuggestedTileSize() {
        Element el = getParameter(SUGGESTED_TILE_SIZEFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(SUGGESTED_TILE_SIZE)) {
                    return value;
                }
            }

        }
        return null;
    }

    //
    // USE_JAI_IMAGEREAD
    //
    /** Constant <code>USE_JAI_IMAGEREAD="USE_JAI_IMAGEREAD"</code> */
    public final static String USE_JAI_IMAGEREAD = "USE_JAI_IMAGEREAD";

    /**
     * <p>addUSE_JAI_IMAGEREAD</p>
     *
     * @param val a boolean.
     */
    protected void addUSE_JAI_IMAGEREAD(final boolean val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(USE_JAI_IMAGEREAD));
        list.add(new Element(STRING).setText((val) ? "true" : "false"));
        parameters.add(null, list);
    }

    private final static Filter USE_JAI_IMAGEREADFilter = new parametersFilter(USE_JAI_IMAGEREAD);

    /**
     * <p>setUSE_JAI_IMAGEREAD</p>
     *
     * @param val a boolean.
     */
    public void setUSE_JAI_IMAGEREAD(final boolean val) {
        removeParameter(USE_JAI_IMAGEREADFilter);
        addUSE_JAI_IMAGEREAD(val);
    }

    /**
     * <p>getUseJaiImageRead</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getUseJaiImageRead() {
        Element el = getParameter(USE_JAI_IMAGEREADFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(USE_JAI_IMAGEREAD)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

    //
    // BackgroundValues
    //
    /** Constant <code>backgroundValues="BackgroundValues"</code> */
    public final static String backgroundValues = "BackgroundValues";

    /**
     * <p>addBackgroundValues</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addBackgroundValues(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(backgroundValues));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter backgroundValuesFilter = new parametersFilter(backgroundValues);

    /**
     * <p>Setter for the field <code>backgroundValues</code>.</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setBackgroundValues(final String val) {
        removeParameter(backgroundValuesFilter);
        addBackgroundValues(val);
    }
    /**
     * <p>Getter for the field <code>backgroundValues</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    public Boolean getBackgroundValues() {
        Element el = getParameter(backgroundValuesFilter);
        if (el != null) {
            List<Element> values = el.getChildren();
            for (Element elm : values) {
                String value = elm.getValue();
                if (!value.equalsIgnoreCase(backgroundValues)) {
                    return Boolean.valueOf(value);
                }
            }
        }
        return null;
    }

    //
    // SORTING
    //
    /** Constant <code>SORTING="SORTING"</code> */
    public final static String SORTING = "SORTING";

    /**
     * <p>addSORTING</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addSORTING(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(SORTING));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter SORTING_FILTER = new parametersFilter(SORTING);

    /**
     * <p>setSORTING</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setSORTING(final String val) {
        removeParameter(SORTING_FILTER);
        addBackgroundValues(val);
    }
    
    //
    // SORTING
    //
    
    /** Constant <code>MERGEBEHAVIOR="MergeBehavior"</code> */
    public final static String MERGEBEHAVIOR = "MergeBehavior";

    /**
     * <p>addMergeBehavior</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    protected void addMergeBehavior(final String val) {
        final List<Element> list = new ArrayList<Element>(2);
        list.add(new Element(STRING).setText(MERGEBEHAVIOR));
        list.add(new Element(STRING).setText(val));
        parameters.add(null, list);
    }

    private final static Filter MERGEBEHAVIOR_FILTER = new parametersFilter(MERGEBEHAVIOR);

    /**
     * <p>setMergeBehavior</p>
     *
     * @param val a {@link java.lang.String} object.
     */
    public void setMergeBehavior(final String val) {
        removeParameter(MERGEBEHAVIOR_FILTER);
        addBackgroundValues(val);
    }
}
