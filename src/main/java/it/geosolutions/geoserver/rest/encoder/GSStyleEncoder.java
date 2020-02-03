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

import it.geosolutions.geoserver.rest.encoder.utils.ElementUtils;
import it.geosolutions.geoserver.rest.encoder.utils.NestedElementEncoder;
import it.geosolutions.geoserver.rest.encoder.utils.PropertyXMLEncoder;

import org.jdom.Element;

/**
 * <p>GSStyleEncoder class.</p>
 *
 * @version $Id: $
 */
public class GSStyleEncoder extends PropertyXMLEncoder {
    /** Style <code>STYLE="style"</code> */
    public final static String STYLE = "style";
    /** Constant <code>NAME="name"</code> */
    public final static String NAME="name";

    /**
     * <p>Constructor for GSWorkspaceEncoder.</p>
     */
    public GSStyleEncoder() {
        super(STYLE);
    }

    /**
     * <p>Constructor for GSWorkspaceEncoder.</p>
     *
     * @param name the workspace name
     */
    public GSStyleEncoder(String name) {
    	super(STYLE);
        addName(name);
    }
    
    /**
     * Add the name to this workspace
     *
     * @param name a {@link java.lang.String} object.
     * @throws java.lang.IllegalStateException if name is already set
     */
    protected void addName(final String name) {
        final Element el = ElementUtils.contains(getRoot(), NAME);
        if (el == null) {
            add(NAME, name);
        } else {
            throw new IllegalStateException("Workspace name is already set: " + el.getText());
        }
    }
    
    /**
     * add or change (if already set) the workspace name
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(final String name) {
        final Element el = ElementUtils.contains(getRoot(), NAME);
        if (el == null) {
            add(NAME, name);
        } else {
            el.setText(name);
        }
    }
    
    /**
     * <p>getName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName(){
        final Element el = ElementUtils.contains(getRoot(), NAME);
        if (el != null) {
            return el.getTextTrim();
        } else {
            return null;
        }
    }
    
    public void setLegendGraphic(String onlineResource, String format, int width, int height) {
        Element legend = new Element("legend");
        Element elOnlineResource = new Element("onlineResource");
        elOnlineResource.setText(onlineResource);
        Element elFormat = new Element("format");
        elFormat.setText(format);
        Element elWidth = new Element("width");
        elWidth.setText("" + width);
        Element elHeight = new Element("height");
        elHeight.setText("" + height);
        legend.addContent(elOnlineResource);
        legend.addContent(elFormat);
        legend.addContent(elWidth);
        legend.addContent(elHeight);
        addContent(legend);
    }
    
    public void setFormat(String format) {
        Element elFormat = new Element("format");
        elFormat.setText(format);
        addContent(elFormat);
    }
    
    public void setLanguageVersion(String version) {
        Element elLanguageVersion = new Element("languageVersion");
        Element elVersion = new Element("version");
        elVersion.setText(version);
        elLanguageVersion.addContent(elVersion);
        addContent(elLanguageVersion);
    }

}
