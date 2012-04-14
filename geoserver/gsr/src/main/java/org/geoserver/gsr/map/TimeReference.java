package org.geoserver.gsr.map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias(value = "")
public class TimeReference {

    private String timeZone;
    
    private boolean respectDaylightSaving;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isRespectDaylightSaving() {
        return respectDaylightSaving;
    }

    public void setRespectDaylightSaving(boolean respectDaylightSaving) {
        this.respectDaylightSaving = respectDaylightSaving;
    }
    
}
