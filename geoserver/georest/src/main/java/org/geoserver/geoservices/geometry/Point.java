package org.geoserver.geoservices.geometry;


/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */


public class Point extends Geometry {

    private double x;
    
    private double y;
    
    private SpatialReference spatialReference;

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public Point(double x, double y, SpatialReference spatialRef) {
        this.x = x;
        this.y = y;
        this.spatialReference = spatialRef;
        this.geometryType = GeometryType.POINT;
    }

}
