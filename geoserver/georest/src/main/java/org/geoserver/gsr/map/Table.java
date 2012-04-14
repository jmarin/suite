package org.geoserver.geoservices.map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias(value = "")
public class Table extends AbstractLayer {

    public Table(int id, String name) {
        super(id, name);
    }

}
