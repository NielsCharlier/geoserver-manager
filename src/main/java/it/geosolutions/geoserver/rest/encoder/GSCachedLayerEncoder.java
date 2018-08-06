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

import it.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;
import it.geosolutions.geoserver.rest.encoder.utils.ElementUtils;
import it.geosolutions.geoserver.rest.encoder.utils.PropertyXMLEncoder;

import org.jdom.Element;

/**
 */
public class GSCachedLayerEncoder extends PropertyXMLEncoder {

    public final static String MIME_FORMATS = "mimeFormats";

    public final static String GRIDSUBSETS = "gridSubsets";

    public final static String PARAMETER_FILTERS = "parameterFilters";

    final private Element mimeFormatsListEncoder = new Element(MIME_FORMATS);

    final private Element gridSubsetsListEncoder = new Element(GRIDSUBSETS);

    final private Element parameterFiltersListEncoder = new Element(PARAMETER_FILTERS);
    
    private GSCachedLayerEncoder(Element root) {
        super(root);
    }
    
    public static GSCachedLayerEncoder build(String response) {
        Element pb = JDOMBuilder.buildElement(response);
        if(pb != null) {
            return new GSCachedLayerEncoder(pb);
        } else {
            return null;
        }
    }

    public GSCachedLayerEncoder() {
        this(true);
    }

    public GSCachedLayerEncoder(boolean encodeLists) {
        super("GeoServerLayer");
        setEnabled(true);
        if (encodeLists) {
            addContent(mimeFormatsListEncoder);
            addContent(gridSubsetsListEncoder);
            addContent(parameterFiltersListEncoder);
        }
    }

    void encodeMimeFormats(boolean encoded) {
        if (encoded) {
            if (this.get(mimeFormatsListEncoder.getName()) == null) {
                addContent(mimeFormatsListEncoder);
            }
        } else {
            remove(mimeFormatsListEncoder.getName());
        }
    }

    void encodeGridSubsets(boolean encoded) {
        if (encoded) {
            if (this.get(gridSubsetsListEncoder.getName()) == null) {
                addContent(gridSubsetsListEncoder);
            }
        } else {
            remove(gridSubsetsListEncoder.getName());
        }
    }

    void encodeParameterFilters(boolean encoded) {
        if (encoded) {
            if (this.get(parameterFiltersListEncoder.getName()) == null) {
                addContent(parameterFiltersListEncoder);
            }
        } else {
            remove(parameterFiltersListEncoder.getName());
        }
    }
    

    /**
     * @param name
     */
    public void setId(String id) {
        set("id", id);
    }
    
    public String getId() {
        final Element node = get("id");
        return node != null ? node.getText() : null;
    }

    /**
     * @param enable true if layer should be set to enabled
     */
    public void setEnabled(boolean enable) {
        if (enable)
            set("enabled", "true");
        else
            set("enabled", "false");
    }
    
    public boolean isEnabled() {
        final Element node = get("enabled");
        return node != null ? Boolean.parseBoolean(node.getText()) : null;
    }
    
    /**
     * @param inMemoryCached
     */
    public void setInMemoryCached(boolean enable) {
        if (enable)
            set("inMemoryCached", "true");
        else
            set("inMemoryCached", "false");
    }
    
    public boolean isInMemoryCached() {
        final Element node = get("inMemoryCached");
        return node != null ? Boolean.parseBoolean(node.getText()) : null;
    }
    
    /**
     * @param BlobStoreId
     */
    public void setBlobStoreId(String blobStoreId) {
        set("blobStoreId", blobStoreId);
    }
    
    /**
     * @param BlobStoreId
     */
    public String getBlobStoreId() {
        final Element node = get("blobStoreId");
        return node != null ? node.getText() : null;
    }

    /**
     * @param expireCache
     */
    public void setExpireCache(int expireCache) {
        set("expireCache", Integer.toString(expireCache));
    }
    
    public int getExpireCache() {
        final Element node = get("expireCache");
        return node != null ? Integer.parseInt(node.getText()) : null;
    }
    
    /**
     * @param expireClients
     */
    public void setExpireClients(int expireClients) {
        set("expireClients", Integer.toString(expireClients));
    }
    
    public int getExpireClients() {
        final Element node = get("expireClients");
        return node != null ? Integer.parseInt(node.getText()) : null;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        set("name", name);
    }
    
    /**
     * @param name
     */
    public String getName() {
        final Element node = get("name");
        return node != null ? node.getText() : null;
    }
    
    /**
     * @param gutter
     */
    public void setGutter(int gutter) {
        set("gutter", Integer.toString(gutter));
    }
    
    public int getGutter() {
        final Element node = get("gutter");
        return node != null ? Integer.parseInt(node.getText()) : null;
    }
    
    /**
     * @param width
     * @param height
     */
    public void setMetaWidthHeight(int... values) {
        final Element el = new Element("metaWidthHeight");
        for (int value : values) {
            final Element elChild = new Element("int");
            elChild.setText(Integer.toString(value));
            el.addContent(elChild);
        }    
        Element pp;
        if ((pp = ElementUtils.contains(getRoot(), "metaWidthHeight")) != null) {
            ElementUtils.remove(pp, pp);
        }
        addContent(el);
    }
    
    /**
     * Add a mime format
     * 
     * @param mimeFormat
     */
    public void addMimeFormat(String mimeFormat) {
        final Element el = new Element("string");
        el.setText(mimeFormat);
        mimeFormatsListEncoder.addContent(el);
    }
    

    /**
     * Add a mime format
     * 
     * @param mimeFormat
     */
    public void addParameterFilter(String type, String key, String defaultValue) {
        final Element el = new Element(type);
        final Element elKey = new Element("key");
        elKey.setText(key);
        el.addContent(elKey);
        final Element elDefaultValue = new Element("defaultValue");
        elDefaultValue.setText(defaultValue);
        el.addContent(elDefaultValue);
        parameterFiltersListEncoder.addContent(el);
    }

    /**
     * Add a grid subset
     * 
     * @param gridSetName
     */
    public void addGridSubset(String gridSetName, Integer zoomStart, Integer zoomStop,
            Integer minCachedLevel, Integer maxCachedLevel) {
        final Element el = new Element("gridSubset");
        final Element elName = new Element("gridSetName");
        elName.setText(gridSetName);
        el.addContent(elName);
        gridSubsetsListEncoder.addContent(el);
    }

}
