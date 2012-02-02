package org.geoserver.geoservices.geometry;

/**
 * 
 * @author Juan Marin, OpenGeo
 *
 */

public class Polyline extends Geometry {

    private Path[] paths;

    private SpatialReference spatialReference;

    public Path[] getPaths() {
        return paths;
    }

    public void setPaths(Path[] paths) {
        this.paths = paths;
    }

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }

    public Polyline(Path[] paths, SpatialReference spatialRef) {
        this.paths = paths;
        this.spatialReference = spatialRef;
        this.geometryType = GeometryType.POLYLINE;
    }

}
