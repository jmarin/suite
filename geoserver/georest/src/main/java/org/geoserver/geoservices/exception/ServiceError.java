package org.geoserver.geoservices.exception;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class ServiceError {

    private String code;

    private String message;

    private String[] details;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getDetails() {
        return details;
    }

    public void setDetails(String[] details) {
        this.details = details;
    }

}
