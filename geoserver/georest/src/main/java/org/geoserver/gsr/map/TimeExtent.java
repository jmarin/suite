package org.geoserver.geoservices.map;

import java.util.Date;

/**
 * 
 * @author Juan Marin, OpenGeo
 * 
 */


public class TimeExtent {

    private String type;
    
    private Date format;
    
    public static final Integer minItems = 2;
    
    public static final Integer maxItems = 2;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getFormat() {
        return format;
    }

    public void setFormat(Date format) {
        this.format = format;
    }
    
}
