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

import it.geosolutions.geoserver.rest.encoder.coverage.GSCoverageEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import it.geosolutions.geoserver.rest.encoder.metadata.GSDimensionInfoEncoder;
import it.geosolutions.geoserver.rest.encoder.metadata.GSFeatureDimensionInfoEncoder;
import it.geosolutions.geoserver.rest.encoder.metadatalink.GSMetadataLinkInfoEncoder;
import it.geosolutions.geoserver.rest.encoder.utils.ElementUtils;
import it.geosolutions.geoserver.rest.encoder.utils.NestedElementEncoder;
import it.geosolutions.geoserver.rest.encoder.utils.PropertyXMLEncoder;
import it.geosolutions.geoserver.rest.encoder.utils.XmlElement;

import org.jdom.Element;
import org.jdom.filter.Filter;

/**
 *
 * Encode a GeoServer resource.
 *
 * @see GSDimensionInfoEncoder
 * @see GSFeatureDimensionInfoEncoder
 * @author ETj (etj at geo-solutions.it)
 * @author Carlo Cancellieri - carlo.cancellieri@geo-solutions.it
 * @author Emmanuel Blondel - emmanuel.blondel1@gmail.com | emmanuel.blondel@fao.org
 * @author Henry Rotzoll
 * @version $Id: $
 */
public abstract class GSResourceEncoder extends PropertyXMLEncoder {
    /** Constant <code>NAME="name"</code> */
    public final static String NAME = "name";

    /** Constant <code>NATIVENAME="nativeName"</code> */
    public final static String NATIVENAME = "nativeName";

    /** Constant <code>METADATA="metadata"</code> */
    public final static String METADATA = "metadata";

    /** Constant <code>KEYWORDS="keywords"</code> */
    public final static String KEYWORDS = "keywords";

    /** Constant <code>METADATALINKS="metadataLinks"</code> */
    public final static String METADATALINKS = "metadataLinks";

    final private GSMetadataEncoder metadata = new GSMetadataEncoder();

    final private Element keywordsListEncoder = new Element(KEYWORDS);

    final private Element metadataLinksListEncoder = new Element(METADATALINKS);

    private class GSMetadataEncoder extends NestedElementEncoder {
        public GSMetadataEncoder() {
            super(METADATA);
        }
    }
    
    /**
     * <p>Constructor for GSResourceEncoder.</p>
     *
     * @param rootName a {@link java.lang.String} object.
     */
    protected GSResourceEncoder(final String rootName) {
        this(rootName, true);        
    }

    /**
     * <p>Constructor for GSResourceEncoder.</p>
     *
     * @param rootName Actually 'feature' or 'coverage'
     * @see GSFeatureTypeEncoder
     * @see GSCoverageEncoder
     * @param encodeLists a boolean.
     */
    protected GSResourceEncoder(final String rootName, boolean encodeLists) {
        super(rootName);
        add("enabled", "true");

        if (encodeLists) {
            // Link members to the parent
            addContent(metadata.getRoot());
            addContent(keywordsListEncoder);
            addContent(metadataLinksListEncoder);
        }
    }
    
    void encodeMetadata(boolean encoded) {
        if (encoded) {
            if (this.get(metadata.getRoot().getName()) == null) {
                addContent(metadata.getRoot());            
            }
        } else {
            remove(metadata.getRoot().getName());
        }
    }
    
    void encodeKeywords(boolean encoded) {
        if (encoded) {
            if (this.get(keywordsListEncoder.getName()) == null) {
                addContent(keywordsListEncoder);            
            }
        } else {
            remove(keywordsListEncoder.getName());
        }
    }
    
    void encodeMetadatalinks(boolean encoded) {
        if (encoded) {
            if (this.get(metadataLinksListEncoder.getName()) == null) {
                addContent(metadataLinksListEncoder);            
            }
        } else {
            remove(metadataLinksListEncoder.getName());
        }
    }

    /**
     * <p>setEnabled</p>
     *
     * @param enabled a boolean.
     */
    public void setEnabled(boolean enabled) {
        set("enabled", (enabled) ? "true" : "false");
    }
    
    /**
     * <p>setAdvertised</p>
     *
     * @param advertised
     *            true if the resource should be advertised
     */
    public void setAdvertised(boolean advertised) {
        set("advertised", (advertised) ? "true" : "false");
    }

    /**
     * <p>addMetadata</p>
     *
     * @param key a {@link java.lang.String} object.
     * @param dimensionInfo a {@link it.geosolutions.geoserver.rest.encoder.utils.XmlElement} object.
     */
    protected void addMetadata(String key, XmlElement dimensionInfo) {
        metadata.add(key, dimensionInfo.getRoot());
    }

    /**
     * <p>Setter for the field <code>metadata</code>.</p>
     *
     * @deprecated Use {@link #setMetadataDimension(String, GSDimensionInfoEncoder)} this method will be set as protected for internal use only
     * @param key a {@link java.lang.String} object.
     * @param dimensionInfo a {@link it.geosolutions.geoserver.rest.encoder.utils.XmlElement} object.
     */
    public void setMetadata(String key, XmlElement dimensionInfo) {
        metadata.set(key, dimensionInfo.getRoot());
    }

    /**
     * <p>addMetadataDimension</p>
     *
     * @param key a {@link java.lang.String} object.
     * @param dimensionInfo a {@link it.geosolutions.geoserver.rest.encoder.metadata.GSDimensionInfoEncoder} object.
     */
    protected void addMetadataDimension(String key, GSDimensionInfoEncoder dimensionInfo) {
        addMetadataDimension(key, dimensionInfo, false);
    }

    /**
     * Add the metadata for a custom dimension.
     *
     * @param key the name of the dimension
     * @param dimensionInfo {@link GSDimensionInfoEncoder} with additional information about the dimension
     * @param custom is the dimension custom or not?
     */
    protected void addMetadataDimension(String key, GSDimensionInfoEncoder dimensionInfo,
            boolean custom) {
        if (custom) {
            metadata.set("custom_dimension_" + key.toUpperCase(), dimensionInfo.getRoot());
        } else {
            metadata.add(key, dimensionInfo.getRoot());
        }
    }

    /**
     * <p>setMetadataDimension</p>
     *
     * @param key a {@link java.lang.String} object.
     * @param dimensionInfo a {@link it.geosolutions.geoserver.rest.encoder.metadata.GSDimensionInfoEncoder} object.
     */
    public void setMetadataDimension(String key, GSDimensionInfoEncoder dimensionInfo) {
        setMetadataDimension(key, dimensionInfo, false);
    }

    /**
     * <p>setMetadataString</p>
     *
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     */
    public void setMetadataString(String key, String value) {
        metadata.set(key, value);
    }

    /**
     * Set the metadata for a custom dimension.
     *
     * @param key the name of the dimension
     * @param dimensionInfo {@link GSDimensionInfoEncoder} with additional information about the dimension
     * @param custom is the dimension custom or not?
     */
    public void setMetadataDimension(String key, GSDimensionInfoEncoder dimensionInfo,
            boolean custom) {
        if (custom) {
            metadata.set("custom_dimension_" + key.toUpperCase(), dimensionInfo.getRoot());
        } else {
            metadata.set(key, dimensionInfo.getRoot());
        }
    }

    /**
     * <p>delMetadata</p>
     *
     * @param key the name of the metadata to add (f.e.: elevation, time)
     * @return true if something is removed, false otherwise
     */
    public boolean delMetadata(String key) {
        return metadata.remove(key);
    }

    /**
     * <p>addKeyword</p>
     *
     * @param keyword a {@link java.lang.String} object.
     */
    public void addKeyword(String keyword) {
        checkKeyword(keyword);
        putKeyword(keyword);
    }

    /**
     * <PRE>{@code
     * <keywords>
     *  <string>WCS</string>
     *  <string>ImageMosaic</string>
     *  <string>srtm30</string>
     *  <string>KEYWORD\@language=LANGUAGE\;\@vocabulary=VOCABULARY\;</string>
     *  <string>KEYWORD_2\@vocabulary=VOCABULARY_2\;</string>
     *  <string>KEYWORD_3\@language=LANGUAGE_3\;</string> </keywords>
     * }</PRE>
     *
     * @param keyword mandatory keyword ('\' characters are not permitted)
     * @param language optional parameter
     * @param vocabulary optional parameter
     */
    public void addKeyword(final String keyword, final String language, final String vocabulary) {
        checkKeyword(keyword);
        putKeyword(buildKeyword(keyword, language, vocabulary));
    }

    /**
     * delete a keyword from the list
     *
     * @param keyword a {@link java.lang.String} object.
     * @return true if something is removed, false otherwise
     */
    public boolean delKeyword(final String keyword) {
        return removeKeyword(keyword, null, null);
    }

    /**
     * delete a keyword from the list
     *
     * @param keyword a {@link java.lang.String} object.
     * @return true if something is removed, false otherwise
     * @param language a {@link java.lang.String} object.
     * @param vocabulary a {@link java.lang.String} object.
     */
    public boolean delKeyword(final String keyword, final String language, final String vocabulary) {
        return removeKeyword(keyword, language, vocabulary);
    }

    private boolean removeKeyword(final String keyword, final String language,
            final String vocabulary) {
        checkKeyword(keyword);
        final String text = buildKeyword(keyword, language, vocabulary);
        return (keywordsListEncoder.removeContent(new Filter() {
            private static final long serialVersionUID = 1L;

            public boolean matches(Object obj) {
                if (((Element) obj).getText().equals(text)) {
                    return true;
                }
                return false;
            }
        })).size() == 0 ? false : true;
    }

    private void putKeyword(String keyword) {
        final Element el = new Element("string");
        el.setText(keyword);
        keywordsListEncoder.addContent(el);
    }

    private void checkKeyword(String keyword) {
        if (keyword == null || keyword.isEmpty() || keyword.contains("\\")) {
            throw new IllegalArgumentException("keyword may not be null, empty or contains '\'");
        }
    }

    private String buildKeyword(final String keyword, final String language, final String vocabulary) {
        StringBuilder sb = new StringBuilder(keyword);
        // \@language={LANGUAGE_3}\;
        if (language != null && !language.isEmpty()) {
            sb.append("\\@language=").append(language).append("\\;");
        }
        // \@vocabulary={VOCABULARY}\;
        if (vocabulary != null && !vocabulary.isEmpty()) {
            sb.append("\\@vocabulary=").append(vocabulary).append("\\;");
        }
        return sb.toString();
    }

    /**
     * Adds a MetadataLinkInfo to the GeoServer Resource
     *
     * @param  metadataLinkInfo a {@link it.geosolutions.geoserver.rest.encoder.metadatalink.GSMetadataLinkInfoEncoder} object.
     */
    public void addMetadataLinkInfo(GSMetadataLinkInfoEncoder metadataLinkInfo) {
        metadataLinksListEncoder.addContent(metadataLinkInfo.getRoot());
    }

    /**
     * Adds quickly a MetadataLinkInfo to the GeoServer Resource
     *
     * @param type a {@link java.lang.String} object.
     * @param metadataType a {@link java.lang.String} object.
     * @param content a {@link java.lang.String} object.
     */
    public void addMetadataLinkInfo(String type, String metadataType, String content) {
        final GSMetadataLinkInfoEncoder mde = new GSMetadataLinkInfoEncoder(type, metadataType,
                content);
        metadataLinksListEncoder.addContent(mde.getRoot());
    }

    /**
     * Deletes a metadataLinkInfo from the list using the metadataURL (MetadataLinkInfo content)
     *
     * @param metadataURL a {@link java.lang.String} object.
     * @return true if something is removed, false otherwise
     */
    public boolean delMetadataLinkInfo(final String metadataURL) {
        return (metadataLinksListEncoder.removeContent(GSMetadataLinkInfoEncoder
                .getFilterByContent(metadataURL))).size() == 0 ? false : true;
    }

    /**
     * Reprojection policy for a published layer. One of:
     * <ul>
     * <li>{@link #REPROJECT_TO_DECLARED} Reproject from native to declared CRS
     * <li>{@link #FORCE_DECLARED} Use the declared CRS (ignore native)
     * <li>{@link #NONE} No reprojection (use native CRS)
     * </ul>
     */
    public enum ProjectionPolicy {
        /** Reproject from native to declared CRS */
        REPROJECT_TO_DECLARED,
        /** Use the declared CRS (ignore native) */
        FORCE_DECLARED,
        /** Keep native */
        NONE
    }

    private final static String PROJECTIONPOLICY = "projectionPolicy";

    /**
     * NONE, REPROJECT_TO_DECLARED, FORCE_DECLARED
     *
     * @param policy a {@link it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy} object.
     */
    protected void addProjectionPolicy(ProjectionPolicy policy) {
        add(PROJECTIONPOLICY, policy.toString());
    }

    /**
     * NONE, REPROJECT_TO_DECLARED, FORCE_DECLARED
     *
     * @param policy a {@link it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy} object.
     */
    public void setProjectionPolicy(ProjectionPolicy policy) {
        set(PROJECTIONPOLICY, policy.toString());
    }

    /**
     * Add the 'name' node with a text value from 'name'
     *
     * REQUIRED to configure a resource
     *
     * @param name a {@link java.lang.String} object.
     */
    protected void addName(final String name) {
        add(NAME, name);
    }

    /**
     * Set or modify the 'name' node with a text value from 'name'
     *
     * REQUIRED to configure a resource
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(final String name) {
        set(NAME, name);
    }

    /**
     * <p>getName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        final Element nameNode = ElementUtils.contains(getRoot(), NAME, 1);
        if (nameNode != null)
            return nameNode.getText();
        else
            return null;
    }

    /**
     * Add the 'nativename' node with a text value from 'name'
     *
     * @param nativename a {@link java.lang.String} object.
     */
    protected void addNativeName(final String nativename) {
        add(NATIVENAME, nativename);
    }

    /**
     * Set or modify the 'nativename' node with a text value from 'name'
     *
     * if not specified, the nativeName will be set with the value of the 'name' node.
     *
     * @param nativename a {@link java.lang.String} object.
     */
    public void setNativeName(final String nativename) {
        set(NATIVENAME, nativename);
    }

    /**
     * Get the nativeName
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNativeName() {
        final Element nameNode = ElementUtils.contains(getRoot(), NATIVENAME, 1);
        if (nameNode != null)
            return nameNode.getText();
        else
            return null;
    }

    private final static String DESCRIPTION = "description";

    /**
     * Add the 'description' node with a text value from 'description'
     *
     * @param description a {@link java.lang.String} object.
     */
    protected void addDescription(final String description) {
        add(DESCRIPTION, description);
    }

    /**
     * Set or modify the 'description' node with a text value from 'description'
     *
     * @param description a {@link java.lang.String} object.
     */
    public void setDescription(final String description) {
        set(DESCRIPTION, description);
    }

    private final static String ABSTRACT = "abstract";

    /**
     * Add the 'abstract' node with a text value from 'abstract'
     *
     * @param _abstract a {@link java.lang.String} object.
     */
    protected void addAbstract(final String _abstract) {
        add(ABSTRACT, _abstract);
    }

    /**
     * Set or modify the 'abstract' node with a text value from 'abstract'
     *
     * @param _abstract a {@link java.lang.String} object.
     */
    public void setAbstract(final String _abstract) {
        set(ABSTRACT, _abstract);
    }

    private final static String TITLE = "title";

    /**
     * Add the 'title' node with a text value from 'title'
     *
     * @param title a {@link java.lang.String} object.
     */
    protected void addTitle(final String title) {
        add(TITLE, title);
    }

    /**
     * Set or modify the 'title' node with a text value from 'title'
     *
     * @param title a {@link java.lang.String} object.
     */
    public void setTitle(final String title) {
        set(TITLE, title);
    }

    private final static String SRS = "srs";

    /**
     * Add the 'SRS' node with a text value from 'srs'
     *
     * @param srs a {@link java.lang.String} object.
     */
    protected void addSRS(final String srs) {
        add(SRS, srs);
    }

    /**
     * Set or modify the 'SRS' node with a text value from 'srs'
     *
     * @param srs a {@link java.lang.String} object.
     */
    public void setSRS(final String srs) {
        set(SRS, srs);
    }

    private final static String NATIVECRS = "nativeCRS";

    /**
     * Add the 'nativeCRS' node with a text value from 'nativeCRS'
     *
     * @param nativeCRS a {@link java.lang.String} object.
     */
    protected void addNativeCRS(final String nativeCRS) {
        add(NATIVECRS, nativeCRS);
    }

    /**
     * Set or modify the 'nativeCRS' node with a text value from 'nativeCRS'
     *
     * @param nativeCRS a {@link java.lang.String} object.
     */
    public void setNativeCRS(final String nativeCRS) {
        set(NATIVECRS, nativeCRS);
    }

    private final static String LATLONBBMINX = "latLonBoundingBox/minx";

    private final static String LATLONBBMAXX = "latLonBoundingBox/maxx";

    private final static String LATLONBBMINY = "latLonBoundingBox/miny";

    private final static String LATLONBBMAXY = "latLonBoundingBox/maxy";

    private final static String LATLONBBCRS = "latLonBoundingBox/crs";

    /**
     * <p>addLatLonBoundingBox</p>
     *
     * @param minx a double.
     * @param maxy a double.
     * @param maxx a double.
     * @param miny a double.
     * @param crs a {@link java.lang.String} object.
     */
    protected void addLatLonBoundingBox(double minx, double miny, double maxx, double maxy,
            final String crs) {
        add(LATLONBBMINX, String.valueOf(minx));
        add(LATLONBBMINY, String.valueOf(miny));
        add(LATLONBBMAXY, String.valueOf(maxy));
        add(LATLONBBMAXX, String.valueOf(maxx));
        add(LATLONBBCRS, crs);
    }

    /**
     * <p>setLatLonBoundingBox</p>
     *
     * @param minx a double.
     * @param miny a double.
     * @param maxx a double.
     * @param maxy a double.
     * @param crs a {@link java.lang.String} object.
     */
    public void setLatLonBoundingBox(double minx, double miny, double maxx, double maxy,
            final String crs) {
        set(LATLONBBMINX, String.valueOf(minx));
        set(LATLONBBMAXY, String.valueOf(maxy));
        set(LATLONBBMAXX, String.valueOf(maxx));
        set(LATLONBBMINY, String.valueOf(miny));
        set(LATLONBBCRS, crs);
    }

    private final static String NATIVEBBMINX = "nativeBoundingBox/minx";

    private final static String NATIVEBBMAXX = "nativeBoundingBox/maxx";

    private final static String NATIVEBBMINY = "nativeBoundingBox/miny";

    private final static String NATIVEBBMAXY = "nativeBoundingBox/maxy";

    private final static String NATIVEBBCRS = "nativeBoundingBox/crs";

    /**
     * <p>addNativeBoundingBox</p>
     *
     * @param minx a double.
     * @param maxy a double.
     * @param maxx a double.
     * @param miny a double.
     * @param crs a {@link java.lang.String} object.
     */
    protected void addNativeBoundingBox(double minx, double miny, double maxx, double maxy,
            final String crs) {
        add(NATIVEBBMINX, String.valueOf(minx));
        add(NATIVEBBMAXY, String.valueOf(maxy));
        add(NATIVEBBMAXX, String.valueOf(maxx));
        add(NATIVEBBMINY, String.valueOf(miny));
        add(NATIVEBBCRS, crs);
    }

    /**
     * <p>setNativeBoundingBox</p>
     *
     * @param minx a double.
     * @param miny a double.
     * @param maxx a double.
     * @param maxy a double.
     * @param crs a {@link java.lang.String} object.
     */
    public void setNativeBoundingBox(double minx, double miny, double maxx, double maxy,
            final String crs) {
        set(NATIVEBBMINX, String.valueOf(minx));
        set(NATIVEBBMAXY, String.valueOf(maxy));
        set(NATIVEBBMAXX, String.valueOf(maxx));
        set(NATIVEBBMINY, String.valueOf(miny));
        set(NATIVEBBCRS, crs);
    }

}
