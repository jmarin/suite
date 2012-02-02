package org.geoserver.geoservices.geometry;

import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.core.ServiceType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias("GeometryService")
public class GeometryService extends AbstractService{

    public GeometryService(String name) {
        super(name, ServiceType.GeometryServer);
    }

}
