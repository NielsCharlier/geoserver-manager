/*
 *  GeoServer-Manager - Simple Manager Library for GeoServer
 *  
 *  Copyright (C) 2007,2014 GeoSolutions S.A.S.
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
import it.geosolutions.geoserver.rest.encoder.dimensions.GSCoverageDimensionEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.FeatureTypeAttribute;
import it.geosolutions.geoserver.rest.encoder.feature.GSAttributeEncoder;
import it.geosolutions.geoserver.rest.encoder.metadatalink.GSMetadataLinkInfoEncoder;
import it.geosolutions.geoserver.rest.encoder.metadatalink.ResourceMetadataLinkInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.Namespace;

/**
 * Parse a resource (FeatureType or Coverage) returned as XML REST objects.
 *
 * @author Emanuele Tajariol etj@geo-solutions.it
 * @author Emmanuel Blondel - emmanuel.blondel1@gmail.com | emmanuel.blondel@fao.org
 * @author Henry Rotzoll
 * @version $Id: $
 */
public class RESTResource {
    protected final Element rootElem;

    /**
     * <p>build</p>
     *
     * @param response a {@link java.lang.String} object.
     * @return a {@link it.geosolutions.geoserver.rest.decoder.RESTResource} object.
     */
    public static RESTResource build(String response) {
        Element elem = JDOMBuilder.buildElement(response);
        return elem == null ? null : new RESTCoverage(elem);
    }

    /**
     * <p>Constructor for RESTResource.</p>
     *
     * @param resource a {@link org.jdom.Element} object.
     */
    public RESTResource(Element resource) {
        this.rootElem = resource;
    }

    /**
     * <p>getName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return rootElem.getChildText("name");
    }

    /**
     * <p>getTitle</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getTitle() {
        return rootElem.getChildText("title");
    }

    /**
     * <p>getNativeName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNativeName() {
        return rootElem.getChildText("nativeName");
    }

    /**
     * <p>getAbstract</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAbstract() {
        return rootElem.getChildText("abstract");
    }

    /**
     * <p>getKeywords</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<String> getKeywords() {
        List<String> kwdsList = null;

        final Element keywordsRoot = rootElem.getChild("keywords");
        if (keywordsRoot != null) {
            final List<Element> keywords = keywordsRoot.getChildren();
            if (keywords != null) {
                kwdsList = new ArrayList<String>(keywords.size());
                for (Element keyword : keywords) {
                    kwdsList.add(keyword.getValue());
                }
            }
        }
        return kwdsList;
    }

    /**
     * <p>getNameSpace</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNameSpace() {
        return rootElem.getChild("namespace").getChildText("name");
    }

    /**
     * <p>getStoreName</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStoreName() {
        return rootElem.getChild("store").getChildText("name");
    }

    /**
     * <p>getStoreType</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStoreType() {
        return rootElem.getChild("store").getAttributeValue("class");
    }

    /**
     * <p>getStoreUrl</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStoreUrl() {
        Element store = rootElem.getChild("store");
        Element atom = store.getChild("link",
                Namespace.getNamespace("atom", "http://www.w3.org/2005/Atom"));
        return atom.getAttributeValue("href");
    }

    /**
     * <p>getNativeCRS</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNativeCRS() {
    	return rootElem.getChildText("nativeCRS");
    }

	/**
	 * <p>getNativeBoundingBox</p>
	 *
	 * @return a {@link it.geosolutions.geoserver.rest.decoder.RESTBoundingBox} object.
	 */
	public RESTBoundingBox getNativeBoundingBox() {
		RESTBoundingBox bbox = null;
		Element bboxElement = rootElem.getChild("nativeBoundingBox");
		if (bboxElement != null) {
			bbox = new RESTBoundingBox(bboxElement);
		}
		return bbox;
	}
	
	/**
	 * <p>getLatLonBoundingBox</p>
	 *
	 * @return a {@link it.geosolutions.geoserver.rest.decoder.RESTBoundingBox} object.
	 */
	public RESTBoundingBox getLatLonBoundingBox() {
		RESTBoundingBox bbox = null;
		Element bboxElement = rootElem.getChild("latLonBoundingBox");
		if (bboxElement != null) {
			bbox = new RESTBoundingBox(bboxElement);
		}
		return bbox;
	}

    /**
     * <p>getCRS</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCRS() {
    	RESTBoundingBox bbox = this.getLatLonBoundingBox();
    	return bbox.getCRS();
    }

    /**
     * <p>getMinX</p>
     *
     * @return a double.
     */
    public double getMinX() {
        return this.getLatLonBoundingBox().getMinX();
    }

    /**
     * <p>getMaxX</p>
     *
     * @return a double.
     */
    public double getMaxX() {
    	return this.getLatLonBoundingBox().getMaxX();
    }

    /**
     * <p>getMinY</p>
     *
     * @return a double.
     */
    public double getMinY() {
    	return this.getLatLonBoundingBox().getMinY();
    }

    /**
     * <p>getMaxY</p>
     *
     * @return a double.
     */
    public double getMaxY() {
    	return this.getLatLonBoundingBox().getMaxY();
    }

    /**
     * <p>getAttributeList</p>
     *
     * @deprecated use {@link RESTFeatureType#getAttributeList()}
     * @throws java.lang.UnsupportedOperationException
     * @return a {@link java.util.List} object.
     */
    public List<Map<FeatureTypeAttribute, String>> getAttributeList() {
        throw new UnsupportedOperationException("This method is specific for RESTFeatureType");
    }

    /**
     * <p>getEncodedAttributeList</p>
     *
     * @deprecated use {@link RESTFeatureType#getEncodedAttributeList()}
     * @throws java.lang.UnsupportedOperationException
     * @return a {@link java.util.List} object.
     */
    public List<GSAttributeEncoder> getEncodedAttributeList() {
        throw new UnsupportedOperationException("This method is specific for RESTFeatureType");
    }

    /**
     * Decodes the list of MetadataLinkInfo from the GeoServer Resource
     *
     * @since gs-2.4.x
     * @return the list of GSMetadataLinkEncoder
     */
    public List<GSMetadataLinkInfoEncoder> getEncodedMetadataLinkInfoList() {
        List<GSMetadataLinkInfoEncoder> metaLinksList = null;

        final Element metaLinksRoot = rootElem.getChild("metadataLinks");
        if (metaLinksRoot != null) {
            final List<Element> metaLinks = metaLinksRoot.getChildren();
            if (metaLinks != null) {
                metaLinksList = new ArrayList<GSMetadataLinkInfoEncoder>(metaLinks.size());
                for (Element metaLink : metaLinks) {
                    final GSMetadataLinkInfoEncoder metaLinkEnc = new GSMetadataLinkInfoEncoder();
                    metaLinkEnc
                            .setType(metaLink.getChildText(ResourceMetadataLinkInfo.type.name()));
                    metaLinkEnc.setMetadataType(metaLink
                            .getChildText(ResourceMetadataLinkInfo.metadataType.name()));
                    metaLinkEnc.setContent(metaLink.getChildText(ResourceMetadataLinkInfo.content
                            .name()));
                    metaLinksList.add(metaLinkEnc);
                }

            }
        }
        return metaLinksList;
    }

    /**
     * Decodes the list of GSCoverageDimensionEncoder from the GeoServer Resource
     *
     * @since gs-2.4.x
     * @return the list of GSCoverageDimensionEncoder
     */
    public List<GSCoverageDimensionEncoder> getEncodedDimensionsInfoList() {
        List<GSCoverageDimensionEncoder> dimensionList = null;
        final Element dimensionsRoot = rootElem.getChild("dimensions");

        if (dimensionsRoot != null) {
            final List<Element> dimensions = dimensionsRoot.getChildren();
            if (dimensions != null) {
                dimensionList = new ArrayList<GSCoverageDimensionEncoder>(dimensions.size());
                for (Element coverageDimension : dimensions) {
                    final String name = coverageDimension.getChildText("name");
                    final String description = coverageDimension.getChildText("description");
                    String rangeMin = null;
                    String rangeMax = null;
                    final Element rangeElement = coverageDimension.getChild("range");
                    if (rangeElement != null) {
                        rangeMin = rangeElement.getChildText("min");
                        rangeMax = rangeElement.getChildText("max");
                    }
                    final String unit = coverageDimension.getChildText("unit");
                    String dimensionTypeName = null;
                    final Element dimensionTypeElement = coverageDimension
                            .getChild("dimensionType");
                    if (dimensionTypeElement != null) {
                        dimensionTypeName = dimensionTypeElement.getChildText("name");
                    }
                    final GSCoverageDimensionEncoder coverageDimensionEncoder = new GSCoverageDimensionEncoder(
                            name, description, rangeMin, rangeMax, unit, dimensionTypeName);
                    dimensionList.add(coverageDimensionEncoder);
                }

            }
        }

        return dimensionList;
    }
}
