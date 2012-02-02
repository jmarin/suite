package org.geoserver.geoservices.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class MultiPoint extends Geometry {

    private Coordinate[] points;

    private SpatialReference spatialReference;

    public Coordinate[] getPoints() {
        return points;
    }

    public void setPoints(Coordinate[] points) {
        this.points = points;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }
    
    public MultiPoint(Coordinate[] coords, SpatialReference spatialReference){
        this.points = coords;
        this.spatialReference = spatialReference;
        this.geometryType = GeometryType.MULTIPOINT;
    }

}
