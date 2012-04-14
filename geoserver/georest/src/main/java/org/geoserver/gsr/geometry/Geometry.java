package org.geoserver.gsr.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public abstract class Geometry {

    protected GeometryType geometryType;

    public GeometryType getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(GeometryType geometryType) {
        this.geometryType = geometryType;
    }
    
}
