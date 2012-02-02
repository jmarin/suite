package org.geoserver.geoservices.exception;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class ServiceException {

    private ServiceError error;

    public ServiceError getError() {
        return error;
    }

    public void setServiceError(ServiceError error) {
        this.error = error;
    }
    
}
