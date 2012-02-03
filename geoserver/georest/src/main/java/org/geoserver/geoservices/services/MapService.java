package org.geoserver.geoservices.services;

import org.geoserver.geoservices.core.AbstractService;
import org.geoserver.geoservices.core.ServiceType;

public class MapService extends AbstractService {

    public MapService(String name) {
        super(name, ServiceType.MapServer);
    }

}
