package org.geoserver.gsr.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class SpatialReferenceWKT extends SpatialReference {

    public String wkt;

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }
    
    public SpatialReferenceWKT(String wkt){
        this.wkt = wkt;
    }
}
