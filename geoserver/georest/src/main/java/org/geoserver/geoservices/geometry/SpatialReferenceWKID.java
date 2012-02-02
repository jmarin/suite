package org.geoserver.geoservices.geometry;


/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */


public class SpatialReferenceWKID extends SpatialReference {

    private int wkid;

    public int getWkid() {
        return wkid;
    }

    public void setWkid(int wkid) {
        this.wkid = wkid;
    }
    
    public SpatialReferenceWKID(int wkid){
        this.wkid = wkid;
    }
    
}
