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

import it.geosolutions.geoserver.rest.encoder.authorityurl.GSAuthorityURLInfoEncoder;
import it.geosolutions.geoserver.rest.encoder.identifier.GSIdentifierInfoEncoder;
import it.geosolutions.geoserver.rest.encoder.utils.PropertyXMLEncoder;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 * Layer encoder for Geoserver &gt;= 2.2
 *
 * @author ETj (etj at geo-solutions.it)
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * @author Emmanuel Blondel - emmanuel.blondel1@gmail.com
 *
 * The layer encoder is enabled by default
 *
 * {@code
 * <layer>
 *      <name>{LAYERNAME}</name>
 *      <type>RASTER</type>
 *      <defaultStyle>
 *              <name>{STYLE_NAME}</name>
 *              <atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate" href="http://{GSURL}/rest/styles/{STYLE}xml" type="application/xml"/>
 *      </defaultStyle>
 *      <resource class="coverage">
 *            <name>{RESOURCE_NAME}</name>
 *              <atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate"
 *                      href="http://{GSURL}/rest/workspaces/{WS}/coveragestores/{STORE}/coverages/{LAYER}.xml" type="application/xml"/>
 *      </resource>
 *      <attribution>
 *              <title>test</title>
 *              <href>http://www.fao.org/fileadmin/templates/faoweb/images/FAO-logo.png</href>
 *              <logoURL>http://www.fao.org/fileadmin/templates/faoweb/images/FAO-logo.png</logoURL>
 *              <logoWidth>412</logoWidth>
 *              <logoHeight>77</logoHeight>
 *              <logoType>image/png</logoType>
 *      </attribution>
 *      ...
 *
 * </layer>
 * }
 * @since gs-2.2.x
 * @version $Id: $
 */
public class GSLayerEncoder extends PropertyXMLEncoder {
	
	/** Constant <code>STYLES="styles"</code> */
	public final static String STYLES = "styles";
	/** Constant <code>AUTHORITY_URLS="authorityURLs"</code> */
	public final static String AUTHORITY_URLS="authorityURLs";
	/** Constant <code>IDENTIFIERS="identifiers"</code> */
	public final static String IDENTIFIERS="identifiers";
	/** Constant <code>DEFAULT_STYLE="defaultStyle"</code> */
	public final static String DEFAULT_STYLE = "defaultStyle";
	
	final private Element stylesEncoder = new Element(STYLES);
	final private Element defaultStyleEncoder = new Element(DEFAULT_STYLE);
	final private Element authorityURLListEncoder = new Element(AUTHORITY_URLS);
	final private Element identifierListEncoder = new Element(IDENTIFIERS);
	
	/**
	 * <p>Constructor for GSLayerEncoder.</p>
	 */
	public GSLayerEncoder() {
	    this(true);
	}
	
    /**
     * <p>Constructor for GSLayerEncoder.</p>
     *
     * @param encodeLists a boolean.
     */
    public GSLayerEncoder(boolean encodeLists) {
        super("layer");
        addEnabled();
        if (encodeLists) {
            addContent(stylesEncoder);
            addContent(authorityURLListEncoder);
            addContent(identifierListEncoder);
        }
    }
    
    void encodeStyles(boolean encoded) {
        if (encoded) {
            if (this.get(stylesEncoder.getName()) == null) {
                addContent(stylesEncoder);            
            }
        } else {
            remove(stylesEncoder.getName());
        }
    }
    
    void encodeAuthorityURLs(boolean encoded) {
        if (encoded) {
            if (this.get(authorityURLListEncoder.getName()) == null) {
                addContent(authorityURLListEncoder);            
            }
        } else {
            remove(authorityURLListEncoder.getName());
        }
    }
    
    void encodeIdentifiers(boolean encoded) {
        if (encoded) {
            if (this.get(identifierListEncoder.getName()) == null) {
                addContent(identifierListEncoder);            
            }
        } else {
            remove(identifierListEncoder.getName());
        }
    }
    
    /**
     * enabled the layer
     */
    protected void addEnabled(){
        add("enabled","true");
    }
    
    /**
     * <p>setEnabled</p>
     *
     * @param enable true if layer should be set to enabled
     */
    public void setEnabled(boolean enable){
    	if (enable)
    		set("enabled","true");
    	else
    		set("enabled","false");
    }
        
//    private final static String DESCRIPTION = "description";
//    /**
//     * Add the 'description' node with a text value from 'description'
//     * 
//     */
//    protected void addDescription(final String description) {
//        add(DESCRIPTION, description);
//    }
//    /**
//     * Set or modify the 'description' node with a text value from 'description'
//     */
//    public void setDescription(final String description) {
//        set(DESCRIPTION, description);
//    }
    
//    queryable
    private final static String QUERYABLE = "queryable";
    /**
     * Add the 'queryable' node with a text value from 'queryable' (true as default)
     *
     * @param queryable a {@link java.lang.String} object.
     */
    protected void addQueryable(final String queryable) {
        add(QUERYABLE, queryable!=null?queryable.toString():"true");
    }
    /**
     * Set or modify the 'queryable' node with a text value from 'queryable' (true as default)
     *
     * @param queryable a {@link java.lang.Boolean} object.
     */
    public void setQueryable(final Boolean queryable) {
        set(QUERYABLE, queryable!=null?queryable.toString():"true");
    }

    /**
     * <p>addWmsPath</p>
     *
     * @see #setWmsPath(String)
     * @param path the wms path to set
     */
    protected void addWmsPath(final String path) {
        add("path", path);
    }
    
    /**
     * Default WMS Path The GeoServer WMS path is, by default, /geoserver/wms.
     * However you can change it to /geoserver/anythingyouwant
     *
     * @param path the wms path to set
     * @throws java.lang.IllegalArgumentException if path is null or empty
     */
    public void setWmsPath(final String path) throws IllegalArgumentException {
        if (path==null || path.isEmpty())
            throw new IllegalArgumentException("Unable to set an empty or null parameter"); 
        set("path",path);
    }

    /**
     * <p>addDefaultStyle</p>
     *
     * @see #setDefaultStyle(String)
     * @param defaultStyle a {@link java.lang.String} object.
     */
    protected void addDefaultStyle(String defaultStyle) {
        add("defaultStyle", defaultStyle);
    }
    
    /**
     * <p>addDefaultStyle</p>
     *
     * @see #setDefaultStyle(String)
     * @param defaultStyle a {@link java.lang.String} object.
     * @param workspace a {@link java.lang.String} object.
     */
    protected void addDefaultStyle(String workspace, String defaultStyle) {
        addContent(defaultStyleEncoder);
        Element el = new Element("name");
        el.setText(defaultStyle);
        defaultStyleEncoder.addContent(el);
        el = new Element("workspace");
        el.setText(workspace);
        defaultStyleEncoder.addContent(el);
    }

    /**
     * <p>setDefaultStyle</p>
     *
     * @param defaultStyle The style that will be applied if no style is specified.
     * @throws java.lang.IllegalArgumentException if defaultStyle is null or empty
     */
    public void setDefaultStyle(final String defaultStyle) throws IllegalArgumentException {
        if (defaultStyle==null || defaultStyle.isEmpty())
            throw new IllegalArgumentException("Unable to set an empty or null parameter");
        set("defaultStyle", defaultStyle);
    }
    
    /**
     * <p>setDefaultStyle</p>
     *
     * @see GSLayerEncoder#setDefaultStyle(String)
     * @param defaultStyle a {@link java.lang.String} object.
     * @param workspace a {@link java.lang.String} object.
     */
    public void setDefaultStyle(String workspace, String defaultStyle) {
        remove("defaultStyle");
        addDefaultStyle(workspace, defaultStyle);
    }
    
	/**
	 * Add a style
	 *
	 * @param style a {@link java.lang.String} object.
	 */
	public void addStyle(String style) {
		final Element el = new Element("style");
		el.setText(style);
		stylesEncoder.addContent(el);
	}

	/**
	 * delete a style from the list of available styles
	 *
	 * @param style a {@link java.lang.String} object.
	 * @return true if something is removed, false otherwise
	 */
	public boolean delStyle(final String style) {
		final Element el = new Element("style");
		el.setText(style);
		return (stylesEncoder.removeContent(new Filter() {
			private static final long serialVersionUID = 1L;

			public boolean matches(Object obj) {
				if (((Element) obj).getText().equals(style)) {
					return true;
				}
				return false;
			}
		})).size() == 0 ? false : true;
	}
	 
	/**
	 * <p>setAdvertised</p>
	 *
	 * @param advertised
	 *            true if the layer should be advertised
	 */
	public void setAdvertised(boolean advertised) {
		if (advertised)
			set("advertised", "true");
		else
			set("advertised", "false");
	}
    
	/**
	 * Add an authorityURLInfo to the GeoServer layer
	 *
	 * @param authorityURLInfo a {@link it.geosolutions.geoserver.rest.encoder.authorityurl.GSAuthorityURLInfoEncoder} object.
	 */
	public void addAuthorityURL(GSAuthorityURLInfoEncoder authorityURLInfo) {
		authorityURLListEncoder.addContent(authorityURLInfo.getRoot());
	}

	/**
	 * Deletes a AuthorityURLInfo from the list using the authorityURL
	 * (AuthorityURLInfo href)
	 *
	 * @param authorityURL a {@link java.lang.String} object.
	 * @return true if something is removed, false otherwise
	 */
	public boolean delAuthorityURL(final String authorityURL) {
		return (authorityURLListEncoder.removeContent(GSAuthorityURLInfoEncoder
				.getFilterByHref(authorityURL))).size() == 0 ? false : true;
	}

	/**
	 * Add an identifierInfo to the GeoServer layer
	 *
	 * @param identifierInfo a {@link it.geosolutions.geoserver.rest.encoder.identifier.GSIdentifierInfoEncoder} object.
	 */
	public void addIdentifier(GSIdentifierInfoEncoder identifierInfo) {
		identifierListEncoder.addContent(identifierInfo.getRoot());
	}

	/**
	 * Deletes a IdentifierInfo from the list using the authority name
	 * (IdentifierInfo authority)
	 *
	 * @param authority a {@link java.lang.String} object.
	 * @return true if something is removed, false otherwise
	 */
	public boolean delIdentifier(final String authority) {
		return (identifierListEncoder.removeContent(GSIdentifierInfoEncoder
				.getFilterByHref(authority))).size() == 0 ? false : true;
	}
}
