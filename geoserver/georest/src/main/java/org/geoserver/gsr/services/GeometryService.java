package org.geoserver.gsr.services;

import org.geoserver.gsr.core.AbstractService;
import org.geoserver.gsr.core.ServiceType;

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
