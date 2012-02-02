package org.geoserver.geoservices.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */
public class Polygon extends Geometry {

    private Ring[] rings;

    private SpatialReference spatialReference;

    public Ring[] getRing() {
        return rings;
    }

    public void setRing(Ring[] ring) {
        this.rings = ring;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public Polygon(Ring[] rings, SpatialReference spatialRef) {
        this.rings = rings;
        this.spatialReference = spatialRef;
        this.geometryType = GeometryType.POLYGON;
    }
}
