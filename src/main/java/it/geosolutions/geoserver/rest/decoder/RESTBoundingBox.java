package it.geosolutions.geoserver.rest.decoder;

import org.jdom.Element;

/**
 * Parse a Boundingbox of the following structure
 *
 * <PRE>
 * {@code
 * <minx>472800.0</minx>
 * <maxx>817362.0</maxx>
 * <miny>35053.40625</miny>
 * <maxy>301500.0</maxy>
 * <crs class="projected">EPSG:21781</crs>
 * }
 * </PRE>
 *
 * @author nmandery
 * @version $Id: $
 */
public class RESTBoundingBox {

	protected Element bboxElem;
	
	/**
	 * <p>Constructor for RESTBoundingBox.</p>
	 *
	 * @param bboxElem a {@link org.jdom.Element} object.
	 */
	public RESTBoundingBox(Element bboxElem) {
		this.bboxElem = bboxElem;
	}
	
	/**
	 * <p>getCRS</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCRS() {
		return this.bboxElem.getChildText("crs");
	}
	
	/**
	 * <p>getEdge</p>
	 *
	 * @param edge a {@link java.lang.String} object.
	 * @return a double.
	 */
	protected double getEdge(String edge) {
		return Double.parseDouble(this.bboxElem.getChildText(edge));
	}

	/**
	 * <p>getMinX</p>
	 *
	 * @return a double.
	 */
	public double getMinX() {
		return this.getEdge("minx");
	}
	
	/**
	 * <p>getMaxX</p>
	 *
	 * @return a double.
	 */
	public double getMaxX() {
		return this.getEdge("maxx");
	}

	/**
	 * <p>getMinY</p>
	 *
	 * @return a double.
	 */
	public double getMinY() {
		return this.getEdge("miny");
	}

	/**
	 * <p>getMaxY</p>
	 *
	 * @return a double.
	 */
	public double getMaxY() {
		return this.getEdge("maxy");
	}

}
