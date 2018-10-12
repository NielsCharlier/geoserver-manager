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
import it.geosolutions.geoserver.rest.encoder.utils.PropertyXMLEncoder;

import org.jdom.Element;


/**
 * <p>GSBackupEncoder class.</p>
 *
 * @author Alessio Fabiani (alessio.fabiani at geo-solutions.it)
 * @version $Id: $
 */
public class GSBackupEncoder extends PropertyXMLEncoder
{
    /** Constant <code>TASK="task"</code> */
    public static final String TASK = "task";
    /** Constant <code>PATH="path"</code> */
    public static final String PATH = "path";
    /** Constant <code>INCLUDE_DATA="includedata"</code> */
    public static final String INCLUDE_DATA = "includedata";
    /** Constant <code>INCLUDE_GWC="includegwc"</code> */
    public static final String INCLUDE_GWC = "includegwc";
    /** Constant <code>INCLUDE_LOG="includelog"</code> */
    public static final String INCLUDE_LOG = "includelog";

    /**
     * <p>Constructor for GSBackupEncoder.</p>
     */
    public GSBackupEncoder()
    {
        super(TASK);
    }

    /**
     * <p>Constructor for GSBackupEncoder.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public GSBackupEncoder(String name)
    {
        super(TASK);
        addPath(name);
    }

    /**
     * Add the path to this task
     *
     * @param path a {@link java.lang.String} object.
     * @throws java.lang.IllegalStateException if path is already set
     * @deprecated will be set to protected in the next release
     */
    public void addPath(final String path)
    {
        final Element el = ElementUtils.contains(getRoot(), PATH);
        if (el == null)
        {
            add(PATH, path);
        }
        else
        {
            throw new IllegalStateException("Task path is already set: " + el.getText());
        }
    }

    /**
     * add or change (if already set) the task path
     *
     * @param path a {@link java.lang.String} object.
     */
    public void setPath(final String path)
    {
        final Element el = ElementUtils.contains(getRoot(), PATH);
        if (el == null)
        {
            add(PATH, path);
        }
        else
        {
            el.setText(path);
        }
    }

    /**
     * <p>getPath</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getPath()
    {
        final Element el = ElementUtils.contains(getRoot(), PATH);
        if (el != null)
        {
            return el.getTextTrim();
        }
        else
        {
            return null;
        }
    }

    /**
     * INCLUDE DATA ELEMENT
     */
    
    /**
     * Add the includedata to this task
     *
     * @param includedata a {@link java.lang.Boolean} object.
     * @deprecated will be set to protected in the next release
     */
    public void addIncludeData(final Boolean includedata)
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_DATA);
        if (el == null)
        {
            add(INCLUDE_DATA, includedata.toString());
        }
        else
        {
        	el.setText(includedata.toString());
        }
    }

    /**
     * add or change (if already set) the task includedata
     *
     * @param includedata a {@link java.lang.Boolean} object.
     */
    public void setIncludeData(final Boolean includedata)
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_DATA);
        if (el == null)
        {
            add(INCLUDE_DATA, includedata.toString());
        }
        else
        {
            el.setText(includedata.toString());
        }
    }

    /**
     * <p>getIncludeData</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getIncludeData()
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_DATA);
        if (el != null)
        {
            return el.getTextTrim();
        }
        else
        {
            return null;
        }
    }
    
    /**
     * INCLUDE GWC ELEMENT
     */
    
    /**
     * Add the includegwc to this task
     *
     * @param includegwc a {@link java.lang.Boolean} object.
     * @deprecated will be set to protected in the next release
     */
    public void addIncludeGwc(final Boolean includegwc)
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_GWC);
        if (el == null)
        {
            add(INCLUDE_GWC, includegwc.toString());
        }
        else
        {
        	el.setText(includegwc.toString());
        }
    }

    /**
     * add or change (if already set) the task includegwc
     *
     * @param includegwc a {@link java.lang.Boolean} object.
     */
    public void setIncludeGwc(final Boolean includegwc)
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_GWC);
        if (el == null)
        {
            add(INCLUDE_GWC, includegwc.toString());
        }
        else
        {
            el.setText(includegwc.toString());
        }
    }

    /**
     * <p>getIncludeGwc</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getIncludeGwc()
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_GWC);
        if (el != null)
        {
            return el.getTextTrim();
        }
        else
        {
            return null;
        }
    }
    
    /**
     * INCLUDE LOG ELEMENT
     */
    
    /**
     * Add the includelog to this task
     *
     * @param includelog a {@link java.lang.Boolean} object.
     * @deprecated will be set to protected in the next release
     */
    public void addIncludeLog(final Boolean includelog)
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_LOG);
        if (el == null)
        {
            add(INCLUDE_LOG, includelog.toString());
        }
        else
        {
        	el.setText(includelog.toString());
        }
    }

    /**
     * add or change (if already set) the task includelog
     *
     * @param includelog a {@link java.lang.Boolean} object.
     */
    public void setIncludeLog(final Boolean includelog)
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_LOG);
        if (el == null)
        {
            add(INCLUDE_LOG, includelog.toString());
        }
        else
        {
            el.setText(includelog.toString());
        }
    }

    /**
     * <p>getIncludeLog</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getIncludeLog()
    {
        final Element el = ElementUtils.contains(getRoot(), INCLUDE_LOG);
        if (el != null)
        {
            return el.getTextTrim();
        }
        else
        {
            return null;
        }
    }
}
