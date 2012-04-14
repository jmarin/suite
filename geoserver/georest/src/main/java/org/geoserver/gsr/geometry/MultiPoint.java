package org.geoserver.gsr.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class MultiPoint extends Geometry {

    private double[] points;

    private SpatialReference spatialReference;

    public double[] getPoints() {
        return points;
    }

    public void setPoints(double[] points) {
        this.points = points;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }
    
    public MultiPoint(double[] coords, SpatialReference spatialReference){
        this.points = coords;
        this.spatialReference = spatialReference;
        this.geometryType = GeometryType.MULTIPOINT;
    }

}
