package org.geoserver.geoservices.core;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public abstract class AbstractService {

    private String name;

    private ServiceType type;

    public AbstractService(String name, ServiceType serviceType) {
        this.name = name;
        this.type = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }
}
