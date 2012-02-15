package org.geoserver.geoservices.map;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

@XStreamAlias(value = "")
public class TimeInfo {

    private List<TimeExtent> timeExtent;
    
    private TimeReference timeReference;
    
    public List<TimeExtent> getTimeExtent() {
        return timeExtent;
    }

    public void setTimeExtent(List<TimeExtent> timeExtent) {
        this.timeExtent = timeExtent;
    }

    public TimeReference getTimeReference() {
        return timeReference;
    }

    public void setTimeReference(TimeReference timeReference) {
        this.timeReference = timeReference;
    }

    public TimeInfo(List<TimeExtent> timeExtents, TimeReference timeRef){
        this.timeExtent = timeExtents;
        this.timeReference = timeRef;
    }

    public TimeInfo() {
        // TODO Auto-generated constructor stub
    }
    
}
