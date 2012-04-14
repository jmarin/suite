package org.geoserver.gsr.exception;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */

@XStreamAlias(value = "")
public class ServiceException {

    private ServiceError error;

    public ServiceError getError() {
        return error;
    }

    public void setServiceError(ServiceError error) {
        this.error = error;
    }

    public ServiceException(ServiceError error) {
        this.error = error;
    }

}
